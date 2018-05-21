package ru.spbau.cli.taskmanager.tasks;

import java.io.InputStream;
import java.io.OutputStream;

final class Exit implements TaskInterface {
    @Override
    public void run(InputStream in, OutputStream out) {
        System.exit(0);
    }
}