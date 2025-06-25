import java.io.File;
import java.io.IOException;
import java.util.function.LongFunction;

public class ExecutableCommand extends BaseCommand {
    private final String _executable;
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

        File out = Logger.getOutput();
        File err = Logger.getErr();
        boolean append = Logger.isAppend();

        try {
            if (out != null) {
                if (append) process.redirectOutput(ProcessBuilder.Redirect.appendTo(out));
                else process.redirectOutput(out);
            }
            if (err != null) {
                if (append) process.redirectError(ProcessBuilder.Redirect.appendTo(err));
                else process.redirectError(err);
            }

            Process p = process.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            Logger.err("Error executing " + _executable + ": " + e.getMessage());
        }
    }
}
