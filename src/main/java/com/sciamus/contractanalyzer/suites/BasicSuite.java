package com.sciamus.contractanalyzer.suites;

import com.sciamus.contractanalyzer.checks.ContractChecksService;
import com.sciamus.contractanalyzer.checks.RestContractCheckRepository;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.reporting.suites.SuiteReport;
import com.sciamus.contractanalyzer.reporting.suites.SuiteReportMapper;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

@Component
public class BasicSuite extends CheckSuite {

    private final String NAME = "Basic Suite";

    private final String DESCRIPTION = "Runs 3 first checks of the system";

    ContractChecksService contractChecksService;
    RestContractCheckRepository restContractCheckRepository;
    SuiteReportMapper mapper;


    public BasicSuite(ContractChecksService contractChecksService,
                      SuiteReportMapper mapper,
                      RestContractCheckRepository restContractCheckRepository) {
        super();
        this.contractChecksService = contractChecksService;
        this.mapper = mapper;
        this.restContractCheckRepository = restContractCheckRepository;
    }


    public String getName() {
        return NAME;
    }

    @Override
    public SuiteReport run(URL url) {


        List<CheckReport> checkReports = List.ofAll(restContractCheckRepository.getAllChecks().stream())
                .take(3)
                .map(s -> Try.of(()-> contractChecksService
                        .runAndGetSavedAutogeneratedReportWithId(s, urlToStringTransform(url)))
                        .getOrElseThrow(throwable -> new RuntimeException()))
                .collect(List.collector());


        SuiteReport suiteReport = new SuiteReport(checkReports.toJavaList());
        return suiteReport;

    }
    private String urlToStringTransform(URL url)  {
        return String.valueOf(url);
    }

}
