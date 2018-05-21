package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.exceptions.IllegalInputFile;
import ru.spbau.cli.exceptions.IllegalStream;
import ru.spbau.cli.exceptions.UnexpectedStringEnd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Thread Task for running every task in new thread;
 * kind of holder
 */
public class TaskRunnable implements Runnable {

    private InputStream in;
    private OutputStream out;
    private TaskInterface task;

    public TaskRunnable(InputStream in, OutputStream out, TaskInterface task) {
        this.in = in;
        this.out = out;
        this.task = task;
    }

    @Override
    public void run() {
        try {
            task.run(in, out);
        } catch (IllegalInputFile e) {
            System.out.println("Problems with file: ");
            e.printStackTrace();
        } catch (IllegalStream e) {
            System.out.println("Problems with stream: ");
            e.printStackTrace();
        } catch (UnexpectedStringEnd e) {
            System.out.println("Problems with parsing: ");
            e.printStackTrace();
        } finally {
            // always should close output stream
            try {
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
