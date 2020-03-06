package com.bridgelabz.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) {
        Map<String, CensusDTO> censusMap=new HashMap();
        censusMap=super.loadCensusData(IndiaStateCensus.class,csvFilePath[0]);
        if(csvFilePath.length==1)
            return censusMap;
        loadDataIndiaStateCode(censusMap,csvFilePath[1]);
        return censusMap;
         }

    private int loadDataIndiaStateCode(Map<String, CensusDTO> censusMap, String csvFilePath) {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder=CSVBuilderFactory.createBuilder();
            Iterator<IndiaStateCode> codeIterator= csvBuilder.getCSVFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> stateCodeCSV= ()-> codeIterator;

            StreamSupport.stream(stateCodeCSV.spliterator(),false)
                    .filter(indianStateCodeCSV -> censusMap.get(indianStateCodeCSV) != null)
                    .forEach(indiaStateCode -> censusMap.get(indiaStateCode).stateCode = indiaStateCode.stateCode);
            return censusMap.size();
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

}

