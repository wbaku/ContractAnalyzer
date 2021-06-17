package com.sciamus.contractanalyzer.domain.suites;

import io.vavr.collection.List;
import org.springframework.stereotype.Repository;

@Repository
public class SuitesRepository {

    private java.util.List<CheckSuite> checkSuites;

    public SuitesRepository (java.util.List<CheckSuite> checkSuites) {
        this.checkSuites = checkSuites;
    }

    public java.util.List<CheckSuite> getCheckSuites() {
        return this.checkSuites;
    }

    public java.util.List<String> getAllSuites() {

        return List.ofAll(checkSuites.stream())
                .map(CheckSuite::getName)
                .collect(List.collector())
                .toJavaList();
    }
}
