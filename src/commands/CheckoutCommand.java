package commands;

import java.nio.file.*;

public class CheckoutCommand {

    public static void run(String hash) throws Exception {

        Path commitPath = Path.of(".gitkev/objects", hash.substring(0, 2), hash.substring(2));
        if (!Files.exists(commitPath)) {
            System.out.println("Commit not found: " + hash);
            return;
        }

        String commitData = Files.readString(commitPath);

        boolean filesSection = false;
        for (String line : commitData.split("\n")) {
            if (line.trim().equals("files:")) {
                filesSection = true;
                continue;
            }
            
            if (filesSection && !line.trim().isEmpty()) {
                // Split by space instead of colon
                String[] parts = line.trim().split(" ", 2);
                if (parts.length != 2) continue; // Skip malformed lines
                
                String objectHash = parts[0].trim();
                String fileName = parts[1].trim();

                // Read object
                Path objectPath = Path.of(".gitkev/objects", objectHash.substring(0, 2), objectHash.substring(2));
                
                if (!Files.exists(objectPath)) {
                    System.out.println("Object not found: " + objectHash);
                    continue;
                }
                
                byte[] content = Files.readAllBytes(objectPath);
                
                // Create parent directories if needed
                Path filePath = Path.of(fileName);
                if (filePath.getParent() != null) {
                    Files.createDirectories(filePath.getParent());
                }
                
                // Write to working directory
                Files.write(filePath, content);
                System.out.println("Restored: " + fileName);
            }
        }
        
        System.out.println("Checked out commit " + hash);
    }
}