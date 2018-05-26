package ru.spbau.cli.taskmanager.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for tasks
 */
public interface TaskInterface {
    void run(InputStream in, OutputStream out); // TODO Output stream err??

    default String getAbsolutePath(String filename) {
        if (filename.startsWith("/")) {
            return filename;
        } else {
            return System.getProperty("user.dir") + "/" + filename;
        }
    }
}
