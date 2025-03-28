package Engine.Core.Display;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

import javax.imageio.*;
import static org.lwjgl.opengl.GL11.*;

import java.awt.image.*;
import java.io.*;
import java.nio.*;

public class Texture {
    protected int id, width, height;

    /**
     * Generates a Texture object from the file path.
     * @param filename name of the file in question
     */
    public Texture(String filename) {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();

            int[] pixels_raw = new int[width*height];
            bi.getRGB(0, 0, width, height, pixels_raw, 0, width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = pixels_raw[i * height + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF)); //RED
                    pixels.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
                    pixels.put((byte) (pixel & 0xFF));         //BLUE
                    pixels.put((byte) ((pixel >> 24) & 0xFF)); //ALPHA
                }
            }
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            pixels.flip();
            id = glGenTextures();
            bind();
            //Setup wrap mode
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * Returns the texture's width. Nothing special.
     * @return The width variable of the texture.
     */
    public int getWidth() {
        return width;
    } public int getHeight() {
        return height;
    } public int getId() {
        return id;
    }
}
