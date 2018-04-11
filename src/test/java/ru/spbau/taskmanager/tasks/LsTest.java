package ru.spbau.taskmanager.tasks;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.spbau.cli.parser.lexems.Argument;
import ru.spbau.cli.taskmanager.tasks.Cd;
import ru.spbau.cli.taskmanager.tasks.Ls;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsTest {
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
    public void lsTestNotADirectory() throws IOException {
        List<Argument> args = new LinkedList<>();
        args.add(new Argument("i_am_not_a_directory"));
        String oldDirectory = System.getProperty("user.dir");
        Ls ls = new Ls(args);
        ls.run(pipeInput, out);
        assertEquals("not a directory", reader.readLine());
    }
    @Test
    public void lsTestDirectory() throws IOException {
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();
        folder.newFile("tmp.txt");
        folder.newFolder("tmp_folder");

        List<Argument> args = new LinkedList<>();
        args.add(new Argument(folder.getRoot().getAbsolutePath()));
        Ls ls = new Ls(args);
        ls.run(pipeInput, out);
        String res = reader.lines().collect(Collectors.joining("\n"));
        assertEquals("tmp.txt\ntmp_folder", res);
        Cd cd = new Cd(args);
        cd.run(pipeInput, out);
        init();
        Ls currentDirLs = new Ls(new LinkedList<>());
        currentDirLs.run(pipeInput, out);
        res = reader.lines().collect(Collectors.joining("\n"));
        assertEquals("tmp.txt\ntmp_folder", res);
    }
}
