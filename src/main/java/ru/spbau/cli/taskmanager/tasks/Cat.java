package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.exceptions.IllegalInputFile;
import ru.spbau.cli.exceptions.IllegalStream;
import ru.spbau.cli.parser.lexems.Argument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;


/**
 * Cat
 */
public final class Cat implements TaskInterface {
    private final List<Argument> args;

    public Cat() {
        args = Collections.emptyList();
    }

    public Cat(List<Argument> args) {
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
                throw new IllegalStream("Cat command cannot read from given input stream");
            }
        } else {
            for (Argument arg : args) {
                try {
                    readFromFile(out, arg.getValue());
                } catch (IOException e) {
                    String msg = MessageFormat.format("Cat cannot find file with name ${0}", arg.getValue());
                    throw new IllegalInputFile(msg);
                }
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            throw new IllegalStream("Cat cannot close output stream :c");
        }
    }

    private void readFromFile(OutputStream out, String fileName) throws
            IOException {


        byte[] bytes = Files.readAllBytes(Paths.get(fileName));
        out.write(bytes);

    }
}
