import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class OutputRedirector {
    public final PrintStream stdout = System.out;
    private PrintStream _current;
    private File _currentFile;

    public OutputRedirector() {
        _current = stdout;
        setSystemStream();
    }

    public File getCurrentOutput() {
        return _currentFile;
    }

    public void setSystemStream() {
        System.setOut(_current);
    }

    public void redirectTo(File file) {
        if (file == null) {
            _current = stdout;
            _currentFile = null;
            return;
        }

        PrintStream stream = getPrintStreamFromFile(file);

        if (stream != null) {
            _current = stream;
            _currentFile = file;
        } else {
            _current = stdout;
            _currentFile = null;
        }
    }

    private PrintStream getPrintStreamFromFile(File file) {
        try {
            FileOutputStream fileStream = new FileOutputStream(file, true);
            return new PrintStream(fileStream);

        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
