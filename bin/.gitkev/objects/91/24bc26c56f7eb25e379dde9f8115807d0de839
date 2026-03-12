package commands;

import java.nio.file.*;
import storage.Hasher;
import storage.ObjectStore;

public class HashObjectCommand {
    public static void run(String file) throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(file));
        String hash = Hasher.sha1(data);
        ObjectStore.writeObject(hash, data);

        System.out.println("Saved object with hash: " + hash);
    }
}
