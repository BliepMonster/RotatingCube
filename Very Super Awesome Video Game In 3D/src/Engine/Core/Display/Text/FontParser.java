package Engine.Core.Display.Text;
import java.io.*;
import java.util.*;
import java.util.regex.*;

class CharacterInfo {
    int id;
    int x;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public int getXAdvance() {
        return xAdvance;
    }

    int y;
    int width;
    int height;
    int xOffset;
    int yOffset;
    int xAdvance;
    int page;
    int channel;

    public CharacterInfo(int id, int x, int y, int width, int height, int xOffset, int yOffset, int xAdvance, int page, int channel) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
        this.page = page;
        this.channel = channel;
    } public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return "CharacterInfo{id=" + id + ", x=" + x + ", y=" + y + ", width=" + width +
                ", height=" + height + ", xoffset=" + xOffset + ", yoffset=" + yOffset +
                ", xadvance=" + xAdvance + ", page=" + page + ", channel=" + channel + "}";
    }
}

public class FontParser {
    public static ArrayList<CharacterInfo> parse(String filePath) {
        return parseFile(filePath);
    }

    protected static ArrayList<CharacterInfo> parseFile(String filePath) {
        ArrayList<CharacterInfo> characters = new ArrayList<>();
        Pattern pattern = Pattern.compile("char id=(-?\\d+) x=(-?\\d+) y=(-?\\d+) width=(-?\\d+) height=(-?\\d+) xoffset=(-?\\d+) yoffset=(-?\\d+) xadvance=(-?\\d+) page=(-?\\d+) chnl=(-?\\d+)");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    int id = Integer.parseInt(matcher.group(1));
                    int x = Integer.parseInt(matcher.group(2));
                    int y = Integer.parseInt(matcher.group(3));
                    int width = Integer.parseInt(matcher.group(4));
                    int height = Integer.parseInt(matcher.group(5));
                    int xOffset = Integer.parseInt(matcher.group(6));
                    int yOffset = Integer.parseInt(matcher.group(7));
                    int xAdvance = Integer.parseInt(matcher.group(8));
                    int page = Integer.parseInt(matcher.group(9));
                    int channel = Integer.parseInt(matcher.group(10));

                    characters.add(new CharacterInfo(id, x, y, width, height, xOffset, yOffset, xAdvance, page, channel));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return characters;
    }
}
