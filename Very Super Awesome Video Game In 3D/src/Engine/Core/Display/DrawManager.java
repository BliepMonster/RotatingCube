package Engine.Core.Display;

import Engine.Core.Display.Text.*;
import Engine.Core.Display.Text.Font;
import org.lwjgl.opengl.*;

import java.awt.*;
import Engine.Core.ExceptionMessages.*;

import static java.awt.Color.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class responsible for drawing on the screen.
 */
public final class DrawManager {
    private final Screen screen;
    public DrawManager(Screen s) {
        screen = s;
    }
    /**
     * Draws a square.
     * @param color The color.
     * @param x1 The left X.
     * @param y1 The bottom Y.
     * @param height The size of the square. 1 = half of the height of the screen.
     */
    public void square(Color color, float x1, float y1, float height) {
        glDisable(GL_TEXTURE_2D);
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha()/255f);

        glBegin(GL_QUADS);
        int screenWidth = screen.getWidth();
        int screenHeight = screen.getHeight();
        float extendedX = (x1 * screenHeight) / screenWidth + (height * screenHeight) / screenWidth;
        // draw the vertices
        glVertex2f(extendedX, y1);
        glVertex2f((x1 * screenHeight) / screenWidth, y1);
        glVertex2f((x1 * screenHeight) / screenWidth, y1 + height);
        glVertex2f(extendedX, y1 + height);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        screen.addCall();
    }

    /**
     * Draw a rectangle on the screen, using slightly altered OpenGL textures, where the X-axis is multiplied by the aspect ratio of the screen.
     * @param color The color of the rectangle.
     * @param x1 The X value of the left side of the rectangle.
     * @param y1 The Y value of the bottom of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangles.
     */
    public void rect(Color color, float x1, float y1, float width, float height) {
        int screenHeight = screen.getHeight();
        int screenWidth = screen.getWidth();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        final float extendedX = x1 * screenHeight / screenWidth + (width * screenHeight) / screenWidth;
        glBegin(GL11.GL_QUADS);
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        GL11.glVertex2f(extendedX, y1);
        GL11.glVertex2f(x1 * screenHeight / screenWidth, y1);
        GL11.glVertex2f(x1 * screenHeight / screenWidth, y1 + height);
        GL11.glVertex2f(extendedX, y1 + height);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        screen.addCall();
    }

    /**
     * Draws a texture on the screen.
     * @param texture The texture being displayed.
     * @param x1 The left side of the texture on the screen.
     * @param y1 The bottom side of the texture on the screen.
     * @param width The width of the rectangle on which the texture is projected.
     * @param height The height of the rectangle on which the texture is projected.
     */
    public void image(Texture texture, float x1, float y1, float width, float height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        texture.bind();
        int screenWidth = screen.getWidth();
        int screenHeight = screen.getHeight();
        glEnable(GL_TEXTURE);
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_QUADS);
        float aspectRatio = (float) screenHeight / screenWidth;
        float extendedX = x1 * aspectRatio + width * aspectRatio;
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glTexCoord2f(0.0f, .0f);
        GL11.glVertex2f((x1 * screenHeight) / screenWidth, y1 + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(extendedX, y1 + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(extendedX, y1);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f((x1 * screenHeight) / screenWidth, y1);
        GL11.glEnd();
        screen.addCall();
    }

    /**
     * Draws a custom shape using the following point coordinates. This can not send failed calls.
     * @throws InvalidListSizeException When the length of Array X is not equal to the size of Array Y.
     * @param color The color.
     * @param x The array of x positions.
     * @param y The array of y positions. Has to be of the same length as x.
     */
    public void custom(Color color, float[] x, float[] y) throws InvalidListSizeException {
        if (x.length != y.length) {
            throw new InvalidListSizeException("Size of Array 'x' is not equal to the Size of Array 'y'.");
        }
        int screenHeight = screen.getHeight();
        int screenWidth = screen.getWidth();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        final float aspectRatio = (float) screenHeight /screenWidth;
        glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha()/255f);
        for (int i = 0; i < x.length; i++) {
            GL11.glVertex2f(x[i]*aspectRatio, y[i]*aspectRatio);
        }
        GL11.glEnd();
        screen.addCall();
    }

    /**
     * Draws a circle on the screen.
     * @param color The color of the circle.
     * @param xCenter The x position of the center of the circle.
     * @param yCenter The y position of the center of the circle.
     * @param radius The radius of the circle.
     * @see #oval(Color color, float xCenter, float yCenter, float radiusX, float radiusY) oval
     * @see #tintedImage(Color, Texture, float, float, float, float) tintedImage
     */
    public void circle(Color color, float xCenter, float yCenter, float radius) {
        tintedImage(color, new Texture("res/CIRCLE_WHITE.png"), xCenter-radius, yCenter-radius, 2*radius, 2*radius);
    }

    /**
     * Draws an oval on the screen.
     * @param color The color of the oval.
     * @param xCenter The center x coordinate.
     * @param yCenter The center y coordinate.
     * @param radiusX The width of the oval.
     * @param radiusY The height of the oval.
     * @see #circle(Color color, float xCenter, float yCenter, float radius) circle
     * @see #tintedImage(Color, Texture, float, float, float, float) tintedImage
     */
    public void oval(Color color, float xCenter, float yCenter, float radiusX, float radiusY) {
        tintedImage(color, new Texture("res/CIRCLE_WHITE.png"), xCenter-radiusX, yCenter-radiusY, 2*radiusX, 2*radiusY);
    }

    /**
     * Draws a Texture on the screen with a certain tint.
     * @param color The tint of the texture. DO NOT make this white, it'll draw the normal image.
     * @param texture The texture being drawn.
     * @param x1 The left x side.
     * @param y1 The bottom y side.
     * @param width The width of the texture.
     * @param height The height of the texture.
     * @see #image(, Texture texture, float x1, float y1, float width, float height) image
     * @see #imageWithAlphaModifier(Texture, float, float, float, float, float) imageWithAlphaModifier
     */
    public void tintedImage(Color color, Texture texture, float x1, float y1, float width, float height) {
        GL11.glColor4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
        texture.bind();
        glEnable(GL_TEXTURE);
        glEnable(GL_TEXTURE_2D);
        glBegin(GL11.GL_QUADS);
        int screenWidth = screen.getWidth();
        int screenHeight = screen.getHeight();
        float extendedX = (x1 * screenHeight) / screenWidth + (width * screenHeight) / screenWidth;
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f((x1 * screenHeight) / screenWidth, y1+height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(extendedX, y1+height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(extendedX, y1);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f((x1 * screenHeight) / screenWidth, y1);
        GL11.glEnd();
        screen.addCall();
    }

    /**
     * Draws a line on the screen.
     * @param color The color of the line.
     * @param x1 The start x position.
     * @param y1 The start y position.
     * @param x2 The end x position.
     * @param y2 The end y position.
     * @param width The width of the line.
     * @see #rect(Color, float, float, float, float) rect
     * @see #square(Color, float, float, float) square
     */
    public void line(Color color, float x1, float y1 ,float x2, float y2, float width) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        glBegin(GL11.GL_QUADS);
        int screenHeight = screen.getHeight();
        int screenWidth = screen.getWidth();
        float aspectRatio = (float) screenHeight/screenWidth;
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha()/255f);
        GL11.glVertex2f(x1*aspectRatio, y1);
        GL11.glVertex2f(x2*aspectRatio, y2);
        GL11.glVertex2f(x1*aspectRatio+width*aspectRatio, y1);
        GL11.glVertex2f(x2*aspectRatio+width*aspectRatio, y2);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        screen.addCall();
    }

    /**
     * Draws an image with a custom alpha.
     * @param texture The image as a Texture object.
     * @param x1 The start X position.
     * @param y1 The start Y position.
     * @param width The width.
     * @param height The height.
     * @param alpha The custom alpha value.
     */
    public void imageWithAlphaModifier(Texture texture, float x1, float y1, float width, float height, float alpha) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);

        texture.bind();
        glEnable(GL_TEXTURE);
        glEnable(GL_TEXTURE_2D);
        glBegin(GL11.GL_QUADS);
        int screenWidth = screen.getWidth();
        int screenHeight = screen.getHeight();
        float extendedX = (x1 * screenHeight) / screenWidth + (width * screenHeight) / screenWidth;
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f((x1 * screenHeight) / screenWidth, y1+height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(extendedX, y1+height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(extendedX, y1);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f((x1 * screenHeight) / screenWidth, y1);
        GL11.glEnd();
        screen.addCall();
    }
    /**
     * Draws a black {@link Glyph Glyph} representing a {@code char} on the screen. This is equal to calling {@link #character(char, float, float, Font, double, Color)} with the {@link java.awt.Color Color} argument as {@link java.awt.Color#BLACK Color.BLACK}.
     * @param c The {@code char} that needs to be drawn. <html><strong>This needs to exist in our font! Please only use normal characters from the Latin alphabet.</strong></html>
     * @param x The left x coordinate of the glyph.
     * @param y The bottom y coordinate of the glyph.
     * @param font The {@link Font} that is used.
     * @param fontSize The size of the font. The max size is calculated as follows: {@code 0.01*56/434*size}. This will mean that for every 1 font size, it will make it roughly 0.06% of the screen's height.
     * @see #paragraph(String, float, float, float, Font, double, Color)
     * @see #paragraph(String, float, float, float, Font, double)
     * @see #text(String, float, float, Font, double)
     * @see #text(String, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double, Color)
     */
    public void character(char c, float x, float y, Font font, double fontSize) {
        character(c, x, y, font, fontSize, BLACK);
    }
    /**
     * Draws a {@link String} of text on the screen, on one line. This is equal to calling {@link #text(String, float, float, Font, double, Color)} with the {@link java.awt.Color Color} argument as {@link java.awt.Color#BLACK Color.BLACK}.
     * @param s The {@link String} that needs to be displayed.
     * @param x The left x location.
     * @param y The bottom y location of the {@link String}.
     * @param font The {@link Font}.
     * @param fontSize The size. See {@link #character(char, float, float, Font, double)} for font size calculations.
     * @see #paragraph(String, float, float, float, Font, double, Color)
     * @see #paragraph(String, float, float, float, Font, double)
     * @see #text(String, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double)
     */
    public void text(String s, float x, float y, Font font, double fontSize) {
        text(s, x, y, font, fontSize, BLACK);
    }

    /**
     * Draws a formatted {@link String} on the screen. This is equal to calling {@link #paragraph(String, float, float, float, Font, double, Color)} with the {@link java.awt.Color Color} argument as {@link java.awt.Color#BLACK Color.BLACK}.
     * @param s The String that needs to be displayed. This will not care about {@code \n} or similar.
     * @param x The left x of the paragraph.
     * @param xMax The right border of the paragraph.
     * @param y The bottom of the top line of the paragraph.
     * @param font The {@link Font}.
     * @param fontSize The font size. See {@link #character(char, float, float, Font, double)} for size calculations.
     * @see #paragraph(String, float, float, float, Font, double, Color)
     * @see #text(String, float, float, Font, double)
     * @see #text(String, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double)
     */
    public void paragraph(String s, float x, float xMax, float y, Font font, double fontSize) {
        paragraph(s,x,xMax,y,font,fontSize,BLACK);
    }

    /**
     * Draws a {@code char} on the screen, in a certain {@link java.awt.Color Color}.
     * @param c The {@code char} that needs to be drawn.
     * @param x The left x coordinate.
     * @param y The bottom y coordinate.
     * @param font The {@link Font}.
     * @param fontSize The font size. See {@link #character(char, float, float, Font, double)} for calculations.
     * @param color The {@link java.awt.Color Color}.
     * @see #paragraph(String, float, float, float, Font, double, Color)
     * @see #paragraph(String, float, float, float, Font, double)
     * @see #text(String, float, float, Font, double)
     * @see #text(String, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double)
     */
    public void character(char c, float x, float y, Font font, double fontSize, Color color) {
        Texture bitmap = font.getBitmap();
        Glyph g = font.get(c);
        if (g != null) {
            float aspectRatio = (float) screen.getHeight()/screen.getWidth();
            int bitmapWidth = bitmap.getWidth();
            float glyphWidth = (float) g.getWidth()/bitmapWidth;
            int bitmapHeight = bitmap.getHeight();
            float glyphHeight = (float) g.getHeight()/bitmapHeight;
            float glyphX = (float) g.getX()/bitmapWidth;
            float glyphY = (float) g.getY()/bitmapHeight;
            float height = (float) (.01*fontSize*glyphHeight);
            float width = (float) (.01*fontSize*glyphWidth);
            glColor4f(color.getRed()/255f,color.getGreen()/255f,color.getBlue()/255f,color.getAlpha()/255f);
            bitmap.bind();
            glEnable(GL_TEXTURE);
            glEnable(GL_TEXTURE_2D);
            glBegin(GL_QUADS);
            GL11.glTexCoord2f(glyphX, glyphY);
            GL11.glVertex2f(x*aspectRatio, y+height);
            GL11.glTexCoord2f(glyphX+glyphWidth, glyphY);
            GL11.glVertex2f((x+width)*aspectRatio, y+height);
            GL11.glTexCoord2f(glyphX+glyphWidth, glyphY+glyphHeight);
            GL11.glVertex2f((x+width)*aspectRatio, y);
            GL11.glTexCoord2f(glyphX, glyphY+glyphHeight);
            GL11.glVertex2f(x*aspectRatio, y);
            GL11.glEnd();
            screen.addCall();
        }
    }
    /**
     * This function will convert a {@link String} to a {@link char[]}, and then use {@link #character(char, float, float, Font, double, Color)} to draw each character.
     * @param s The {@link String}.
     * @param x The left x coordinate.
     * @param y The bottom y coordinate.
     * @param font The {@link Font}.
     * @param fontSize The size. See {@link #character(char, float, float, Font, double)} for calculations.
     * @param color The {@link java.awt.Color Color}.
     * @see #paragraph(String, float, float, float, Font, double, Color)
     * @see #paragraph(String, float, float, float, Font, double)
     * @see #text(String, float, float, Font, double)
     * @see #character(char, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double)
     */
    public void text(String s, float x, float y, Font font, double fontSize, Color color) {
        char[] chars = s.toCharArray();
        Texture bitmap = font.getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int maxHeight = 56;
        for (char c : chars) {
            Glyph g = font.get(c);
            if (g != null) {
                float glyphHeight = (float) g.getHeight() / bitmapHeight;
                float height = (float) (.01 * fontSize * glyphHeight);
                float max = (float) (.01*fontSize*maxHeight/bitmapHeight);
                float advance = (float) (.01*g.getXAdvance()/bitmapWidth*fontSize);
                float xOffset = (float) (.01*g.getXOffset()/bitmapWidth*fontSize);
                float yOffset = (float) (.01*g.getYOffset()/bitmapWidth*fontSize);
                character(c, x+xOffset, y+max-height-yOffset, font, fontSize, color);
                x += advance;
            }
        }
    }
    /**
     * This function will convert a {@link String} to a {@link char[]}, and then use {@link #character(char, float, float, Font, double, Color)} to draw each character, taking into account the right border.
     * @param s The {@link String}.
     * @param x The left x coordinate.
     * @param xMax The right border of the paragraph.
     * @param y The bottom y coordinate of the top row of the paragraph.
     * @param font The {@link Font}.
     * @param fontSize The size. See {@link #character(char, float, float, Font, double)} for calculations.
     * @param color The {@link java.awt.Color Color}.
     * @see #paragraph(String, float, float, float, Font, double)
     * @see #text(String, float, float, Font, double)
     * @see #text(String, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double, Color)
     * @see #character(char, float, float, Font, double)
     */
    public void paragraph(String s, float x, float xMax, float y, Font font, double fontSize, Color color) {
        char[] chars = s.toCharArray();
        float start = x;
        Texture bitmap = font.getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int maxHeight = 56;
        for (char c : chars) {
            Glyph g = font.get(c);
            if (g != null) {
                float glyphHeight = (float) g.getHeight() / bitmapHeight;
                float height = (float) (.01 * fontSize * glyphHeight);
                float max = (float) (.01*fontSize*maxHeight/bitmapHeight);
                float advance = (float) (.01*g.getXAdvance()/bitmapWidth*fontSize);
                float xOffset = (float) (.01*g.getXOffset()/bitmapWidth*fontSize);
                float yOffset = (float) (.01*g.getYOffset()/bitmapWidth*fontSize);
                if (x+advance >= xMax) {
                    x = start;
                    y -= (float) (.01f*60f/bitmapHeight*fontSize);
                }
                character(c, x + xOffset, y + max - height - yOffset, font, fontSize, color);
                x += advance;
            } else if (c == '\n') {
                x = start;
                y -= (float) (.01f*60f/bitmapHeight*fontSize);
            }
        }
    }
}