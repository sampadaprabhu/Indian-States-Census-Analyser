package com.bridgelabz.census;

public class CensusAnalyserException extends RuntimeException{

    public enum ExceptionType{
        NO_CENSUS_DATA,CENSUS_FILE_PROBLEM;
    }
    public ExceptionType type;

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type=ExceptionType.valueOf(name);
    }

    public CensusAnalyserException(ExceptionType type) {
        this.type=type;
    }

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, Throwable cause, ExceptionType type) {
        super(message, cause);
        this.type = type;
    }
}
