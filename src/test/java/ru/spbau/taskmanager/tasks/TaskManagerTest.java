package ru.spbau.taskmanager.tasks;

import org.junit.Test;
import ru.spbau.cli.taskmanager.tasks.ThreadManager;
import ru.spbau.cli.parser.lexems.Argument;
import ru.spbau.cli.parser.lexems.Command;
import ru.spbau.cli.parser.lexems.LexemInterface;
import ru.spbau.cli.parser.lexems.Pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.LinkedList;
import java.util.List;

public class TaskManagerTest {
    @Test
    public void testSimpleChain() {
        ThreadManager threadManager = new ThreadManager();

        List<LexemInterface> lexems = new LinkedList<>();

        lexems.add(new Command("echo"));
        lexems.add(new Argument("blah blah blah"));
        lexems.add(new Argument("blah blah blah"));
        lexems.add(new Pipe());
        lexems.add(new Command("cat"));


        threadManager.run(lexems);

        PipedInputStream stdout = threadManager.getStdout();
        while (true) {
            int data = -1;
            try {
                 data = stdout.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (data == -1) {
                break;
            }

            System.out.print((char)data);

        }
    }
}
