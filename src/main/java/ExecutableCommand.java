import java.io.File;
import java.io.IOException;

public class ExecutableCommand extends BaseCommand {
    private final String _executable;
    private String[] _parameters;
    private File _output;

    public ExecutableCommand(String executable, String[] args, File outputStream) {
        super(args);
        _executable = executable;
        _output = outputStream;
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
            Process p = (_output == null) ? process.start() : process.redirectOutput(_output).start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing " + _executable + ": " + e.getMessage());
        }
    }
}
