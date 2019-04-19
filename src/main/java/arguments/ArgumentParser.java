package arguments;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {
  
    public static Arguments parse(String[] args) throws ParseException {
        Options options = buildOptions();
        CommandLine commandLine = new DefaultParser().parse(options, args);
        return parseArguments(options, commandLine);
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Show possible commands.");
        options.addOption("g", "generate", false, "Generates a input.");
        options.addOption("s", "solve", false, "Solves a given input.");
        options.addOption("c", "check", false, "Checks two outputs.");
        return options;
    }

    private static Arguments parseArguments(Options options, CommandLine commandLine) {
        Arguments arguments = new Arguments(options);

        boolean noArgumentsPassed = isEmpty(commandLine);

        boolean isHelpCommand = commandLine.hasOption("h");
        arguments.setHelpCommand(isHelpCommand);

        boolean generateInput = commandLine.hasOption("g") || noArgumentsPassed;
        arguments.setGenerateInput(generateInput);

        boolean solveInput = commandLine.hasOption("s") || noArgumentsPassed;
        arguments.setSolveInput(solveInput);

        boolean verifyOutput = commandLine.hasOption("c") || noArgumentsPassed;
        arguments.setVerifyOutput(verifyOutput);

        return arguments;
    }

    private static boolean isEmpty(CommandLine commandLine) {
        return commandLine.getOptions().length == 0;
    }
}