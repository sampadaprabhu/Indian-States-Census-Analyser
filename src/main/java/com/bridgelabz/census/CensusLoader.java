package com.bridgelabz.census;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusLoader {
    List<CensusDTO> censusList=new ArrayList<>();
    Map<String, CensusDTO> censusMap=new HashMap<>();
    public  <E> Map<String,CensusDTO> loadCensusData(Class<E> censusCSVClass,String... csvFilePath) {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder= CSVBuilderFactory.createBuilder();
            Iterator<E> stateCensus= csvBuilder.getCSVFileIterator(reader,censusCSVClass);
            Iterable<E> stateCensusIterable=() ->stateCensus;
//            while(stateCensus.hasNext()){
//                CensusDTO csv=new CensusDTO(stateCensus.next());
//                this.censusMap.put(csv.state,csv) ;
//            }
            if (censusCSVClass.getName().equals("com.bridgelabz.census.IndiaStateCensus")) {
                StreamSupport.stream(stateCensusIterable.spliterator(), false)
                        .map(IndiaStateCensus.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDTO(censusCSV)));
            }else if (censusCSVClass.getName().equals("com.bridgelabz.census.USCensusCSV")){
                StreamSupport.stream(stateCensusIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDTO(censusCSV)));
            }
            if (csvFilePath.length==1){
                return censusMap;}
            this.loadDataIndiaStateCode(censusMap,csvFilePath[1]);
            return censusMap;
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    private int loadDataIndiaStateCode(Map<String, CensusDTO> censusMap,String csvFilePath){
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder=CSVBuilderFactory.createBuilder();
            Iterator<IndiaStateCode> stateCode= csvBuilder.getCSVFileIterator(reader,IndiaStateCode.class);
            Iterable<IndiaStateCode> stateCodeIterable= () -> stateCode;
            StreamSupport.stream(stateCodeIterable.spliterator(),false)
                    .filter(csvState -> censusMap.get(csvState.stateName) !=null)
                    .forEach(csvState ->censusMap.get(csvState.stateName).stateCode=csvState.stateCode);

//            while(stateCode.hasNext()){
//                IndiaStateCode code=stateCode.next();
//                CensusDTO csv=censusMap.get(code.stateCode);
//                if(csv == null){
//                    continue;
//                }else{
//                    csv.stateCode=code.stateCode;
//                }
//
//            }
//            censusList=censusMap.values().stream().collect(Collectors.toList());
            return censusMap.size();
        } catch (IOException ioe) {
            throw new CensusAnalyserException(ioe.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }


}
