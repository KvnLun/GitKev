package commands;

import java.nio.file.Files;
import java.nio.file.Path;

public class LogDetailCommand {
    //Display all files in the commit, and their content (like git show <hash>)
    public static void run(String hash) throws Exception {
        // Read commit object
        Path commitPath = Path.of(
            ".gitkev/objects",
            hash.substring(0, 2),
            hash.substring(2)
        );

        if (!Files.exists(commitPath)) {
            System.out.println("Commit not found: " + hash);
            return;
        }

        String commitData = Files.readString(commitPath);

        String message = "";
        String parent = "";
        StringBuilder filesBuilder = new StringBuilder();
        
        for (String line : commitData.split("\n")) {
            if (line.startsWith("message:")) {
                message = line.substring(8);
            } else if (line.startsWith("parent:")) {
                parent = line.substring(7);
            } else if (line.startsWith("files:")) {
                // Start of files section
                continue;
            } else {
                filesBuilder.append(line).append("\n");
            }
        }
        System.out.println("=".repeat(100));
        System.out.println("My Gitkev Commit Details");
        System.out.println("Commit Details For: " + hash);
        System.out.println("=".repeat(100));
        System.out.println("Commit: " + hash);
        System.out.println("Message: " + message);
        System.out.println("Date:    " + Files.getLastModifiedTime(commitPath));
        if (!parent.isEmpty()) {
            System.out.println("Parent: " + parent);
        }
        System.out.println();
        System.out.println("Files:");
        System.out.println(filesBuilder.toString());
    }    
}
