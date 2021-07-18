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
import java.nio.file.*;
import java.util.Set;

@Service("COPY_TO_WORKING_DIR")
@Log4j2
public class CopyToWorkDirCommand implements ICommand, Tasklet {

    private final TBConfigService tbConfigService;

    public CopyToWorkDirCommand(TBConfigService tbConfigService) {
        this.tbConfigService = tbConfigService;
    }

    /**
     * Custom command to copy temp created files into work directory path
     *
     * @param tbConfig: Database Object
     */
    @Override
    public void execute(TBConfig tbConfig) {
        try {
            // Get Source Directory Files
            Set<String> sourceFiles = FileUtils.listFilesUsingDirectory(tbConfig.getSourcePath() + File.separator + BatchConstants.TEMP);
            // Check if sourceDirectory is a directory, otherwise do not do anything
            if (!sourceFiles.isEmpty()) {
                // Loop through files
                for (String sourceFileName : sourceFiles) {
                    // Wrap block with try catch to handle loop exceptions without breaking the whole operation
                    try {
                        // Copy files from temp directory to work directory
                        final File sourceFile = new File(tbConfig.getSourcePath()
                                + File.separator
                                + BatchConstants.TEMP
                                + File.separator
                                + sourceFileName);
                        final File destinationFile = new File(tbConfig.getDestinationPath() + File.separator + sourceFile.getName());
                        Files.copy(
                                Paths.get(sourceFile.getPath()),
                                Paths.get(destinationFile.getPath()),
                                StandardCopyOption.REPLACE_EXISTING
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        log.error("Error during execution of CopyToWorkDirCommand in files for loop | Exception message: {}", ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error during execution of CopyToWorkDirCommand | Exception message: {}", ex.getMessage());
        }
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // Get TBConfig from execution context
        String jobId = (String) chunkContext.getStepContext().getJobParameters().get(BatchConstants.BATCH_NAME);
        TBConfig tbConfig = this.tbConfigService.getTbConfig(jobId, "COPY_TO_WORKING_DIR");
        // Execute
        this.execute(tbConfig);
        // Return Status
        return RepeatStatus.FINISHED;
    }

}
