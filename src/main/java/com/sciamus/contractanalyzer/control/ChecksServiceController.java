package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.CheckNotFoundException;
import com.sciamus.contractanalyzer.checks.ContractChecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.MalformedURLException;

@RestController
public class ChecksServiceController {

    private final ContractChecksService contractChecksService;
    private final CheckReportMapper checkReportMapper;

    @Autowired
    public ChecksServiceController(ContractChecksService contractChecksService, CheckReportMapper mapper) {
        this.contractChecksService = contractChecksService;
        this.checkReportMapper = mapper;
    }

    @RolesAllowed({"writer"})
    @GetMapping("/checks/{name}/run")
    @ResponseBody
    public CheckReportDTO runAndGetReportWithId(
            @PathVariable("name") String name, @RequestParam(name = "url") String url) throws MalformedURLException {

        return checkReportMapper.mapToDTO(contractChecksService.runAndGetSavedReportWithId(name, url));
    }

    @RolesAllowed({"writer"})
    @GetMapping("/checks/{name}/autorun")
    @ResponseBody
    public CheckReportDTO autorunReport(
            @PathVariable("name") String name, @RequestParam(name = "url") String url) throws MalformedURLException {

        return checkReportMapper.mapToDTO(contractChecksService.runAndGetSavedAutogeneratedReportWithId(name, url));
    }



    @ExceptionHandler(CheckNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            CheckNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

}
