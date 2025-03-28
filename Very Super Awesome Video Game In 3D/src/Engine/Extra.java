package Engine;

import java.awt.*;
import java.io.*;

/**
 * Miscellaneous variables and methods
 */
public final class Extra {
    /**
     * EVENT TYPES
     */
    public static final int KEY_DOWN = 1;
    public static final int KEY_UP = 2;
    public static final int MOUSE_DOWN = 3;
    public static final int MOUSE_UP = 4;
    //OTHER
    public static final java.util.Random random = new java.util.Random();

    /**
     * Generates a random color.
     * @return The color.
     * @see #randomColor(int) randomColor(int max)
     * @see #randomColor(int, int) randomColor(int min, int max)
     */
    public static Color randomColor() {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    /**
     * Takes a random color with values from 0 to max.
     * @param max The maximum color value of the R, G, and B values.
     * @return The random Color object.
     * @see #randomColor() randomColor
     * @see #randomColor(int, int) randomColor(int min, int max)
     */
    public static Color randomColor(int max) {
        return new Color(random.nextInt(max-1), random.nextInt(max-1), random.nextInt(max-1));
    }

    /**
     * Takes a random color between to colors.
     * @param min The minimum R,G,B values.
     * @param max The maximum R,G,B values.
     * @return The random Color object.
     * @see #randomColor(int) randomColor(int max)
     * @see #randomColor() randomColor
     */
    public static Color randomColor(int min, int max) {
        return new Color(random.nextInt(max - min) + min, random.nextInt(max - min) + min, random.nextInt(max - min) + min);
    }
    public static float calcDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
    } public static String loadAsString(String path) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch(IOException e) {
            System.err.println("Could not find the file at "+path+".");
        }
        return result.toString();
    }
}
