package Engine.Core.Input;

import Engine.Core.Display.Screen;
import Engine.Extra;
import Engine.Core.Input.Events.Event;
import Engine.Core.Input.Events.EventManager;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

/**
 * Class responsible for taking the User Input on the screen.
 */
public final class Input {
    private static final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static final boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;
    private static double scrollX, scrollY;
    public final EventManager events = new EventManager();

    private final GLFWKeyCallback keyboard;
    private final GLFWMouseButtonCallback mouseButtons;
    private final GLFWCursorPosCallback mouseMove;
    private final GLFWScrollCallback scroll;

    /**
     * Takes the X-position of the mouse.
     * @return The X-position of the mouse.
     * @see #getMouseY()
     */
    public static double getMouseX() {
        return mouseX;
    }
    /**
     * Takes the Y-position of the mouse.
     * @return The Y-position of the mouse.
     * @see #getMouseX()
     */
    public static double getMouseY() {
        return mouseY;
    }

    /**
     * Gives the X position of the scrollbar.
     * @return The scroll X
     * @see #getScrollY()
     */
    public static double getScrollX() {
        return scrollX;
    }

    /**
     * Gives the Y position of the scrollbar.
     * @return The scroll Y
     * @see #getScrollY()
     */
    public static double getScrollY() {
        return scrollY;
    }
    /**
     * Take the Keyboard Callback.
     * This should only be used in the DisplayScreen createCallbacks function, which is private.
     * @return The Keyboard GLFWKeyCallback
     */
    public GLFWKeyCallback getKeyboard() {
        return keyboard;
    }
    /**
     * Take the Mouse Movement Callback.
     * This should only be used in the DisplayScreen createCallbacks function, which is private.
     * @return The MouseMove GLFWCursorPosCallback
     */
    public GLFWCursorPosCallback getMouseMove() {
        return mouseMove;
    }
    /**
     * Take the Mouse Button Callback.
     * This should only be used in the DisplayScreen createCallbacks function, which is private.
     * @return The MouseMove GLFWMouseButtonCallback
     */
    public GLFWMouseButtonCallback getMouseButtons() {
        return mouseButtons;
    }
    /**
     * Take the Mouse Button Callback.
     * This should only be used in the DisplayScreen createCallbacks function, which is private.
     * @return The MouseMove GLFWMouseButtonCallback
     */
    public GLFWScrollCallback getMouseScroll() {
        return scroll;
    }

    /**
     * Constructor to get the non-static functions of the Input class.
     */
    public Input() {
        keyboard = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key != GLFW_KEY_UNKNOWN) {
                    if (keys[key] == (action == GLFW.GLFW_RELEASE)) {
                        if (action != GLFW.GLFW_RELEASE) {
                            events.add(new Event(Extra.KEY_DOWN, key));
                        } else {
                            events.add(new Event(Extra.KEY_UP, key));
                        }
                    }
                    keys[key] = (action != GLFW.GLFW_RELEASE);
                }
            }
        };
        mouseButtons = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                if (button != GLFW_KEY_UNKNOWN) {
                    if (buttons[button] == (action == GLFW.GLFW_RELEASE)) {
                        if (action != GLFW.GLFW_RELEASE) {
                            events.add(new Event(Extra.MOUSE_DOWN, button));
                        } else {
                            events.add(new Event(Extra.MOUSE_UP, button));
                        }
                    }
                    buttons[button] = (action != GLFW.GLFW_RELEASE);
                }
            }
        };
        mouseMove = new GLFWCursorPosCallback() {
            public void invoke(long window, double xPos, double yPos) {
                mouseX = xPos;
                mouseY = yPos;
            }
        };
        scroll = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }
    /**
     * Deletes (frees) the Callbacks.
     */
    public void deleteCallbacks() {
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        scroll.free();
    }
    /**
     * Checks if a certain key is down. Returns a boolean checking if anything is pressing the key.
     * @param key The GLFW code for the key.
     * @return True: the key is pressed, False: the key is not pressed
     * @see #isMouseDown(int button) isMouseDown
     */
    public static boolean isKeyDown(int key) {
        return keys[key];
    }
    /**
     * Checks if the button of the mouse is pressed.
     * @param button The GLFW code for the mouse button.
     * @return Is the button pressed? True - False.
     * @see #isKeyDown(int key) isKeyDown
     */
    public static boolean isMouseDown(int button) {
        return buttons[button];
    } public static float getMouseY(Screen screen) {
        return (float) (-2*getMouseY()/screen.getHeight()+1);
    } public static float getMouseX(Screen screen) {
        return (float) ((2.0f * getMouseX() / screen.getWidth()) - 1.0f) * ((float) screen.getWidth() / screen.getHeight());
    } public static float getScrollY(Screen screen) {
        return (float) (-2*getScrollX()/screen.getHeight()+1);
    } public static float getScrollX(Screen screen) {
        return (float) ((2.0f * getScrollX() / screen.getWidth()) - 1.0f) * ((float) screen.getWidth() / screen.getHeight());
    }
}
