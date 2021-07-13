package com.sciamus.contractanalyzer.domain.checks.suites;

import com.sciamus.contractanalyzer.application.ReportDTO;
import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.application.mapper.ReportMapper;
import com.sciamus.contractanalyzer.domain.checks.rest.RestCheckRepository;
import com.sciamus.contractanalyzer.domain.checks.reports.Report;
import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

public class BasicSuite extends CheckSuite {

    private final String NAME = "Basic Suite";

    private final String DESCRIPTION = "Runs 3 first checks of the system";

    private final ChecksFacade checksFacade;

    private final RestCheckRepository restCheckRepository;

    private final ReportMapper reportMapper;

    @Autowired
    public BasicSuite(ChecksFacade checksFacade, RestCheckRepository restCheckRepository, ReportMapper reportMapper) {
        super();
        this.checksFacade = checksFacade;
        this.restCheckRepository = restCheckRepository;
        this.reportMapper = reportMapper;
    }

    public String getName() {
        return NAME;
    }

    @Override
    public SuiteReport run(URL url) {


        List<ReportDTO> checkReportDTOS = List.ofAll(restCheckRepository.getAllChecks().stream())
                .take(3)
                .peek(System.out::println)
                .map(s -> Try.of(()-> checksFacade
                        .runAndGetSavedAutogeneratedReportWithId(s, urlToStringTransform(url)))
                        .getOrElseThrow(throwable -> new RuntimeException(throwable)))
                .collect(List.collector());


        List<Report> checkReports = checkReportDTOS
                .toStream()
                .map(checkReportDTO -> reportMapper.mapFromDTO(checkReportDTO))
                .collect(List.collector());

        SuiteReport suiteReport = new SuiteReport(checkReports.toJavaList());
        return suiteReport;

    }
    private String urlToStringTransform(URL url)  {
        return String.valueOf(url);
    }

}