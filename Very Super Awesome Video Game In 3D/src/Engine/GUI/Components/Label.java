package Engine.GUI.Components;

import Engine.Core.Display.*;
import Engine.Core.Display.Text.Font;

import java.awt.*;

public class Label extends Component {
    protected String text;
    protected Color color;
    protected Font font;
    public Label(String t, Color c, Font f, float x, float y, float w, float h) {
        super(x,y,w,h);
        text = t;
        color = c;
        font = f;
    } public void display(Screen screen) {
        double fontSize = font.getSizeForWidth(text, width);
        screen.draw.text(text,x,y,font,fontSize,color);
    } public void detectClick(double e ,double f) {

    } public void detectContinuousPress(double e, double f) {

    } public void setText(String t) {
        text = t;
    } public String getText() {
        return text;
    } public Color getColor() {
        return color;
    } public void setColor(Color c) {
        color = c;
    }
}
