package ru.spbau.cli.helpers;

/**
 * Enum Commands contains all existing commands names,
 * should be expanded if new commands are created.
 */
public enum Commands {
    echo("echo"),
    pwd("pwd"),
    cat("cat"),
    wc("wc"),
    exit("exit");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    /**
     * Returns true if given value is command, otherwise returns true
     *
     * @param value
     * @return
     */
    public static boolean isCommand(String value) {
        for (Commands cmd : Commands.values()) {
            if (cmd.getCommand().compareToIgnoreCase(value) == 0) {
                return true;
            }
        }

        return false;
    }
}


