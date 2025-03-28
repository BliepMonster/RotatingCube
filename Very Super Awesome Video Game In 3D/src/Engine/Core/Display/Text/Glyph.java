package Engine.Core.Display.Text;

public class Glyph {
    protected int x, y, width, height, xOffset, yOffset, xAdvance;

    public Glyph(int x, int y, int w, int h, int xOff, int yOff, int xAdv) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        xOffset = xOff;
        yOffset = yOff;
        xAdvance = xAdv;
    } public int getY() {
        return y;
    } public int getX() {
        return x;
    } public int getXOffset() {
        return xOffset;
    } public int getYOffset() {
        return yOffset;
    } public int getXAdvance() {
        return xAdvance;
    } public int getHeight() {
        return height;
    } public int getWidth() {
        return width;
    }
}
