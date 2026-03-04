package commands;

import java.nio.file.*;
import storage.*;

public class CommitCommand {

    public static void run(String message) throws Exception {

        // Read current HEAD
        Path headPath = Path.of(".gitkev/refs/main");
        String parentHash = "";
        if (Files.exists(headPath)) {
            parentHash = Files.readString(headPath).trim();
        }

        // Read index
        String index = Files.readString(Path.of(".gitkev/index"));

        // Build commit content
        StringBuilder commitBuilder = new StringBuilder();
        commitBuilder.append("message:").append(message).append("\n");
        if (!parentHash.isEmpty()) {
            commitBuilder.append("parent:").append(parentHash).append("\n");
        }
        commitBuilder.append("files:\n").append(index);

        byte[] commitBytes = commitBuilder.toString().getBytes();
        String hash = Hasher.sha1(commitBytes);

        // Save commit
        ObjectStore.writeObject(hash, commitBytes);

        // Update HEAD
        Files.writeString(headPath, hash);
        Files.writeString(Path.of(".gitkev/index"), ""); // Clear index after commit

        System.out.println("Committed: " + hash);
    }
}