package storage;
import java.nio.file.*;
public class ObjectStore {
    public static void writeObject(String hash, byte[] data) throws Exception {
        String dir = hash.substring(0, 2);
        String file = hash.substring(2);
        Path objectDire = Path.of(".gitkev/objects", dir);
        if (!Files.exists(objectDire)) {
            Files.createDirectories(objectDire);
        }
        Path objectPath = objectDire.resolve(file);
        Files.write(objectPath, data);
    }
}
