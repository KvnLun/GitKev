package commands;

import java.nio.file.*;
import java.time.Instant;
import storage.*;

public class CommitCommand {

    public static void run(String message) throws Exception {

        // Guard: nothing staged
        Path indexPath = Path.of(".gitkev/index");
        if (!Files.exists(indexPath) || Files.readString(indexPath).isBlank()) {
            System.out.println("Nothing to commit. Run 'add' first.");
            return;
        }

        // Read current HEAD
        Path headPath = Path.of(".gitkev/refs/main");
        String parentHash = "";
        if (Files.exists(headPath)) {
            parentHash = Files.readString(headPath).trim();
        }

        // Read index
        String index = Files.readString(indexPath);

        // Build commit content
        StringBuilder commitBuilder = new StringBuilder();
        commitBuilder.append("message:").append(message).append("\n");
        commitBuilder.append("timestamp:").append(Instant.now()).append("\n");
        if (!parentHash.isEmpty()) {
            commitBuilder.append("parent:").append(parentHash).append("\n");
        }
        commitBuilder.append("files:\n").append(index);

        byte[] commitBytes = commitBuilder.toString().getBytes();
        String hash = Hasher.sha1(commitBytes);

        // Save commit object
        ObjectStore.writeObject(hash, commitBytes);

        // Update HEAD and clear index
        Files.writeString(headPath, hash);
        Files.writeString(indexPath, "");

        System.out.println("Committed: " + hash);
        System.out.println("Message:   " + message);
        System.out.println("Parent:    " + (parentHash.isEmpty() ? "(root commit)" : parentHash));
    }
}