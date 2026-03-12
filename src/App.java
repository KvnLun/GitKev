import commands.AddCommand;
import commands.InitCommand;

public class App {

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Provide a command to run the program.");
            return;
        }

        System.out.println("Running command: " + args[0]);

        switch (args[0]) {
            case "init":
                InitCommand.run();
                break;
            case "add":
                if (args.length < 2) {
                    System.out.println("Please provide a file to add.");
                    return;
                }
                //HashObjectCommand.run(args[1]);
                AddCommand.run(args[1]);
                break;
            case "commit":
                if (args.length < 2) {
                    System.out.println("Please provide a commit message.");
                    return;
                }
                commands.CommitCommand.run(args[1]);
                break;
            case "log":
                commands.LogCommand.run();
                break;
            case "checkout":
                if (args.length < 2) {
                    System.out.println("Provide commit hash to checkout");
                    return;
                }
                commands.CheckoutCommand.run(args[1]);
                break;
            case "status":
                commands.StatusCommand.run();
                break;
            case "status-detail":
                if (args.length < 2) {
                    System.out.println("Provide commit hash to show details");
                    return;
                }
                commands.LogDetailCommand.run(args[1]);
                break;
            case "help":
                System.out.println("Available commands:");
                System.out.println("=".repeat(120));
                System.out.println("init - Initialize a new repository");
                System.out.println("add <file> - Add a file to the staging area");
                System.out.println("commit <message> - Commit staged changes with a message");
                System.out.println("log - Show commit history");
                System.out.println("checkout <commitHash> - Checkout a specific commit");
                System.out.println("status - Show the status of the repository");
                System.out.println("status-detail <commitHash> - Show details of a specific commit");
                System.out.println("=".repeat(120));
                break;
            default:
                System.out.println("Unknown command");
        }
    }
}