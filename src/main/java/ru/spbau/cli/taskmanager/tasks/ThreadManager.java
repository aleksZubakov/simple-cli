package ru.spbau.cli.taskmanager.tasks;

import ru.spbau.cli.helpers.Commands;
import ru.spbau.cli.parser.lexems.Argument;
import ru.spbau.cli.parser.lexems.Command;
import ru.spbau.cli.parser.lexems.LexemInterface;
import ru.spbau.cli.parser.lexems.Pipe;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Tread Manager for running parsed lexems in a different threads
 */
public class ThreadManager {

    private ExecutorService tpes;
    private PipedInputStream stdout;


    public ThreadManager() {
        tpes = Executors.newCachedThreadPool();
    }


    /**
     * Runs givem lexems.
     *
     * @param lexems list of high-level lexems
     */
    public void run(List<LexemInterface> lexems) {
        List<TaskInterface> tasks = new ArrayList<>();

        List<Argument> args = new LinkedList<>();
        Command cmd = null;

        for (LexemInterface lex : lexems) {
            if (lex instanceof Pipe) {
                if (cmd == null)
                    continue;


                tasks.add(createTask(cmd, args));
                args = new LinkedList<>();
                cmd = null;
                continue;
            }
            if (cmd == null) {
                cmd = (Command) lex;
                continue;
            }
            args.add((Argument) lex);
        }

        if (cmd != null) {
            tasks.add(createTask(cmd, args));
        }

        PipedInputStream oldInput = null;
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = null;
        try {
            out = new PipedOutputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator<TaskInterface> it = tasks.iterator();

        while (it.hasNext()) {
            TaskRunnable taskThread = createTaskThread(oldInput, out, it.next());

            if (it.hasNext()) {
                oldInput = in;

                try {
                    in = new PipedInputStream();
                    out = new PipedOutputStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            tpes.execute(taskThread);
        }
        stdout = in;
    }

    public PipedInputStream getStdout() {
        return stdout;
    }

    private TaskRunnable createTaskThread(InputStream in, OutputStream out,
                                          TaskInterface task) {
        return new TaskRunnable(in, out, task);
    }

    private TaskInterface createTask(Command cmd, List<Argument> args) {

        if (!Commands.isCommand(cmd.getValue())) {
            return new SubProcess(cmd, args);
        }

        TaskInterface task = null;
        switch (Commands.valueOf(cmd.getValue())) {
            case echo:
                task = new Echo(args);
                break;
            case wc:
                task = new WC(args);
                break;
            case cat:
                task = new Cat(args);
                break;
            case pwd:
                task = new Pwd();
                break;
            case exit:
                task = new Exit();
        }
        return task;
    }


}
