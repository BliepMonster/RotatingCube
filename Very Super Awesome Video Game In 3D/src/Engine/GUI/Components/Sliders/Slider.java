package Engine.GUI.Components.Sliders;


import Engine.Core.Display.Screen;
import Engine.GUI.Components.Component;

import java.awt.*;

public class Slider extends Component {
    protected Color c1, c2, c3, c4;
    protected double value;
    protected double min, max;
    public Slider(float x, float y, float w, float h, Color c1, Color c2, Color c3, Color c4, double min, double max, double val) {
        super(x,y,w,h);
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        value = val;
        if (min < max) {
            this.min = min;
        } else {
            this.min = max-1;
        }
        this.max = max;
    }
    public double getValue() {
        return value;
    } public void setValue(double v) {
        if (v > min && v < max) {
            value = v;
        }
    }
    public void display(Screen screen) {
        screen.draw.rect(c2, x, y+(2*0.1f/32), width, 0.3f/32);
        screen.draw.rect(c1, x, y+(3*0.1f/32), width, .1f/32);
        screen.draw.rect(c3, (float) (x + (float) ((float) value-min) *  (width/(max-min))), y, (float) 3* .1f/32, 0.7f/32);
        screen.draw.rect(c4, (float) (x + (float) ((float) value-min) * (width/(max-min)) + .1f/32), y, .1f/32, 0.7f/32);
    } public void detectClick(double x1, double y1) {
        if (y1 <= this.y + .7f/32 && y1 >= this.y && x1 <= this.x+this.width && x1 >= this.x) {
            float relativeXPos = (float) -(this.x - x1);
            value = min + relativeXPos / (width/(max-min));
        }
    } public void detectContinuousPress(double x1, double y1) {
        if (y1 <= this.y + .7f/32 && y1 >= this.y && x1 <= this.x+this.width && x1 >= this.x) {
            float relativeXPos = (float) -(this.x - x1);
            value = min + relativeXPos / (width/(max-min));
        }
    }
}
