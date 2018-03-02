package ru.spbau.taskmanager.tasks;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.cli.taskmanager.tasks.Pwd;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest {

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
    public void pwdTest() throws IOException {
        Pwd pwd = new Pwd();
        pwd.run(pipeInput, out);

        assertEquals(System.getProperty("user.dir"), reader.readLine());
    }
}
