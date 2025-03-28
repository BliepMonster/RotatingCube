package Engine.Core.Display;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Screenshot {
    /**
     * Takes a screenshot in a certain file path.
     * @param filePath The path.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    public static void takeScreenshot(String filePath, int width, int height) {
        // Allocate a ByteBuffer to store the pixels (RGBA format)
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        // Read pixels from the frameBuffer
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        // Create a BufferedImage to store the pixels
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = (x + (width * (height - y - 1))) * 4;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                int a = buffer.get(i + 3) & 0xFF;

                // Set the pixel color in the BufferedImage
                image.setRGB(x, y, ((a << 24) | (r << 16) | (g << 8) | b));
            }
        }

        // Save the BufferedImage to a file
        try {
            ImageIO.write(image, "PNG", new File(filePath));
            System.out.println("Screenshot saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
