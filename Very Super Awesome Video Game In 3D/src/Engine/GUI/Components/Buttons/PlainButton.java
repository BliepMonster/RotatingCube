package Engine.GUI.Components.Buttons;

import Engine.Core.Display.*;
import Engine.Core.Display.Text.Font;
import Engine.GUI.Components.Component;

import java.awt.*;

import static java.awt.Color.*;

public abstract class PlainButton extends Component implements Button {
    protected String text;
    protected boolean clicked = false;
    protected Font font;
    protected Color color, textColor;
    public PlainButton(String text, Color c, Font font, Color textColor, float x, float y, float w, float h) {
        super(x,y,w,h);
        this.text = text;
        this.font = font;
        this.color = c;
        this.textColor = textColor;
    }
    public PlainButton(String text, Color c, Font font, float x, float y, float w, float h) {
        super(x,y,w,h);
        this.text = text;
        this.font = font;
        this.color = c;
        this.textColor = BLACK;
    }
    public PlainButton(String text, Font font, float x, float y, float w, float h) {
        super(x,y,w,h);
        this.text = text;
        this.font = font;
        this.color = WHITE;
        this.textColor = BLACK;
    }
    public void display(Screen screen) {
        if (clicked) {
            screen.draw.rect(Color.GRAY,x,y,width,height);
        }
        else {
            screen.draw.rect(color, x, y, width, height);
        }
        double size = font.getSizeForWidth(text, width);
        screen.draw.text(text, x, y+(height/2)-(font.getHeight(size)/2), font, size, textColor);
        clicked = false;
    } public void detectClick(double x, double y) {
        if(x > this.x && x < this.x+width && y > this.y && y < this.y+height) {
            clicked = true;
            invoke();
        }
    } public void detectContinuousPress(double x, double y) {

    } public void setText(String t) {
        this.text = t;
    } public void setColor(Color c) {
        this.color = c;
    } public void setTextColor(Color c) {
        this.textColor = c;
    }
}
