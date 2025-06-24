import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class OutputRedirector {
    public final PrintStream stdout = System.out;
    private PrintStream _current;

    public OutputRedirector() {
        _current = stdout;
        setStream();
    }

    public void setStream() {
        System.setOut(_current);
    }

    public void redirectTo(File file) {
        if (file == null) {
            _current = stdout;
            return;
        }

        PrintStream stream = getPrintStreamFromFile(file);

        if (stream != null) _current = stream;
        else _current = stdout;
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
