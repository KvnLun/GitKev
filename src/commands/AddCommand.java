package commands;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import storage.*;

public class AddCommand {
    public static void run(String file) throws Exception {
        if (file.equals(".")) {
            Files.writeString(Paths.get(".gitkev/index"), "",
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            addAll();
        } else {
            addFile(file);
        }
    }

    private static void addAll() throws Exception {
        Path root = Paths.get(".").toRealPath();
        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.getFileName().toString().equals(".gitkev")) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(".class")) {
                    System.out.println("Skipped (class file): " + file);
                    return FileVisitResult.CONTINUE;
                }
                try {
                    addFile(file.toAbsolutePath().toString()); // addFile must be static to call from anonymous class
                } catch (Exception e) {
                    System.err.println("Skipped: " + file + " (" + e.getMessage() + ")");
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Skipped (access denied): " + file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void addFile(String file) throws Exception {
        Path path = Paths.get(file);
        if (Files.isDirectory(path)) return;

        byte[] data = Files.readAllBytes(path);
        String hash = Hasher.sha1(data);
        ObjectStore.writeObject(hash, data);
        Files.writeString(Paths.get(".gitkev/index"), hash + " " + file + "\n",
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Added: " + file + " → " + hash);
    }
}