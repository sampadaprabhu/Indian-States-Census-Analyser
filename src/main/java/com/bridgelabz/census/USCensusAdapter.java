package com.bridgelabz.census;

import java.util.HashMap;
import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) {
        Map<String, CensusDTO> censusMap=new HashMap();
        censusMap=super.loadCensusData(USStateCensus.class,csvFilePath[0]);
        return censusMap;
    }
}
