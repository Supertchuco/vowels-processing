package com.processfile.vowelsprocessing.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

@Component
@Service
@Slf4j
public class MonitorService implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        main(args);
    }

    public static void main(String[] args) throws Exception {
        log.info("monitoring started");
        final long pollingInterval = 5 * 1000;
        File folder = new File(FileService.INPUT_PATH);
        if (!folder.exists()) {
            throw new RuntimeException("Directory not found: " + FileService.INPUT_PATH);
        }
        FileAlterationObserver observer = new FileAlterationObserver(folder);
        FileAlterationMonitor monitor = new FileAlterationMonitor(pollingInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            // Is triggered when a file is created in the monitored folder
            @Override
            public void onFileCreate(File file) {
                FileService fileService = new FileService();
                try {
                    log.info("File created: " + file.getCanonicalPath());
                    if (StringUtils.equalsIgnoreCase(
                            FilenameUtils.getExtension(file.getName()), "txt")
                            && !StringUtils.contains(FilenameUtils.getBaseName(file.getName()), "_OUTPUT")) {
                        fileService.processFile(file);
                    } else {
                        log.info("File: " + file.getName() + " not allowed");
                    }
                } catch (Exception e) {
                    fileService.createErrorFile(e.getMessage(), file.getName());
                }
            }

        };
        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }

}
