package com.sciamus.contractanalyzer.domain.checks.rest.reportcheck;

import com.sciamus.contractanalyzer.domain.checks.rest.FeignCheckClientInterface;
import com.sciamus.contractanalyzer.domain.checks.rest.RestContractCheck;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportResults;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReportBuilder;
import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ReportingCheck implements RestContractCheck {

    private final static String NAME = "Reporting Check";

    private final CheckReportMapper checkReportMapper;

    private final RequestInterceptor interceptor;

    public ReportingCheck(CheckReportMapper checkReportMapper, RequestInterceptor interceptor) {
        this.checkReportMapper = checkReportMapper;
        this.interceptor = interceptor;
    }

    @Override
    public CheckReport run(URL url, CheckReportBuilder reportBuilder) {
        FeignCheckClientInterface feignCheckClientInterface = Feign.builder()
                .decoder(new GsonDecoder())
                .requestInterceptor(interceptor)
                .target(FeignCheckClientInterface.class, url.toString());

        String checkToRun = URLEncoder.encode(feignCheckClientInterface
                .getAvailableChecks()
                .listOfChecks.get(0), StandardCharsets.UTF_8)
                .replace("+", "%20");

        CheckReport reportSentToDatabase = checkReportMapper.
                mapFromDTO(feignCheckClientInterface.autogenerate(checkToRun, url));

        CheckReport reportFetchedFromDatabase = checkReportMapper.
                mapFromDTO(feignCheckClientInterface.getReportById(reportSentToDatabase.getId()));

        reportBuilder.setNameOfCheck(this.getName())
                .setReportBody("Run on: " +url)
                .createTimestamp();

        if (reportSentToDatabase.getTimestamp().equals(reportFetchedFromDatabase.getTimestamp())) {
            return reportBuilder
                    .setResult(ReportResults.PASSED)
                    .build();
        }
        return  reportBuilder
                .setResult(ReportResults.FAILED)
                .build();
    }

    @Override
    public String getName() {
        return NAME;
    }

}
