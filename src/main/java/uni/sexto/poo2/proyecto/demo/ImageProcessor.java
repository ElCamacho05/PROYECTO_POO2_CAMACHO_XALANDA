package uni.sexto.poo2.proyecto.demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

public class ImageProcessor {

    private static final String INPUT_DIR = "demo/src/main/java/uni/sexto/poo2/proyecto/demo/downloads";
    private static final String OUTPUT_DIR = "demo/src/main/java/uni/sexto/poo2/proyecto/demo/filtered";

    private static final ExecutorService processingExecutor = Executors.newFixedThreadPool(5);

    public static void processImagesFromDirectory() throws IOException {
        File dir = new File(INPUT_DIR);
        File[] imageFiles = dir.listFiles((d, name) ->
                name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

        // If there's no files in directory, return to MeinClass
        if (imageFiles == null || imageFiles.length == 0) {
            System.out.println("Files not found in: " + INPUT_DIR);
            return;
        }

        for (File file : imageFiles) {
            processingExecutor.submit(() -> {
                try {
                    BufferedImage image = ImageIO.read(file);
                    String name = file.getName();
                    String baseName = name.substring(0, name.lastIndexOf('.'));
                    String ext = name.substring(name.lastIndexOf('.') + 1);

                    saveFilteredImage(applySepia(image), baseName + "_sepia", ext);
                    saveFilteredImage(applyBlackAndWhite(image), baseName + "_bw", ext);
                    saveFilteredImage(applySharpen(image), baseName + "_sharpen", ext);

                    System.out.println("Processing: " + name);

                } catch (IOException e) {
                    System.err.println("Error while processing image: " + file.getName());
                }
            });
        }

        shutdownExecutor();
    }

    private static void saveFilteredImage(BufferedImage img, String name, String ext) throws IOException {
        File outFile = new File(OUTPUT_DIR, name + "." + ext);
        ImageIO.write(img, ext, outFile);
    }

    private static BufferedImage applyBlackAndWhite(BufferedImage original) {
        BufferedImage bw = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = bw.getGraphics();
        g.drawImage(original, 0, 0, null);
        g.dispose();
        return bw;
    }

    private static BufferedImage applySepia(BufferedImage img) {
        BufferedImage sepia = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int r = (int)(0.393 * c.getRed() + 0.769 * c.getGreen() + 0.189 * c.getBlue());
                int g = (int)(0.349 * c.getRed() + 0.686 * c.getGreen() + 0.168 * c.getBlue());
                int b = (int)(0.272 * c.getRed() + 0.534 * c.getGreen() + 0.131 * c.getBlue());
                sepia.setRGB(x, y, new Color(Math.min(255, r), Math.min(255, g), Math.min(255, b)).getRGB());
            }
        }
        return sepia;
    }

    private static BufferedImage applySharpen(BufferedImage img) {
        float[] kernel = {
                0.f, -1.f, 0.f,
                -1.f, 5.f, -1.f,
                0.f, -1.f, 0.f
        };
        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, kernel));
        return op.filter(img, null);
    }

    private static void shutdownExecutor() {
        processingExecutor.shutdown();
        try {
            if (!processingExecutor.awaitTermination(2, TimeUnit.MINUTES)) {
                processingExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            processingExecutor.shutdownNow();
        }
    }
}
