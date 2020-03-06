package com.bridgelabz.census;

import java.util.Map;

public class CensusAdaptorFactory  {

    public static Map<String, CensusDTO> getCensusData(CensusAnalyser.Country country, String... csvFilePath) {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        else if(country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        else
            throw new CensusAnalyserException("Incorrect country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
