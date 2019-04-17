package arguments;

import org.apache.commons.cli.Options;

public class Arguments {

    private boolean isHelpCommand;
    private boolean generateInput;
    private boolean solveInput;
    private boolean verifyOutput;
    
    private Options fullOptions;

    public Arguments(Options fullOptions) {
        this.fullOptions = fullOptions;
        isHelpCommand = false;
        generateInput = true;
        solveInput = true;
        verifyOutput = false;
    }

    public Options getPossibleOptions() {
        return fullOptions;
    }

    public boolean isHelpCommand() {
        return isHelpCommand;
    }

    public void setHelpCommand(boolean isHelpCommand) {
        this.isHelpCommand = isHelpCommand;
    }

    public boolean generateInput() {
        return generateInput;
    }

    public void setGenerateInput(boolean generateInput) {
        this.generateInput = generateInput;
    }

    public boolean solveInput() {
        return solveInput;
    }

    public void setSolveInput(boolean solveInput) {
        this.solveInput = solveInput;
    }

    public boolean verifyOutput() {
        return verifyOutput;
    }

    public void setVerifyOutput(boolean verifyOutput) {
        this.verifyOutput = verifyOutput;
    }

}