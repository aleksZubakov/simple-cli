package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.parser.lexems.Argument;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Echo implements TaskInterface {

    private List<Argument> args;

    public Echo() {
        args = null;
    }

    public Echo(List<Argument> args) {
        this.args = args;
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        if (args.isEmpty()) {
            try {
                out.close();
            } catch (IOException e) { /* TODO normal exception handling*/
                e.printStackTrace();
            }
            return;
        }

        for (Argument arg : args) {
            try {
                out.write(arg.getValue().getBytes());
                out.flush();

            } catch (IOException e) { /* TODO normal exception handling*/
                e.printStackTrace();
            }
        }

        try {
            out.close();
        } catch (IOException e) { /* TODO normal exception handling*/
            e.printStackTrace();
        }
    }
}
