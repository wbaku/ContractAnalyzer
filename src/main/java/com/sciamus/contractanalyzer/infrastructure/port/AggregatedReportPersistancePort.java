package com.sciamus.contractanalyzer.infrastructure.port;

import java.util.List;

public interface AggregatedReportPersistancePort {

    AggregatedReportInfrastructureDTO save(AggregatedReportInfrastructureDTO report, String name);

    AggregatedReportInfrastructureDTO findById(String id);

    List<AggregatedReportInfrastructureDTO> findAll();

    List<AggregatedReportInfrastructureDTO> findAll(int pageSize);



}
