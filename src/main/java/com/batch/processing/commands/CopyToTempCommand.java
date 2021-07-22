package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.services.TBConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Service("COPY_TO_TEMP")
@Log4j2
public class CopyToTempCommand implements ICommand, Tasklet {

    private final TBConfigService tbConfigService;

    public CopyToTempCommand(TBConfigService tbConfigService) {
        this.tbConfigService = tbConfigService;
    }

    /**
     * Custom command to merge source path files and copy merged file into temp directory
     *
     * @param tbConfig: Database Object
     */
    @Override
    public void execute(TBConfig tbConfig) {
        try {
            // Split files
            String[] fileNames = tbConfig.getSourcePath().split(BatchConstants.SOURCE_FILES_SPLITTER);
            // Temp file name where all files where be merged
            String tempFileName = "";
            // Loop through files
            if (fileNames.length > 0) {
                for (String fileName : fileNames) {
                    // Get Source Directory Files
                    File sourceFile = new File(fileName);
                    // Check if sourceDirectory is a directory, otherwise do not do anything
                    if (!sourceFile.isDirectory() && sourceFile.exists()) {
                        // Wrap block with try catch to handle loop exceptions without breaking the whole operation
                        try {
                            // Assign file name if not already assigned
                            if (tempFileName.isEmpty()) {
                                tempFileName = sourceFile.getName();
                            }
                            // Check file existence
                            if (sourceFile.exists() && !sourceFile.isDirectory()) {
                                final String fileData = FileUtils.readFileToString(sourceFile, StandardCharsets.UTF_8);
                                // Create Destination Path if not exists
                                File destinationFile = new File(
                                        sourceFile.getParent().toString()
                                        + File.separator
                                        + BatchConstants.TEMP
                                        + File.separator
                                        + tempFileName
                                );
                                System.out.println("destinationFile.getAbsolutePath(): " + destinationFile.getAbsolutePath());
                                // Copy file into final created file in the temp directory if exists
                                FileUtils.writeStringToFile(
                                        destinationFile,
                                        fileData,
                                        StandardCharsets.UTF_8,
                                        true
                                );
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            log.error("Error during execution of CopyToTempCommand in files for loop | Exception message: {}", ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error during execution of CopyToTempCommand | Exception message: {}", ex.getMessage());
        }
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // Get TBConfig from execution context
        String jobId = (String) chunkContext.getStepContext().getJobParameters().get(BatchConstants.BATCH_NAME);
        TBConfig tbConfig = this.tbConfigService.getTbConfig(jobId, "COPY_TO_TEMP");
        // Execute
        this.execute(tbConfig);
        // Return Status
        return RepeatStatus.FINISHED;
    }
}
