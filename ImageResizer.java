import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.imgscalr.Scalr.OP_ANTIALIAS;

public class ImageResizer implements Runnable {

    private File[] files;
    private String dstFolder;
    private long start;

    private int targetWidth = 500;
    private int targetHeight = 500;
    private String dst = "C:\\Users\\Ñîôüÿ\\Desktop\\dst\\image.jpg";

    public ImageResizer(File[] files, String dstFolder, long start) {
        this.files = files;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public synchronized void run() {
        try {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }

                int newWidth = 300;
                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = new BufferedImage(
                        newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                );

                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++) {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);


                BufferedImage scr = ImageIO.read(new File(dst));
                Scalr.resize(scr, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC,
                        targetWidth, targetHeight, OP_ANTIALIAS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished after start: " + (System.currentTimeMillis() - start) + " ms " + Thread.currentThread().getName());
    }
}
