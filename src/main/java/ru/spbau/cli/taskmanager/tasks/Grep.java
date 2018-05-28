package ru.spbau.cli.taskmanager.tasks;

import org.apache.commons.cli.*;
import ru.spbau.cli.exceptions.IllegalStream;
import ru.spbau.cli.parser.lexems.Argument;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Implementation of echo utility
 */
public class Grep implements TaskInterface {

    private final static Options options;
    private final List<Argument> args;
    private CommandLine parsed;
    private String regexp = null;
    private String fileName = null;

    static {
        options = new Options();
        Option currentOption = new Option("i", "ignore-case", false,
                "Perform case insensitive matching.  By default, grep is case sensitive.");
        options.addOption(currentOption);
        currentOption = new Option("w", "word-regexp", false,
                "The expression is searched for as a word");
        options.addOption(currentOption);


        currentOption = new Option("A", "after-context", true,
                "Print num lines of trailing context after each match.");
        options.addOption(currentOption);
    }

    public Grep(List<Argument> args) {
        this.args = args;
    }

    private void parseArguments() {
        CommandLineParser parser = new DefaultParser();

        if (args.size() < 1) {
            throw new IllegalArgumentException("too few arguments for grep");
        }

        if (args.size() == 1) {
            regexp = args.get(0).getValue();
        }

        int stopIdx = args.size() - 2;
        if (args.size() > 2) {
            String[] stringArgs = args.subList(0, stopIdx)
                    .stream().map(Argument::getValue)
                    .toArray(String[]::new);

            try {
                parsed = parser.parse(options, stringArgs);
            } catch (ParseException e) {
                throw new IllegalArgumentException("illegal argument for grep");
            }
        }
        regexp = args.get(stopIdx).getValue();
        fileName = args.get(stopIdx + 1).getValue();
    }

    @Override
    public void run(InputStream in, OutputStream out) {
        try {
            parseArguments();

            List<String> allLines;
            if (fileName != null) {
                allLines = Files.readAllLines(Paths.get(fileName));
            } else {
                Scanner s = new Scanner(in).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";

                allLines = Arrays.asList(result.split("\\r?\\n"));
            }

            String ignore = parsed.hasOption("i") ? "(?i)" : "";
            String onlyWords = parsed.hasOption("w") ? "\\b" : "";

            regexp = ignore + "(" + onlyWords + regexp + onlyWords + ")";
            Pattern predicate = Pattern.compile(regexp);


            int howMuchPrint = Integer.parseUnsignedInt(parsed.getOptionValue("A", "0"));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));


            for (int i = 0; i < allLines.size(); i++) {
                Matcher m = predicate.matcher(allLines.get(i));
                if (!m.find()) {
                    continue;
                }

                int row = i;
                int printUntil = row + howMuchPrint + 1;
                printUntil = printUntil > allLines.size() ? allLines.size() - 1 : printUntil;

                for (; row < printUntil; row++) {
                    String s = allLines.get(row);
                    bufferedWriter.write(allLines.get(row));
                    bufferedWriter.write("\n");
                }
            }
            bufferedWriter.write("\n");
            bufferedWriter.close();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("-A argument should be number");
        } catch (IOException e) {
            throw new IllegalStream("grep cannot read or write to stream");
        }


    }

}
