package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.parser.lexems.Argument;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Cat implements TaskInterface {
    private List<Argument> args;

    public Cat() {
        args = null;
    }

    public Cat(List<Argument> args) {
        this.args = args;
    }

    @Override
    public void run(InputStream in, OutputStream out){
        if (args.isEmpty()) {
            try {
                int data = in.read();
                while (data != -1) {
                    out.write(data);
                    out.flush();
                    data = in.read();
                }
            } catch (IOException e) {
            }
        } else {

            for (Argument arg : args) {
                try { /*TODO normal exception handling*/
                    readFromFile(out, getAbsolutePath(arg.getValue()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            out.close(); /*TODO normal exception handling*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile(OutputStream out, String fileName) throws
            IOException {
        Scanner fileInput = new Scanner(new FileReader(fileName));

        while (fileInput.hasNext()) {
            out.write(fileInput.next().getBytes());
            out.flush();
        }

    }


}
