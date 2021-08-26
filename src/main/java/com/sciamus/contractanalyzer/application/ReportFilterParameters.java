package com.sciamus.contractanalyzer.application;


public class ReportFilterParameters {
    public final  String  result;
    public final  String  reportBody;
    public final  String  timestampFrom;
    public final  String  timestampTo;
    public final  String  nameOfCheck;
    public final  String  userName;


    public ReportFilterParameters (String result, String reportBody, String timestampFrom, String timestampTo, String nameOfCheck, String userName) {
        this.result = result;
        this.reportBody = reportBody;
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
        this.nameOfCheck = nameOfCheck;
        this.userName = userName;
    }



}