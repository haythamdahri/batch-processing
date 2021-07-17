package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("COPY_TO_TEMP")
@Log4j2
public class CopyToTempCommand implements ICommand {

    /**
     * Custom command to merge source path files and copy merged file into temp directory
     *
     * @param tbConfig: Database Object
     */
    @Override
    public void execute(TBConfig tbConfig) {
        try {
            // Get Source Directory Files
            File sourceDirectory = new File(tbConfig.getSourcePath());
            // Check if sourceDirectory is a directory, otherwise do not do anything
            if( sourceDirectory.isDirectory() ) {
                // Get files
                File[] sourceFiles = sourceDirectory.listFiles();
                // Check files
                if (sourceFiles.length > 0) {
                    // Temp file name where all files where be merged
                    String tempFileName = "";
                    // Loop through files
                    for (File sourceFile : sourceFiles) {
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
                                File destinationFile = new File(tbConfig.getSourcePath() + File.separator + BatchConstants.TEMP + File.separator + tempFileName);
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
            log.error("Error during execution of CopyToTempCommand | Exception message: {}", ex.getMessage());
        }
    }

}
