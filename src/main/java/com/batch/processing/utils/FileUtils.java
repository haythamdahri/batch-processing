package com.batch.processing.utils;

import com.batch.processing.constants.BatchConstants;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {

    private FileUtils() {}

    public static Set<String> listFilesUsingDirectory(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName().toString());
                }
            }
        }
        return fileList;
    }

    public static String generateTimestampedFileName(String fileName) {
        return FilenameUtils.removeExtension(fileName) +
                BatchConstants.UNDERSCORE +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) +
                BatchConstants.DOT +
                FilenameUtils.getExtension(fileName);
    }

}
