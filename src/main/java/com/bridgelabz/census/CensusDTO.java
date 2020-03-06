package com.bridgelabz.census;

public class CensusDTO {
    public String state;
    public int population;
    public double totalArea;
    public double populationDensity;
    public String stateCode;

    public CensusDTO(IndiaStateCensus census) {
        state=census.state;
        population=census.population;
        totalArea=census.areaInSqKm;
        populationDensity=census.densityPerSqKm;

    }

    public CensusDTO(USStateCensus census) {
        state=census.state;
        stateCode=census.stateId;
        population=census.population;
        totalArea=census.totalArea;
        populationDensity=census.populationDensity;
    }

//    public Object getCensusDTO(CensusDTO var) {
//        if (CensusAnalyser.Country.US){
//            return new USStateCensus(state,stateCode,population,totalArea,populationDensity);
//        }
//        else if (CensusAnalyser.Country.INDIA){
//            return new IndiaStateCensus(state,stateCode,population,totalArea,populationDensity);
//        }
//        return null;
//    }
}
