package app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class LogicFramework {
    public static void main(String[] args) {
        
        try {
            Options options = buildOptions();
            CommandLine commandLine = new DefaultParser().parse(options, args);

            if(hasNoArguments(commandLine)) 
                throw new ParseException("Invalid arguments.");
            
            else if (commandLine.hasOption("h"))
                printHelp(options);

            else {

                if(commandLine.hasOption("g")) {

                }

                if(commandLine.hasOption("s")) {

                }

                if(commandLine.hasOption("v")) {

                }

            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Run with --help to check possible arguments.");
        }

    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Show possible commands.");
        options.addOption("g", "generate", false, "Generates a input.");
        options.addOption("s", "solve", false, "Solves a given input.");
        options.addOption("v", "verify", false, "Verifies two outputs.");
        return options;
    }

    private static boolean hasNoArguments(CommandLine commandLine) {
        return commandLine.getArgs().length == 0;
    }

    private static void printHelp(Options options) {
        new HelpFormatter().printHelp("LogicFramework [options]", options);
    }
}
