package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service("COPY_TO_WORKING_DIR")
@Log4j2
public class CopyToWorkDirCommand implements ICommand {

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
            log.error("Error during execution of CopyToTempCommand | Exception message: {}", ex.getMessage());
        }
    }

}
