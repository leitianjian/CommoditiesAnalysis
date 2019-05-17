import java.io.File;
import java.nio.file.Path;

public class testSystemPath {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        File project = new File(System.getProperty("user.dir"));
        File[] projectFiles =
        project.listFiles();
        for (File f: projectFiles){
            System.out.println(f.getPath());
        }
    }
}
