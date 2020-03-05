package com.bridgelabz.census;

public class IndiaCensusDTO {
    public String state;
    public int population;
    public String areaInSqKm;
    public String densityPerSqKm;
    public String stateCode;

    public IndiaCensusDTO(IndiaStateCensus census) {
        state=census.state;
        population=census.population;
        areaInSqKm=census.areaInSqKm;
        densityPerSqKm=census.densityPerSqKm;

    }
}
