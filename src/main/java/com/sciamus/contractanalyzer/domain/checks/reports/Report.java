package com.sciamus.contractanalyzer.domain.checks.reports;


import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "checkReports")
@TypeAlias("check_report")
@Getter
public class Report {


    @Id
    private String id;
    @Field("result")
    private final ReportResults result;
    @Field("content")
    private String reportBody;
    @Field("name")
    private final String nameOfCheck;
    @Field("timestamp")
            //review: is it precise enough?
    Date timestamp;
    @Field("userName")
    private final String userName;



    @PersistenceConstructor
    public Report(String id, ReportResults result, String reportBody, Date timestamp, String nameOfCheck, String userName) {
        this.id = id;
        this.result = result;
        this.reportBody = reportBody;
        this.timestamp = timestamp;
        this.nameOfCheck = nameOfCheck;
        this.userName = userName;
    }

//     TO REVIEW:
//    public TestReport(ReportResults result, String reportBody, String nameOfCheck) {
//
//        this.result = result;
//        this.reportBody = reportBody;
//        this.nameOfCheck = nameOfCheck;
//       }



    // nie jestem pewien tego rozwiązania
    void addId(String id) {
        this.id = id;
    }

    public Report addAutogenerated() {
        this.reportBody  = reportBody + " [this report was autogenerated]";
        return this;
    }

    @Override
    public String toString() {
        return "CheckReport{" +
                "id='" + id + '\'' +
                ", result=" + result +
                ", reportBody='" + reportBody + '\'' +
                ", nameOfCheck='" + nameOfCheck + '\'' +
                ", timestamp=" + timestamp + '\'' +
                ", userName='" + userName +
                '}';
    }
}


