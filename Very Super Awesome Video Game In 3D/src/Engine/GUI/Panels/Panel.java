package Engine.GUI.Panels;

import Engine.Core.Display.*;
import Engine.Core.Input.*;
import Engine.Core.Input.Events.*;
import Engine.GUI.Components.*;
import org.lwjgl.glfw.*;

import java.util.*;

import static Engine.Extra.*;

/**
 * TODO: FADE/GROW/SLIDE GRAPHICS LIKE IN THE OTHER ONE - PASS THAT TO COMPONENTS
 */
public class Panel {
    protected final ArrayList<Component> components = new ArrayList<>();
    protected Texture texture;
    protected int state;
    protected float x,
                    y,
                width,
                height;
    public static final int FADE_IN   = 0,
                            FADE_OUT  = 1,
                            SLIDE_IN  = 2,
                            SLIDE_0UT = 3,
                            GROW      = 4,
                            SHRINK    = 5,
                            NORMAL    = 6,
                            INACTIVE  = 7;

    public Panel(Texture txt, float x, float y, float w, float h, int state) {
        texture = txt;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.state = state;
    }
    public void manage(Screen screen, Console console) {
        screen.draw.image(texture, x, y, width, height);
        for (Component c : components) {
            for (Event e : console.input.events.get()) {
                if (e.type == MOUSE_DOWN && e.key == GLFW.GLFW_MOUSE_BUTTON_1) {
                    c.detectClick(Input.getMouseX(screen), Input.getMouseY(screen));
                }
            } if (Input.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
                c.detectContinuousPress(Input.getMouseX(screen), Input.getMouseY(screen));
            }
            c.display(screen);
        }
    } public void add(Component c) {
        components.add(c);
    } public Component[] getComponents() {
        return components.toArray(new Component[]{});
    }

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

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
    } public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
