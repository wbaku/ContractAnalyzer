package com.sciamus.contractanalyzer.application;

import com.sciamus.contractanalyzer.domain.checks.CheckRepository;
import com.sciamus.contractanalyzer.domain.reporting.checks.CheckReport;
import com.sciamus.contractanalyzer.domain.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.application.mapper.CheckReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ContractChecksService {


    private final CheckRepository checkRepository;
    private final ReportService reportService;
    private final CheckReportMapper checkReportMapper;

    @Autowired
    public ContractChecksService(CheckRepository checkRepository, ReportService reportService, CheckReportMapper checkReportMapper) {
        this.checkRepository = checkRepository;
        this.reportService = reportService;
        this.checkReportMapper = checkReportMapper;
    }


    public CheckReportDTO runAndGetSavedReportWithId(String name, String url) throws MalformedURLException {

        CheckReport toSave = checkRepository.runCheck(name, new URL(url));
        // TODO: czy nie trzeba zwalidować czy się zachował?
        reportService.addReportToRepository(toSave);
        return checkReportMapper.mapToDTO(toSave);

    }

    public CheckReportDTO runAndGetSavedAutogeneratedReportWithId(String name, String url) throws MalformedURLException {

        CheckReport toSave = checkRepository.runCheck(name, new URL(url)).addAutogenerated();
        reportService.addReportToRepository(toSave);

        return checkReportMapper.mapToDTO(toSave);

    }

}
