package com.bridgelabz.census;

import com.opencsv.bean.AbstractCSVToBean;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCsvBuilder<E> implements ICSVBuilder {
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass){
        return getCSVBean(reader,csvClass).iterator();
    }

    @Override
    public List<E> getCSVFileList(Reader reader, Class csvClass){
        return getCSVBean(reader,csvClass).parse();
    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        }catch (IllegalStateException ise){
            throw new CSVBuilderException(ise.getMessage(),CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
