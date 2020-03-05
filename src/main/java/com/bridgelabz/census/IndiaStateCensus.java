package com.bridgelabz.census;
import com.opencsv.bean.CsvBindByName;

public class IndiaStateCensus {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public double areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public double densityPerSqKm;

    @Override
    public String toString(){
        return "State Census {"+
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }

}
