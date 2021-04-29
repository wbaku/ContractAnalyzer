package com.sciamus.contractanalyzer.control;


import com.sciamus.contractanalyzer.reporting.checks.ReportNotFoundException;
import com.sciamus.contractanalyzer.reporting.checks.ReportService;
import com.sciamus.contractanalyzer.reporting.checks.CheckReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class ReportRepositoryController {

    private final ReportService reportService;

    @Autowired
    public ReportRepositoryController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RolesAllowed({"reader"})
    @GetMapping("/reports/{id}")
    @ResponseBody
    public CheckReport GetReportById(
            @PathVariable("id") String id) {
        return reportService.getReportByID(id);
    }

    @RolesAllowed({"reader"})
    @GetMapping("/reports")
    @ResponseBody

    //refactor to DTO:
    public List<CheckReport> getAllReports() {
        return reportService.getAllReports();
    }


    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            ReportNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }




}
