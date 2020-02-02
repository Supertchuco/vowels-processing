package com.processfile.vowelsprocessing.service;

import com.processfile.vowelsprocessing.dto.WordGrouped;
import com.processfile.vowelsprocessing.enumerator.ErrorMessages;
import com.processfile.vowelsprocessing.exception.CreateOutputFileException;
import com.processfile.vowelsprocessing.exception.ReadFileException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FileService {

    public static final String INPUT_PATH = "/opt/files/input/";
    public static final String OUT_PATH = "/opt/files/output/";

    public List<String> readFile(final File file) {
        List<String> fileLines = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (Exception exception) {
            log.error(ErrorMessages.ERROR_READ_FILE.getMessage(), exception);
            throw new ReadFileException(ErrorMessages.ERROR_READ_FILE.getMessage());
        }
        return fileLines;
    }

    public void deleteFile(final String fileName, final String path) {
        try {
            Files.delete(Paths.get(path.concat(fileName)));
        } catch (IOException e) {
            log.error(ErrorMessages.ERROR_DELETE_FILE.getMessage(), e);
        }
    }

    public void createFileOutput(final Map<String, WordGrouped> data, final String fileName) {
        try {
            final PrintWriter writer = new PrintWriter(OUT_PATH.concat(fileName.concat("_OUTPUT.txt")), "UTF-8");
            if (data.isEmpty()) {
                writer.println(ErrorMessages.ERROR_FILE_IS_EMPTY.getMessage());
            } else {
                data.forEach((key, value) -> writer.println("({" + value.getVowels() + "}, " +
                        value.getWordSize() + ") -> " + value.getAverage()));
            }
            writer.close();
        } catch (IOException e) {
            log.error(ErrorMessages.ERROR_CREATE_OUTPUT_FILE.getMessage());
            throw new CreateOutputFileException();
        }
    }

    public void processFile(final File file) {
        log.info("Process file");
        final String fileName = buildFileName(file.getName());
        final List<String> lines = readFile(file);
        TextService textService = new TextService();
        final Map<String, WordGrouped> data = textService.processLines(lines);
        createFileOutput(data, fileName);
        deleteFile(file.getName(), INPUT_PATH);
    }

    public void createErrorFile(final String errorMessage, final String fileName) {
        try {
            final PrintWriter writer = new PrintWriter(OUT_PATH.concat(buildFileName(fileName)).concat("_OUTPUT.txt"), "UTF-8");
            writer.println(errorMessage);
            writer.close();
        } catch (IOException e) {
            log.error(ErrorMessages.ERROR_CREATE_OUTPUT_FILE.getMessage());
            throw new CreateOutputFileException();
        }
    }

    public String buildFileName(final String fileName) {
        return FilenameUtils.getBaseName(fileName).concat("_")
                .concat(new SimpleDateFormat("yyyy-dd-M--HH-mm-ss").format(new Date()));
    }

}
