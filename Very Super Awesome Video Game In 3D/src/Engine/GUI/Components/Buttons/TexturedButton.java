package Engine.GUI.Components.Buttons;

import Engine.Core.Display.*;
import Engine.GUI.Components.Image;

public abstract class TexturedButton extends Image implements Button {
    public TexturedButton(float x, float y, float w, float h, Texture t) {
        super(x,y,w,h, t);
    } public void detectClick(double x, double y) {
        if(x > this.x && x < this.x+width && y > this.y && y < this.y+height) {
            clicked = true;
            invoke();
        }
    }
}
