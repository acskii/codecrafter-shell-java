import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Logger {
    public static final PrintStream stdout = System.out;
    private static PrintStream _output = stdout;
    private static File _outputFile;
    private static File _errFile;
    private static PrintStream _err = stdout;

    public static void set(String outPath, String errPath) {
        try {
            if (outPath == null) {
                _output = stdout;
                _outputFile = null;
            } else {
                _outputFile = getFileFromPath(outPath);
                _output = new PrintStream(new FileOutputStream(_outputFile, true));
            }
            if (errPath == null) {
                _err = stdout;
                _errFile = null;
            } else {
                _errFile = getFileFromPath(errPath);
                _err = new PrintStream(new FileOutputStream(_errFile, true));
            }
        } catch (FileNotFoundException e) {
            _output = stdout;
            _err = stdout;
            _outputFile = _errFile = null;
        }
    }

    public static File getOutput() {
        return _outputFile;
    }

    public static File getErr() {
        return _errFile;
    }

    private static File getFileFromPath(String path) {
        return new File(path);
    }

    public static void output(String message) {
        _output.println(message);
    }

    public static void err(String message) {
        _err.println(message);
    }
}
