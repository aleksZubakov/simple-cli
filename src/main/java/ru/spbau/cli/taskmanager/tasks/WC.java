package ru.spbau.cli.taskmanager.tasks;


import ru.spbau.cli.parser.lexems.Argument;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Implementations of wc utility
 */
public class WC implements TaskInterface {

    private List<Argument> args;
    private int totalWords;
    private int totalLines;
    private int totalChars;

    public WC() {
        totalWords = 0;
        totalLines = 0;
        totalChars = 0;
        args = null;

    }

    public WC(List<Argument> args) {
        this();
        this.args = args;
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        try {
            if (args.isEmpty()) {
                count(in, out);
            } else {
                /* TODO file not found error */
                for (Argument arg : args) {
                    count(arg.getValue(), out);
                }

                if (args.size() > 1) {
                    flushTotalValues(out);
                }
            }

            out.close();
        } catch (IOException e) {
            /* TODO normal exception handling */
            e.printStackTrace();
        }
    }

    /**
     * Count words, lines and bytes in given file
     *
     * @param fileName
     * @param out
     * @throws IOException
     */
    private void count(String fileName, OutputStream out) throws IOException {
        FileReader file = new FileReader(fileName);
        Scanner scanner = new Scanner(file);

        countFromInputStream(out, scanner);

        String toWrite = "     " + fileName + "\n";
        out.write(toWrite.getBytes());
        out.flush();
    }

    /**
     * Count words, lines and bytes in given stream
     *
     * @param in
     * @param out
     * @throws IOException
     */
    private void count(InputStream in, OutputStream out) throws IOException {
        countFromInputStream(out, new Scanner(in));

        out.write("      \n".getBytes());
        out.flush();
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

    private void flushTotalValues(OutputStream out) throws IOException {
        out.write(MessageFormat.format("     {0}     {1}     {2}", totalWords,
                totalLines, totalChars).getBytes());

        out.write("     total \n".getBytes());

        out.flush();
    }

}

