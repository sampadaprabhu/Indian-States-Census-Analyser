package com.bridgelabz.census;

import static org.graalvm.compiler.nodeinfo.Verbosity.Id;

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

    public CensusDTO(USCensusCSV censusUS) {
        state=censusUS.state;
        stateCode=censusUS.stateId;
        population=censusUS.population;
        totalArea=censusUS.totalArea;
        populationDensity=censusUS.populationDensity;



    }
}
