package ru.spbau.cli;

import ru.spbau.cli.environment.Environment;
import ru.spbau.cli.exceptions.UnexpectedStringEnd;
import ru.spbau.cli.interploator.Interpolator;
import ru.spbau.cli.lexer.Lexer;
import ru.spbau.cli.lexer.tokens.TokenInterface;
import ru.spbau.cli.parser.Parser;
import ru.spbau.cli.parser.lexems.Assignment;
import ru.spbau.cli.parser.lexems.LexemInterface;
import ru.spbau.cli.taskmanager.tasks.ThreadManager;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * This is class representing main loop of whole program
 */

public class MainThread {
    /**
     * Prints data from stream if stream contains data
     *
     * @param in input stream containing result of previous runs
     */
    private static void printStream(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter("\\r?\\n");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }


    /**
     * Main loop. Call lexer, parser, interpolator and run commands
     */
    public static void main(String args[]) {
        ThreadManager threadManager = new ThreadManager();
        Environment env = new Environment();
        Scanner scanner = new Scanner(System.in);

        run(threadManager, env, scanner);
    }

    private static void run(ThreadManager threadManager, Environment env, Scanner scanner) {

        String readString = scanner.nextLine();
        while (readString != null) {
            Lexer lexer = new Lexer(readString);
            List<TokenInterface> tokens = null;
            try {
                tokens = lexer.parse();
            } catch (UnexpectedStringEnd e) {
                readString += scanner.nextLine();
                continue;
            }

            Interpolator interpolator = new Interpolator(env);
            interpolator.interpolate(tokens);

            Parser parser = new Parser(tokens);
            List<LexemInterface> lexems = parser.parse();

            // if assignment is the only one command, then do assignment
            if (lexems.size() == 1 && lexems.get(0) instanceof Assignment) {
                Assignment tmp = (Assignment) lexems.get(0);
                env.addSymbol(tmp.getVar(), tmp.getValue());
            }
            // if assignment is last command, i.e. .. | ... | x=5
            // then do assignment
            if (!lexems.isEmpty() && lexems.get(lexems.size() - 1) instanceof
                    Assignment) {
                Assignment tmp = (Assignment) lexems.get(lexems.size() - 1);
                env.addSymbol(tmp.getVar(), tmp.getValue());
            }

            // delete extra assignment
            lexems = lexems.stream().filter(t -> !(t instanceof Assignment))
                    .collect(Collectors.toList());

            if (!lexems.isEmpty()) {
                threadManager.run(lexems);

                printStream(threadManager.getStdout());
                System.out.println();
            }

            if (scanner.hasNextLine()) {
                readString = scanner.nextLine();
            } else {
                readString = null;
            }
        }
    }
}
