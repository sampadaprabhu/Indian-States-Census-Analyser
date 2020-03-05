package com.bridgelabz.census.test;

import com.bridgelabz.census.CensusAnalyser;
import com.bridgelabz.census.CensusAnalyserException;
import com.bridgelabz.census.IndiaStateCensus;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CensusAnalyserTest {

    private static final String US_CENSUS_CSV_FILE_PATH ="./src/test/resources/USCensusData (1).csv";
    CensusAnalyser analyser;
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_WRONG_EXT_CSV_FILE = "./src/test/resources/IndiaStateCensusData.cv";
    private static final String INDIA_STATE_CSV_FILE_PATH="./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_WRONG_CSV_FILE_PATH="./src/resources/IndiaStateCode.csv";

    @Test
    public void givenIndiaStateCensusPath_Correct_ShouldReturn_NoOfRecords() {
        try {
            int count=analyser.loadDataIndiaStateCensus(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,count);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaStateCensusPath_Wrong_ShouldThrow_Exception() {
        try {
            analyser.loadDataIndiaStateCensus(INDIA_CENSUS_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensusExtension_Wrong_ShouldThrow_Exception() {
        try {
            analyser.loadDataIndiaStateCensus(INDIA_CENSUS_WRONG_EXT_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodePath_Correct_ShouldReturn_NoOfRecords() {
        try {
            analyser.loadDataIndiaStateCensus(INDIA_CENSUS_CSV_FILE_PATH);
            int count=analyser.loadDataIndiaStateCode(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29,count);
        } catch (CensusAnalyserException e) {  }
    }

    @Test
    public void givenIndiaStateCodePath_Wrong_ShouldReturn_Exception() {
        try {
            analyser.loadDataIndiaStateCode(INDIA_STATE_WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCensus_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        analyser.loadDataIndiaStateCensus(INDIA_CENSUS_CSV_FILE_PATH);
        String sortCensusData = analyser.getPopulationWiseSortCensusData();
        IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
        Assert.assertEquals(199812341,censusCsv[censusCsv.length-1].population);
    }


    @Test
    public void givenIndiaStateCensus_WhenSortedOnState_ShouldReturnSortedResult() {
        try{
            analyser.loadDataIndiaStateCensus(INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = analyser.getStateWiseSortCensusData();
            IndiaStateCensus[] censusCsv = new Gson().fromJson(sortCensusData, IndiaStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh",censusCsv[0].state);
        }catch(CensusAnalyserException e){}
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {
        int censusDataCount = analyser.loadDataUSStateCensus(US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51,censusDataCount);
    }

    @Before
    public void setUp(){
        analyser=new CensusAnalyser();
    }


}