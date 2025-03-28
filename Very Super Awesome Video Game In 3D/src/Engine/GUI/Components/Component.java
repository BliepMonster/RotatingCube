package Engine.GUI.Components;

import Engine.Core.Display.*;

import java.io.*;

public abstract class Component implements Serializable {
    protected float x,
                    y,
                width,
               height;
    public Component(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    public abstract void display(Screen screen);
    public abstract void detectClick(double xPos, double yPos);
    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public abstract void detectContinuousPress(double x1, double y1);
}
