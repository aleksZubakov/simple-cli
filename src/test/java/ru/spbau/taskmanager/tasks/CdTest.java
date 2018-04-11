package ru.spbau.taskmanager.tasks;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.cli.parser.lexems.Argument;
import ru.spbau.cli.taskmanager.tasks.Cd;
import ru.spbau.cli.taskmanager.tasks.Pwd;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdTest {
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
    public void cdTestNotADirectory() throws IOException {
        List<Argument> args = new LinkedList<>();
        args.add(new Argument("i_am_not_a_directory"));
        String oldDirectory = System.getProperty("user.dir");
        Cd cd = new Cd(args);
        cd.run(pipeInput, out);
        assertEquals("not a directory", reader.readLine());
        assertEquals(oldDirectory, System.getProperty("user.dir"));
    }
    @Test
    public void cdTestDirectory() throws IOException {
        List<Argument> args = new LinkedList<>();
        args.add(new Argument("/home"));
        Cd cd = new Cd(args);
        cd.run(pipeInput, out);
        assertEquals("/home", System.getProperty("user.dir"));
    }
}
