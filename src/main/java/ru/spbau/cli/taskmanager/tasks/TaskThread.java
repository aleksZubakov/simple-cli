package ru.spbau.cli.taskmanager.tasks;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Thread Task for running every task in new thread;
 * kind of holder
 */
public class TaskThread implements Runnable {

    private InputStream in;
    private OutputStream out;
    private TaskInterface task;

    public TaskThread(InputStream in, OutputStream out, TaskInterface task) {
        this.in = in;
        this.out = out;
        this.task = task;
    }

    @Override
    public void run() {
        task.run(in, out);
    }
}
