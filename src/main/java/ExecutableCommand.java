import java.io.IOException;

public class ExecutableCommand extends BaseCommand {
    private String _executable;
    private String[] _parameters;

    public ExecutableCommand(String executable, String[] args) {
        super(args);
        _executable = executable;
        setArgs(args);
    }

    @Override
    public void setArgs(String[] args) {
        _parameters = args;
    }

    @Override
    public void execute() {
        String[] commandLine = new String[_parameters.length + 1];
        commandLine[0] = _executable;
        System.arraycopy(_parameters, 0, commandLine, 1, _parameters.length);

        ProcessBuilder process = new ProcessBuilder(commandLine);
        process.inheritIO();

        try {
            Process p = process.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing " + _executable + ": " + e.getMessage());
        }
    }
}
