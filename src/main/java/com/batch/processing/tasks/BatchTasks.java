package com.batch.processing.tasks;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.commands.ICommand;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.services.TBConfigService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchTasks {

    private final TBConfigService tbConfigService;
    private final BeanFactory beanFactory;

    public BatchTasks(TBConfigService tbConfigService, BeanFactory beanFactory) {
        this.tbConfigService = tbConfigService;
        this.beanFactory = beanFactory;
    }

    @Scheduled(cron = "${com.batch.processing.GCU0.scheduling}")
    public void gcu0Task() {
        // Get Steps
        List<TBConfig> configList = this.tbConfigService.getOrderedTbConfig(BatchConstants.GCU0);
        // Loop through config list and execute commands by provided order
        for( TBConfig tbConfig : configList ) {
            // Get command from db
            ICommand command = this.beanFactory.getBean(tbConfig.getCommand(), ICommand.class);
            // Check if command exists then execute it
            if( command != null ) {
                command.execute(tbConfig);
            }
        }
    }


    @Scheduled(cron = "${com.batch.processing.GCU1.scheduling}")
    public void gcu1Task() {
        // Get Steps
        List<TBConfig> configList = this.tbConfigService.getOrderedTbConfig(BatchConstants.GCU1);
        // Loop through config list and execute commands by provided order
        for( TBConfig tbConfig : configList ) {
            // Get command from db
            ICommand command = this.beanFactory.getBean(tbConfig.getCommand(), ICommand.class);
            // Check if command exists then execute it
            if( command != null ) {
                command.execute(tbConfig);
            }
        }
    }

}
