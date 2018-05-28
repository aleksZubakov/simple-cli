package ru.spbau.cli.taskmanager.tasks;


import ru.spbau.cli.exceptions.IllegalStream;
import ru.spbau.cli.parser.lexems.Argument;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Implementations of wc utility
 */
public class WC implements TaskInterface {

    private final List<Argument> args;
    private int totalWords;
    private int totalLines;
    private int totalChars;

    public WC() {
        this(Collections.emptyList());
    }

    public WC(List<Argument> args) {
        totalWords = 0;
        totalLines = 0;
        totalChars = 0;
        this.args = args;
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        if (args.isEmpty()) {
            count(in, out);
        } else {

            for (Argument arg : args) {
                count(arg.getValue(), out);
            }

            if (args.size() > 1) {
                flushTotalValues(out);
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            throw new IllegalStream("WC cannot close output stream" + e.getMessage());
        }
    }

    /**
     * Count words, lines and bytes in given file
     *
     * @param fileName
     * @param out
     * @throws IOException
     */
    private void count(String fileName, OutputStream out) {
        try (FileReader file = new FileReader(fileName);
             Scanner scanner = new Scanner(file)) {

            countFromInputStream(out, scanner);
            String toWrite = "     " + fileName + "\n";
            out.write(toWrite.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new IllegalStream("WC cannot write into stream: " + e.getMessage());
        }
    }

    /**
     * Count words, lines and bytes in given stream
     *
     * @param in
     * @param out
     * @throws IOException
     */
    private void count(InputStream in, OutputStream out) {
        try {
            countFromInputStream(out, new Scanner(in));

            out.write("      \n".getBytes());
            out.flush();
        } catch (IOException e) {
            throw new IllegalStream("WC cannot write into stream: " + e.getMessage());
        }
    }


    /**
     * Helper words, lines and bytes in given stream
     *
     * @param in
     * @param out
     * @throws IOException
     */
    private void countFromInputStream(OutputStream out, Scanner in) throws IOException {
        int words = 0;
        int lines = 0;
        int chars = 0;

        while (in.hasNextLine()) {
            lines++;
            String line = in.nextLine();
            chars += line.getBytes().length + 1;
            words += new StringTokenizer(line).countTokens();
        }

        totalWords += words;
        totalLines += lines;
        totalChars += chars;

        out.write(MessageFormat.format("     {0}     {1}     {2}", words,
                lines, chars).getBytes());

        out.flush();
    }

    private void flushTotalValues(OutputStream out) {

        try {
            out.write(MessageFormat.format("     {0}     {1}     {2}", totalWords,
                    totalLines, totalChars).getBytes());

            out.write("     total \n".getBytes());

            out.flush();
        } catch (IOException e) {
            throw new IllegalStream("WC cannot flush total values: " + e.getMessage());
        }
    }

}

