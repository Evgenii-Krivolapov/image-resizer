import java.io.File;

public class Main {

    public static void main(String[] args) {
        String srcFolder = "C:\\Users\\Ρξτόÿ\\Desktop\\scr";
        String dstFolder = "C:\\Users\\Ρξτόÿ\\Desktop\\dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();
        int middle = files.length / 5;

        for (int i = 0; i < 5; i++) {
            File[] files5 = new File[files.length - middle];
            System.arraycopy(files, middle, files5, 0, files5.length);
            ImageResizer imageResizer = new ImageResizer(files5, dstFolder, start);
            new Thread(imageResizer).start();
        }
    }
}