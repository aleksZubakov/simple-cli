package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.exceptions.IllegalStream;
import ru.spbau.cli.parser.lexems.Argument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Implementations of echo utility
 */
public class Echo implements TaskInterface {

    private final List<Argument> args;

    public Echo() {
        args = Collections.emptyList();
    }

    public Echo(List<Argument> args) {
        this.args = args;
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        for (Argument arg : args) {
            try {
                out.write(arg.getValue().getBytes());
                out.flush();

            } catch (IOException e) {
                throw new IllegalStream("Echo cannot write into output stream");
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            throw new IllegalStream("Echo cannot close output stream");
        }
    }
}
