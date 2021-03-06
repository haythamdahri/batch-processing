package com.batch.processing.services.impl;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.dao.TBConfigDAO;
import com.batch.processing.services.TBConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TBConfigServiceImpl implements TBConfigService {

    private final TBConfigDAO tbConfigDAO;

    public TBConfigServiceImpl(TBConfigDAO tbConfigDAO) {
        this.tbConfigDAO = tbConfigDAO;
    }

    @Override
    public List<TBConfig> getOrderedTbConfig(String jobId) {
        return this.tbConfigDAO.findOrderedJobId(jobId);
    }

    @Override
    public TBConfig getTbConfig(String jobId, String command) {
        return this.tbConfigDAO.findJob(jobId, command);
    }
}
