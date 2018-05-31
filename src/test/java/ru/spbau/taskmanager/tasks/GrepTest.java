package ru.spbau.taskmanager.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.cli.parser.lexems.Argument;
import ru.spbau.cli.taskmanager.tasks.Grep;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GrepTest {
    private PipedInputStream pipeInput;
    private BufferedReader reader;
    private BufferedOutputStream out;
    private File newFile;

    @Before
    public void init() throws IOException {
        pipeInput = new PipedInputStream();
        reader = new BufferedReader(new InputStreamReader(pipeInput));
        out = new BufferedOutputStream(new PipedOutputStream(pipeInput));

        newFile = new File("test.file");
        newFile.createNewFile();

        PrintWriter printWriter = new PrintWriter("test.file");

        printWriter.write("first string \n");
        printWriter.write("second string \n");
        printWriter.write("blah string \n");
        printWriter.write("blah string \n");

        printWriter.close();
    }

    @After
    public void tearDown() {
        newFile.delete();
    }

    @Test
    public void grepTest() throws IOException {
        List<Argument> args = Arrays.asList("-i", "second", "test.file")
                .stream()
                .map(Argument::new)
                .collect(Collectors.toList());

        Grep grep = new Grep(args);
        grep.run(pipeInput, out);


        assertEquals("second string ", reader.readLine());

    }

}