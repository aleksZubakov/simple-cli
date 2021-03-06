package ru.spbau.taskmanager.tasks;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.cli.parser.lexems.Argument;
import ru.spbau.cli.taskmanager.tasks.Echo;
import ru.spbau.cli.taskmanager.tasks.WC;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WCTest {
    private PipedInputStream pipeInput;
    private BufferedReader reader;
    private BufferedOutputStream out;

    @Before
    public void init() throws IOException {
        pipeInput = new PipedInputStream();
        reader = new BufferedReader(new InputStreamReader(pipeInput));
        out = new BufferedOutputStream(new PipedOutputStream(pipeInput));

    }

    @Test
    public void testEchoWithArguments() throws IOException {
        List<Argument> args = new LinkedList<>();

        args.add(new Argument("test.txt"));
        args.add(new Argument("test.txt"));

        WC test = new WC(args);

        test.run(pipeInput, out);


        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
    }
}
