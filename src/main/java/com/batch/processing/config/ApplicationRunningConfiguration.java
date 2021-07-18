package com.batch.processing.config;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.commands.ICommand;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.dao.TBConfigDAO;
import com.batch.processing.dto.ActionType;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(value = "com.batch.processing.populate-testing-data", havingValue = "true")
public class ApplicationRunningConfiguration implements CommandLineRunner {

    private final TBConfigDAO tbConfigDAO;
    private final BeanFactory beanFactory;

    public ApplicationRunningConfiguration(TBConfigDAO tbConfigDAO, BeanFactory beanFactory) {
        this.tbConfigDAO = tbConfigDAO;
        this.beanFactory = beanFactory;
    }

    @Override
    public void run(String... args) {
        System.out.println("RUNNING");
        // Populate testing data
        TBConfig copyToTempConfig = new TBConfig();
        copyToTempConfig.setId(1L);
        copyToTempConfig.setCommand(ActionType.COPY_TO_TEMP.name());
        copyToTempConfig.setExecutionOrder("1");
        copyToTempConfig.setSourcePath("C:\\FILES\\Input");
        copyToTempConfig.setDestinationPath("C:\\FILES\\Output");
        copyToTempConfig.setJobName(BatchConstants.GCU0);
        copyToTempConfig.setJobDescription(BatchConstants.GCU0);
        copyToTempConfig.setAdvancement("ADV");
        copyToTempConfig.setJobId(BatchConstants.GCU0);

        TBConfig copyToWorkDirConfig = new TBConfig();
        copyToWorkDirConfig.setId(2L);
        copyToWorkDirConfig.setCommand(ActionType.COPY_TO_WORKING_DIR.name());
        copyToWorkDirConfig.setExecutionOrder("2");
        copyToWorkDirConfig.setSourcePath("C:\\FILES\\Input");
        copyToWorkDirConfig.setDestinationPath("C:\\FILES\\Output");
        copyToWorkDirConfig.setJobName(BatchConstants.GCU0);
        copyToWorkDirConfig.setJobDescription(BatchConstants.GCU0);
        copyToWorkDirConfig.setAdvancement("ADV");
        copyToWorkDirConfig.setJobId(BatchConstants.GCU0);

        TBConfig deleteTempFiles = new TBConfig();
        deleteTempFiles.setId(3L);
        deleteTempFiles.setCommand(ActionType.DELETE_TEMP.name());
        deleteTempFiles.setExecutionOrder("3");
        deleteTempFiles.setSourcePath("C:\\FILES\\Input");
        deleteTempFiles.setDestinationPath("C:\\FILES\\Output");
        deleteTempFiles.setJobName(BatchConstants.GCU0);
        deleteTempFiles.setJobDescription(BatchConstants.GCU0);
        deleteTempFiles.setAdvancement("ADV");
        deleteTempFiles.setJobId(BatchConstants.GCU0);

        TBConfig renameDirFiles = new TBConfig();
        renameDirFiles.setId(4L);
        renameDirFiles.setCommand(ActionType.RENAME.name());
        renameDirFiles.setExecutionOrder("4");
        renameDirFiles.setSourcePath("C:\\FILES\\Input");
        renameDirFiles.setDestinationPath("C:\\FILES\\Output");
        renameDirFiles.setJobName(BatchConstants.GCU0);
        renameDirFiles.setJobDescription(BatchConstants.GCU0);
        renameDirFiles.setAdvancement("ADV");
        renameDirFiles.setJobId(BatchConstants.GCU0);

        TBConfig notify = new TBConfig();
        notify.setId(5L);
        notify.setCommand(ActionType.NOTIFY.name());
        notify.setExecutionOrder("5");
        notify.setSourcePath("C:\\FILES\\Input");
        notify.setDestinationPath("C:\\FILES\\Output");
        notify.setJobName(BatchConstants.GCU0);
        notify.setJobDescription(BatchConstants.GCU0);
        notify.setAdvancement("ADV");
        notify.setJobId(BatchConstants.GCU0);

        // Save
        this.tbConfigDAO.saveAll(List.of(copyToTempConfig, copyToWorkDirConfig, deleteTempFiles, renameDirFiles, notify));

    }
}
