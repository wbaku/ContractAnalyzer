package com.sciamus.contractanalyzer.control;

import com.sciamus.contractanalyzer.checks.CheckNotFoundException;
import com.sciamus.contractanalyzer.checks.ContractChecksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
public class ContractChecksServiceController {

    private final ContractChecksService contractChecksService;
    private final TestReportMapper testReportMapper;

    @Autowired
    public ContractChecksServiceController(ContractChecksService contractChecksService, TestReportMapper mapper) {
        this.contractChecksService = contractChecksService;
        this.testReportMapper = mapper;
    }


    @GetMapping("/checks/{name}/run")
    @ResponseBody
    public TestReportDTO runAndGetReportWithId(
            @PathVariable("name") String name, @RequestParam(name = "url") String url) throws MalformedURLException {

        return testReportMapper.mapToDTO(contractChecksService.runAndGetSavedReportWithId(name, url));
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
