import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    public static void writeLinesToFile(File file, String[] lines) {
        try {
            Files.write(file.toPath(), List.of(lines));
        } catch (IOException e) {
            Logger.err("Failed to write history from file.");
        }
    }

    public static void appendLinesToFile(File file, String[] lines) {
       try {
            Files.write(file.toPath(), List.of(lines), StandardOpenOption.APPEND);
        } catch (IOException e) {
            Logger.err("Failed to append history from file.");
        }
    }

    public static void writeLinesToFile(String filePath, String[] lines) {
        writeLinesToFile(getAbsoluteFile(filePath), lines);
    }

    public static void appendLinesToFile(String filePath, String[] lines) {
        appendLinesToFile(getAbsoluteFile(filePath), lines);
    }
}
