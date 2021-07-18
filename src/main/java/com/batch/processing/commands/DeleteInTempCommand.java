package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.services.TBConfigService;
import com.batch.processing.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;

@Service("DELETE_TEMP")
@Log4j2
public class DeleteInTempCommand implements ICommand, Tasklet {

    private final TBConfigService tbConfigService;

    public DeleteInTempCommand(TBConfigService tbConfigService) {
        this.tbConfigService = tbConfigService;
    }

    /**
     * Custom command to delete files in temp directory
     *
     * @param tbConfig: Database Object
     */
    @Override
    public void execute(TBConfig tbConfig) {
        try {
            // Get Source Directory Files
            Set<String> fileNames = FileUtils.listFilesUsingDirectory(tbConfig.getSourcePath() + File.separator + BatchConstants.TEMP);
            // Check if sourceDirectory is a directory, otherwise do not do anything
            if( !fileNames.isEmpty() ) {
                // Loop through files
                for (String sourceFileName : fileNames) {
                    // Wrap block with try catch to handle loop exceptions without breaking the whole operation
                    try {
                        // Delete File
                        org.apache.commons.io.FileUtils.delete(new File(tbConfig.getSourcePath() + File.separator + BatchConstants.TEMP + File.separator + sourceFileName));
                    } catch (Exception ex) {
                        log.error("Error during execution of CopyToTempCommand in files for loop | Exception message: {}", ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error during execution of DeleteInTempCommand | Exception message: {}", ex.getMessage());
        }
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // Get TBConfig from execution context
        String jobId = (String) chunkContext.getStepContext().getJobParameters().get(BatchConstants.BATCH_NAME);
        TBConfig tbConfig = this.tbConfigService.getTbConfig(jobId, "DELETE_TEMP");
        // Execute
        this.execute(tbConfig);
        // Return Status
        return RepeatStatus.FINISHED;
    }

}
