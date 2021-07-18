package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.services.TBConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.StandardCopyOption;

@Service("RENAME")
@Log4j2
public class RenameFilesInWorkDirCommand implements ICommand, Tasklet {

    private final TBConfigService tbConfigService;

    public RenameFilesInWorkDirCommand(TBConfigService tbConfigService) {
        this.tbConfigService = tbConfigService;
    }

    /**
     * Custom command to rename files in work dir based on date time pattern
     *
     * @param tbConfig: Database Object
     */
    @Override
    public void execute(TBConfig tbConfig) {
        try {
            // Get Source Directory Files
            File sourceDirectory = new File(tbConfig.getDestinationPath());
            // Check if sourceDirectory is a directory, otherwise do not do anything
            if( sourceDirectory.isDirectory() ) {
                // Get files
                File[] sourceFiles = sourceDirectory.listFiles();
                // Check files
                if (sourceFiles != null && sourceFiles.length > 0) {
                    // Loop through files
                    for (File sourceFile : sourceFiles) {
                        // Generate New File Name
                        String finalFileName =  tbConfig.getDestinationPath() + File.separator + com.batch.processing.utils.FileUtils.generateTimestampedFileName(sourceFile.getName());
                        // Rename file
                        FileUtils.moveFile(FileUtils.getFile(sourceFile), FileUtils.getFile(finalFileName), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error during execution of CopyToTempCommand | Exception message: {}", ex.getMessage());
        }
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // Get TBConfig from execution context
        String jobId = (String) chunkContext.getStepContext().getJobParameters().get(BatchConstants.BATCH_NAME);
        TBConfig tbConfig = this.tbConfigService.getTbConfig(jobId, "RENAME");
        // Execute
        this.execute(tbConfig);
        // Return Status
        return RepeatStatus.FINISHED;
    }

}
