package app;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import arguments.ArgumentParser;
import arguments.Arguments;
import interfaces.*;
import services.InjectorModule;

import com.google.inject.*;

public class LogicFramework {

    private Generator generator;
    private Solver solver;
    private Verifier verifier;

    @Inject
    public LogicFramework(Generator generator, Solver solver, Verifier verifier) {
        this.generator = generator;
        this.solver = solver;
        this.verifier = verifier;
    }

    public static void main(String[] args) {

        try {
            Arguments arguments = ArgumentParser.parse(args);

            if (arguments.isHelpCommand())
                printHelp(arguments.getPossibleOptions());

            else {
                Injector injector = Guice.createInjector(new InjectorModule());
                LogicFramework logicFramework = injector.getInstance(LogicFramework.class);

                if (arguments.generateInput())
                    logicFramework.generateInput();

                if (arguments.solveInput())
                    logicFramework.solveInput();

                if (arguments.verifyOutput())
                    logicFramework.verifyOutput();

            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.out.println("Run with --help to check possible arguments.");
        }

    }

    public void generateInput() {
        System.out.println("GENERATING INPUT");
        System.out.println("================");
        generator.generateInput();
        System.out.println();
    }

    public void solveInput() {
        System.out.println("SOLVING");
        System.out.println("=======");
        solver.solveInput();
        System.out.println();
    }

    public void verifyOutput() {
        System.out.println("VERIFYING OUTPUT");
        System.out.println("=========");
        verifier.verifyOutput();
        System.out.println();
    }

    private static void printHelp(Options options) {
        new HelpFormatter().printHelp("LogicFramework [options]", options);
    }
}
