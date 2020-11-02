package com.candidateTest.backend.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.candidateTest.backend.entity.RequestData;

public interface DAORequestData extends CrudRepository<RequestData, Long> {
  List<RequestData> findByTimestamp(long timestamp);
  RequestData findById(long id);
}