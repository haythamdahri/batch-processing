package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("RENAME")
@Log4j2
public class RenameFilesInWorkDirCommand implements ICommand {

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

}
