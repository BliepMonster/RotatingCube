package Engine.Core.Display.Text;

import Engine.Core.Display.Texture;

import java.util.*;

import static Engine.Core.Display.Text.FontParser.*;

public class Font {
    protected Texture bitmap = new Texture("resources/font/Unnamed.png");
    protected HashMap<Character, Glyph> font = new HashMap<>();
    public Font() {
        ArrayList<CharacterInfo> chars = parse("resources/font/Unnamed.txt");;
        for (CharacterInfo character : chars) {
            Glyph g;
            char c = (char) character.getID();
            g = new Glyph(character.getX(),
                    character.getY(),
                    character.getWidth(),
                    character.getHeight(),
                    character.getXOffset(),
                    character.getYOffset(),
                    character.getXAdvance());
            font.put(c, g);
        }
    }
    public Texture getBitmap() {
        return bitmap;
    } public Glyph get(char a) {
        return font.get(a);
    } public float getStringWidth(String s, double fontSize) {
        float w = 0;
        for (char c : s.toCharArray()) {
            w += (float) (.01*get(c).getXAdvance()/bitmap.getWidth()*fontSize);
        } return w;
    } public double getSizeForWidth(String s, float width) {
        float w = 0;
        for (char c : s.toCharArray()) {
            w += (float) (.01*get(c).getXAdvance()/bitmap.getWidth());
        } return width/w;
    }
    public float getHeight(double fontSize) {
        return (float) (.01f*56/bitmap.getHeight()*fontSize);
    }
}
