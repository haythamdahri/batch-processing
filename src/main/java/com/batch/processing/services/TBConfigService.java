package com.batch.processing.services;

import com.batch.processing.bo.TBConfig;

import java.util.List;

public interface TBConfigService {

    List<TBConfig> getOrderedTbConfig(String jobId);

}
