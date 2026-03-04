package commands;
import storage.*;
import java.nio.file.*;
public class AddCommand {
    public static void run(String file) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(file));
        String hash = Hasher.sha1(data);
        ObjectStore.writeObject(hash, data);
        Files.writeString(Paths.get(".gitkev/index"), hash + " " + file + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Added file " + file + " with hash: " + hash);
    }
}
