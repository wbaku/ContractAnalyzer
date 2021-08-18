package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.application.ReportViewDTO;
import com.sciamus.contractanalyzer.application.ChecksFacade;
import com.sciamus.contractanalyzer.domain.checks.exception.CheckNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.MalformedURLException;

@RestController
public class ChecksServiceController {

    private final ChecksFacade checksFacade;


    public ChecksServiceController(ChecksFacade checksFacade) {
        this.checksFacade = checksFacade;
    }

//    @RolesAllowed("writer")

    @CrossOrigin("*")
    @PostMapping("/checks/{name}/run")
    @ResponseBody
    public ReportViewDTO runAndGetReportWithId(
            @PathVariable("name") String name, @RequestParam(name = "url") String url) throws MalformedURLException {

        return checksFacade.runAndGetSavedReportWithId(name, url);

    }

    @RolesAllowed("writer")
    @PostMapping("/checks/{name}/autorun")
    @ResponseBody
    public ReportViewDTO autorunReport(
            @PathVariable("name") String name, @RequestParam(name = "url") String url) throws MalformedURLException {

        return checksFacade.runAndGetSavedAutogeneratedReportWithId(name, url);
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
