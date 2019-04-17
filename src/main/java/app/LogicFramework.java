package app;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import arguments.ArgumentParser;
import arguments.Arguments;

import com.google.inject.*;

public class LogicFramework {
    public static void main(String[] args) {
        
        try {
            Arguments arguments = ArgumentParser.parse(args);

            if (arguments.isHelpCommand())
                printHelp(arguments.getPossibleOptions());

            else {

                if(arguments.generateInput()) {

                }

                if(arguments.solveInput()) {

                }

                if(arguments.verifyOutput()) {

                }

            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Run with --help to check possible arguments.");
        }

    }

    private static void printHelp(Options options) {
        new HelpFormatter().printHelp("LogicFramework [options]", options);
    }
}
