package ru.spbau.cli.taskmanager.tasks;
import ru.spbau.cli.parser.lexems.Argument;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class Ls implements TaskInterface {
    private List<Argument> args;

    public Ls() {
        args = null;
    }

    public Ls(List<Argument> args) {
        this.args = args;
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        if (args.isEmpty()) {
            args.add(new Argument(""));
        }
        for (Argument arg : args) {
            File dir;
            if (arg.getValue().isEmpty()) {
                dir = new File(System.getProperty("user.dir"));
            } else {
                dir = new File(arg.getValue());
            }
            dir = dir.getAbsoluteFile();
            if (!dir.isDirectory()) {
                try {
                    out.write("not a directory".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                StringBuilder result = new StringBuilder();
                File[] filesInDir = dir.listFiles();
                for (File file : filesInDir) {
                    result.append(file.getName());
                    result.append("\n");
                }
                try {
                    out.write(result.substring(0, result.length() - 1).getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
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
