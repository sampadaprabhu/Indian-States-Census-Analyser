package com.bridgelabz.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class  CensusAdapter {
    public abstract Map<String, CensusDTO> loadCensusData(String ...csvFilePath);

    public <E> Map<String, CensusDTO> loadCensusData(Class<E> className,String csvFilePath) {
        Map<String, CensusDTO> censusMap=new HashMap<>();
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder=CSVBuilderFactory.createBuilder();
            Iterator<E> stateCensus= csvBuilder.getCSVFileIterator(reader,className);
            Iterable<E> stateCensusIterable=()->stateCensus;
            if (className.getName().equals("com.bridgelabz.census.IndiaStateCensus")){
                StreamSupport.stream(stateCensusIterable.spliterator(),false)
                        .map(IndiaStateCensus.class::cast)
                        .forEach(indiaStateCode -> censusMap.put(indiaStateCode.state,new CensusDTO(indiaStateCode)));
            }
            else if(className.getName().equals("com.bridgelabz.census.USStateCensus")){
                StreamSupport.stream(stateCensusIterable.spliterator(),false)
                        .map(USStateCensus.class::cast)
                        .forEach(indiaStateCode -> censusMap.put(indiaStateCode.state,new CensusDTO(indiaStateCode)));
            }
            return censusMap;
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }
}
