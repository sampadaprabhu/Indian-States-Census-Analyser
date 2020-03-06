package com.bridgelabz.census;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    public enum Country{
        INDIA,US
    }
    List<CensusDTO> censusList=null;

    Map<String, CensusDTO> censusMap=null;

    Map<SortedField,Comparator<CensusDTO>> sortMap=null;

    public CensusAnalyser() {
        this.sortMap=new HashMap<>();

        this.sortMap.put(SortedField.State,Comparator.comparing(census ->census.state));
        this.sortMap.put(SortedField.Population,Comparator.comparing(census -> census.population));
        this.sortMap.put(SortedField.PopulationDensity,Comparator.comparing(census -> census.populationDensity));
        this.sortMap.put(SortedField.TotalArea,Comparator.comparing(census -> census.totalArea));
    }


    public int loadDataCensus(Country country,String ...csvFilePath) {
        censusMap=CensusAdaptorFactory.getCensusData(country,csvFilePath);
        censusList=censusMap.values().stream().collect(Collectors.toList());
        return censusMap.size();
    }

   private <E> int getCount(Iterator<E> Iterator) {
        Iterable<E> census=()-> Iterator;
        int numOfEnteries =(int) StreamSupport.stream(census.spliterator(),false).count();
        return numOfEnteries;
    }

    public String getStateWiseSortCensusData(SortedField sortedField) {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.state);
        this.sortMap.get(sortedField);
        String json=new Gson().toJson(censusList);
        return json;
    }

    public String getPopulationWiseSortCensusData(SortedField sortedField) {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.population);
        this.sortMap.get(sortedField);
        String json=new Gson().toJson(censusList);
        return json;
    }

    public String getDensityWiseSortCensusData(SortedField sortedField) {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.populationDensity);
        this.sortMap.get(sortedField);
        String json=new Gson().toJson(censusList);
        return json;
    }

    public String getAreaWiseSortCensusData(SortedField sortedField) {
        if( censusList.size()==0 || censusList== null  ){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator= Comparator.comparing(stateCensus->stateCensus.totalArea);
        //censusList=censusMap.values().stream().sorted(censusComparator).map(var->var.getCensusDTO(var)).collect(Collectors.toCollection(ArrayList::new));
        censusList=censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusList,this.sortMap.get(sortedField));
        String json=new Gson().toJson(censusList);
        return json;
    }

   private void sort(List<CensusDTO> censusList, Comparator<CensusDTO> censusComparator) {
        for (int i = 0; i< this.censusList.size(); i++){
            for(int j = 0; j< this.censusList.size()-i-1; j++){
                CensusDTO census1= this.censusList.get(j);
                CensusDTO census2= this.censusList.get(j+1);
                if(censusComparator.compare(census1,census2)>0){
                    this.censusList.set(j,census2);
                    this.censusList.set(j+1,census1);
                }
            }
        }
    }

}
