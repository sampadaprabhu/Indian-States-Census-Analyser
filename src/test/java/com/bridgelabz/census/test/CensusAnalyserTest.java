package com.bridgelabz.census.test;

import com.bridgelabz.census.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CensusAnalyserTest {



    CensusAnalyser analyser;
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_EXT_CSV_FILE = "./src/test/resources/IndiaStateCensusData.cv";
    private static final String INDIA_STATE_CSV_FILE_PATH="./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_WRONG_CSV_FILE_PATH="./src/resources/IndiaStateCode.csv";

    private static final String US_CENSUS_CSV_FILE_PATH="./src/test/resources/USCensusData.csv";
    private static final String US_CENSUS_WRONG_CSV_FILE_PATH ="./src/main/resources/USCensusData.csv";
    private static final String US_CENSUS_WRONG_EXT_CSV_FILE = "./src/test/resources/USCensusData.cv";
    private static final String US_STATE_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String US_STATE_WRONG_CSV_FILE_PATH = "./src/resources/USCensusData.csv";


    @Before
    public void setUp(){
        analyser=new CensusAnalyser();
    }

    @Test
    public void givenIndiaStateCensusPath_Correct_ShouldReturn_NoOfRecords() {
        try {
            int count=analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,count);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaStateCensusPath_Wrong_ShouldThrow_Exception() {
        try {
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusExtension_Wrong_ShouldThrow_Exception() {
        try {
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_WRONG_EXT_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodePath_Correct_ShouldReturn_NoOfRecords() {
        try {
            int count=analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29,count);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaStateCodePath_Wrong_ShouldReturn_Exception() {
        try {
            int count=analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnState_ShouldReturnSortedResult() {
        try{
            analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getStateWiseSortCensusData(SortedField.State);
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh",censusCsv[0].state);
        }catch(CensusAnalyserException e){}
    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortCensusData = analyser.getPopulationWiseSortCensusData(SortedField.Population);
        IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
        Assert.assertEquals(199812341,censusCsv[censusCsv.length-1].population);
    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnDensity_ShouldReturnSortedResult() {
        analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortCensusData = analyser.getDensityWiseSortCensusData(SortedField.PopulationDensity);
        IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
        Assert.assertEquals(103804637,censusCsv[censusCsv.length-1].population);
    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnArea_ShouldReturnSortedResult() {
        analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortCensusData = analyser.getAreaWiseSortCensusData(SortedField.TotalArea);
        IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
        Assert.assertEquals(1457723,censusCsv[0].population);
    }

    @Test
    public void givenUSStateCensusPath_Correct_ShouldReturn_NoOfRecords() {
        try {
            int count=analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51,count);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenUSStateCensusPath_Wrong_ShouldThrowException() {
        try{
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSStateCensusExtension_Wrong_ShouldReturnNoOfRecords() {
        try {
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_WRONG_EXT_CSV_FILE);
        } catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSStateCodePath_Correct_ShouldReturn_NoOfRecords() {
        try {
            int count=analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH,US_STATE_CSV_FILE_PATH);
            Assert.assertEquals(51,count);
        } catch (CensusAnalyserException e){}
    }

    @Test
    public void givenUSStateCodePath_Wrong_ShouldReturn_Exception() {
        try {
            int count=analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH,US_STATE_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSStateCensus_WhenSortedOnState_ShouldReturnSortedResult() {
        try{
            analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getStateWiseSortCensusData(SortedField.State);
            USStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, USStateCensus[].class);
            Assert.assertEquals("North Carolina",censusCsv[0].state);
        }catch(CensusAnalyserException e){}

    }

    @Test
    public void givenUSStateCensus_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortCensusData = analyser.getPopulationWiseSortCensusData(SortedField.Population);
        USStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, USStateCensus[].class);
        Assert.assertEquals(11536504,censusCsv[censusCsv.length-1].population);
    }

    @Test
    public void givenUSStateCensus_WhenSortedOnDensity_ShouldReturnSortedResult() {
        analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortCensusData = analyser.getDensityWiseSortCensusData(SortedField.PopulationDensity);
        USStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, USStateCensus[].class);
        Assert.assertEquals(11536504,censusCsv[censusCsv.length-1].population);
    }

    @Test
    public void givenUSStateCensus_WhenSortedOnArea_ShouldReturnResult() {
        analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortCensusData = analyser.getAreaWiseSortCensusData(SortedField.TotalArea);
        USStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, USStateCensus[].class);
        Assert.assertEquals(601723,censusCsv[0].population);
    }


    @Test
    public void testDemo() {
        analyser.loadDataCensus(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortCensusData1 = analyser.getPopulationWiseSortCensusData(SortedField.Population);
        IndiaStateCensus[] censusCsv1 = new Gson().fromJson(sortCensusData1, IndiaStateCensus[].class);
        Assert.assertEquals(1980602,censusCsv1[censusCsv1.length-1].population);

        analyser.loadDataCensus(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        String sortCensusData2 = analyser.getPopulationWiseSortCensusData(SortedField.Population);
        USStateCensus[] censusCsv2 = new Gson().fromJson(sortCensusData2, USStateCensus[].class);
        Assert.assertEquals(11536504,censusCsv2[censusCsv2.length-1].population);

    }

}