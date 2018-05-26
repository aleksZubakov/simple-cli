package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.parser.lexems.Argument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.io.File;

/*
  command that changes directory
 */
public class Cd implements TaskInterface {
    private List<Argument> args;

    public Cd() {}

    public Cd(List<Argument> args) {
        this.args = args;
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        if (args.isEmpty()) {
            try {
                int data = in.read();
                while (data != -1) {
                    out.write(data);
                    out.flush();
                    data = in.read();
                }
            } catch (IOException e) {
                System.out.println("sth bad");
            }
        } else {
            if (args.size() > 1) {
                System.out.println("too many arguments");
                return;
            }
            for (Argument arg : args) {
                String dirName = arg.getValue();
                File dir;
                if (dirName.startsWith("/")) {
                    dir = new File(dirName);
                } else {
                    dir = new File(System.getProperty("user.dir"), dirName);
                }
                if (!dir.isDirectory()) {
                    try {
                        out.write("not a directory".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        System.setProperty("user.dir", dir.getCanonicalPath());
                    } catch (IOException e) {
                        System.err.println("error while getting dir path");
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}