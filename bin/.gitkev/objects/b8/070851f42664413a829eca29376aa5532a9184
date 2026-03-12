package commands;

import java.nio.file.*;
import java.util.List;

public class StatusCommand {
    public static void run() throws Exception {
        Path index = Paths.get(".gitkev/index");

        if (!Files.exists(index)) {
            System.out.println("Nothing tracked yet. Run 'add' first.");
            return;
        }

        List<String> lines = Files.readAllLines(index);

        if (lines.isEmpty()) {
            System.out.println("Index is empty.");
            return;
        }

        System.out.println("Tracked Uncommited files:");
        System.out.println("=".repeat(120));
        System.out.println("Hash                                       |  Path");
        System.out.println("-".repeat(120));
        for (String line : lines) {
            String[] parts = line.split(" ", 2);
            if (parts.length == 2) {
                System.out.printf("%-42s | %s%n", parts[0], parts[1]);
            }
        }
        System.out.println("=".repeat(120));
        System.out.println(lines.size() + " file(s) tracked.");
    }
}