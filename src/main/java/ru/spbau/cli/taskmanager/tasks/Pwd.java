package ru.spbau.cli.taskmanager.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Pwd implements TaskInterface {
    @Override
    public void run(InputStream in, OutputStream out) {


        try {
            out.write(System.getProperty("user.dir").getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
