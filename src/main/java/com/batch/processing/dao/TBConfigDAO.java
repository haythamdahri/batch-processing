package com.batch.processing.dao;

import com.batch.processing.bo.TBConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TBConfigDAO extends JpaRepository<TBConfig, Long> {

    @Query(value = "FROM TBConfig TB WHERE TB.jobId = :jobId ORDER BY TB.executionOrder ASC")
    List<TBConfig> findOrderedJobId(@Param("jobId") String jobId);

    @Query(value = "FROM TBConfig TB WHERE TB.jobId = :jobId AND TB.command = :command")
    TBConfig findJob(@Param("jobId") String jobId, @Param("command") String command);

}
