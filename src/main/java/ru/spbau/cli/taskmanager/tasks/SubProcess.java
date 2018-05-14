package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.parser.lexems.Argument;
import ru.spbau.cli.parser.lexems.Command;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class SubProcess implements TaskInterface {
    private final Command cmd;

    private final List<String> args;

    public SubProcess(Command cmd, List<Argument> args) {
        this.args = args.stream().map(Argument::getValue).collect(Collectors.toList());
        this.cmd = cmd;
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        ProcessBuilder processBuilder;
        if (args.isEmpty()) {
            processBuilder = new ProcessBuilder(cmd.getValue());
        } else {
            args.add(0, cmd.getValue());
            processBuilder = new ProcessBuilder(args);
        }
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        OutputStream stdin = process.getOutputStream();
        InputStream stderr = process.getErrorStream();
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        while (true) {
            int data = -1;

            try {
                if (in != null)
                    data = in.read();

                if (data == -1) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                writer.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        while (true) {
            int data = -1;
            try {
                data = reader.read();
                if (data == -1)
                    break;

                out.write(data);
                out.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
