import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public static String getAbsolutePath(String relative) {
        String root = ShellSession.getWD().getAbsolutePath();
        Path resolvedToRoot = Paths.get(root).resolve(relative);
        return resolvedToRoot.normalize().toString();
    }

    public static File getAbsoluteFile(String relative) {
        return new File(getAbsolutePath(relative));
    }

    public static String[] getLinesFromFile(File file) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Logger.err("Failed to load history from file.");
            return new String[] {};
        }

        return lines.toArray(String[]::new);
    }

    public static String[] getLinesFromFile(String filePath) {
        return getLinesFromFile(getAbsoluteFile(filePath));
    }
}
