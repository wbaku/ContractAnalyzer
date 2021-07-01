package com.sciamus.contractanalyzer.interfaces.rest;

import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.checks.rest.getlistof.ListOfChecksDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class ChecksRepositoryController {

    //TODO: fix me: this cannot use domain object directly -> it always must got through application layer
    private final CheckRepository checkRepository;

    @Autowired
    public ChecksRepositoryController(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    @RolesAllowed("reader")
    @GetMapping("/restContractChecks")
    public ListOfChecksDTO getRestContractCheckList() {

        ListOfChecksDTO responseDTO = new ListOfChecksDTO();
        responseDTO.listOfChecks = checkRepository.getAllChecks();
        return responseDTO;

    }


}
