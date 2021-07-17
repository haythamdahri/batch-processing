package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;

@Service("DELETE_TEMP")
@Log4j2
public class DeleteInTempCommand implements ICommand {

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

}
