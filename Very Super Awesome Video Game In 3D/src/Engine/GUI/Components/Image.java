package Engine.GUI.Components;

import Engine.Core.Display.*;

import java.awt.*;

public class Image extends Component {
    protected Texture texture;
    protected boolean clicked = false;
    public Image(float x, float y, float w, float h, Texture t) {
        super(x,y,w,h);
        texture = t;
    }
    public void display(Screen screen) {
        if (clicked) {
            screen.draw.tintedImage(Color.GRAY, texture, x, y, width, height);
        }
        else {
            screen.draw.image(texture, x, y, width, height);
        } clicked  = false;
    }
    public void detectClick(double x, double y) {

    }
    public void detectContinuousPress(double x, double y) {

    }
    public void setImage(Texture t) {
        this.texture = t;
    }
    public Texture getImage() {
        return texture;
    }
}
