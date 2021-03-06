package ru.spbau.cli.taskmanager.tasks;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for tasks
 */
public interface TaskInterface {
    void run(InputStream in, OutputStream out);
}
