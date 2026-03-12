package commands;
import java.io.File;

public class InitCommand {
    public static void run(){
        new File(".gitkev/refs").mkdirs();
        new File(".gitkev/objects").mkdirs();

        System.out.println("Initialized empty GitKev repository in " + new File(".gitkev").getAbsolutePath());
    }
}
