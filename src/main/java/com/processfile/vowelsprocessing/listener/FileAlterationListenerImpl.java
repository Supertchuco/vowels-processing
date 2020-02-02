package com.processfile.vowelsprocessing.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.Date;

@Slf4j
public class FileAlterationListenerImpl implements FileAlterationListener {

    @Override
    public void onStart(final FileAlterationObserver observer) {
        log.info("The WindowsFileListener has started on " + observer.getDirectory().getAbsolutePath());
    }

    @Override
    public void onDirectoryCreate(final File directory) {
        log.info(directory.getAbsolutePath() + " was created.");
    }

    @Override
    public void onDirectoryChange(final File directory) {
        log.info(directory.getAbsolutePath() + " wa modified");
    }

    @Override
    public void onDirectoryDelete(final File directory) {
        log.info(directory.getAbsolutePath() + " was deleted.");
    }

    @Override
    public void onFileCreate(final File file) {
        log.info(file.getAbsoluteFile() + " was created.");
        log.info("----------> length: " + file.length());
        log.info("----------> last modified: " + new Date(file.lastModified()));
        log.info("----------> readable: " + file.canRead());
        log.info("----------> writable: " + file.canWrite());
        log.info("----------> executable: " + file.canExecute());

    }

    @Override
    public void onFileChange(final File file) {
        log.info(file.getAbsoluteFile() + " was modified.");
        log.info("----------> length: " + file.length());
        log.info("----------> last modified: " + new Date(file.lastModified()));
        log.info("----------> readable: " + file.canRead());
        log.info("----------> writable: " + file.canWrite());
        log.info("----------> executable: " + file.canExecute());
    }

    @Override
    public void onFileDelete(final File file) {
        log.info(file.getAbsoluteFile() + " was deleted.");
    }

    @Override
    public void onStop(final FileAlterationObserver observer) {
        log.info("The WindowsFileListener has stopped on " + observer.getDirectory().getAbsolutePath());
    }
}
