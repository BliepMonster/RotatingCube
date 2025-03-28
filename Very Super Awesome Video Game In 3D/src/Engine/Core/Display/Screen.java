package Engine.Core.Display;

import Engine.Core.Input.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Screen {
    protected int WIDTH;
    protected int HEIGHT;
    protected String title;
    protected long window;
    protected GLFWWindowSizeCallback sizeCallback;
    protected boolean isResized;
    protected boolean isFullscreen;
    protected int windowPosX;
    protected int windowPosY;
    protected long timeThisFrame = System.currentTimeMillis();
    protected long timePreviousFrame = System.currentTimeMillis();
    protected long timeDiff = 50;
    protected double FPS = 60;
    protected long nanoTime = System.nanoTime();
    protected long pastNanoTime = System.nanoTime();
    protected long nanoDiff = System.nanoTime();
    protected int drawCalls = 0;
    protected boolean active;
    protected Color bgColor = Color.BLACK;
    public DrawManager draw = new DrawManager(this);
    /**
     * Creates a window with size 1280x720 and title "Untitled".
     */
    public Screen() {
        WIDTH = 1280;
        HEIGHT = 720;
        title = "Untitled";
    }

    /**
     * Creates a window with custom size and title "Untitled".
     * @param width The width of the screen, in pixels.
     * @param height The height of the screen, in pixels.
     */
    public Screen(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        title = "Untitled";
    }

    /**
     * Creates a screen with size 1280x720 and a custom title.
     * @param title The title of your screen.
     */
    public Screen(String title) {
        WIDTH = 1280;
        HEIGHT = 720;
        this.title = title;
    }

    /**
     * Creates a screen with custom width, height, and title.
     * @param width The width of your screen.
     * @param height The height of your screen.
     * @param title The title of your screen.
     */
    public Screen(int width, int height, String title) {
        WIDTH = width;
        HEIGHT = height;
        this.title = title;
    }

    public int getWidth() {
        return WIDTH;
    }
    public int getHeight() {
        return HEIGHT;
    }
    public String getTitle() {
        return title;
    }
    public long getWindow() {
        return window;
    }

    /**
     * Will create this Screen and its callbacks.
     * @param console The console (with the input) to use to create the callbacks.
     */
    public void create(Console console) {
        boolean initSucceed = GLFW.glfwInit();
        if (!initSucceed) {
            System.err.println("There was a problem starting the screen.");
            return;
        }
        window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, title, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
        if (window == 0) {
            System.err.println("The window wasn't created.");
            return;
        }
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (videoMode != null) {
            windowPosX = (videoMode.width() - WIDTH) / 2;
            windowPosY = (videoMode.height() - HEIGHT) / 2;
            GLFW.glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);
        }
        else {
            System.err.println("Failed to fetch the VideoMode.");
            return;
        }
        System.out.println("Screen is correctly set up!");
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        createCallbacks(console.input);
        GLFW.glfwSwapInterval(1);
        active = true;
    }

    /**
     * Creates all callbacks.
     * @param input The input to use to create callbacks.
     */
    private void createCallbacks(Input input) {
        GLFW.glfwSetKeyCallback(window, input.getKeyboard());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtons());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMove());
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int width, int height) {
                WIDTH = width;
                HEIGHT = height;
                isResized = true;
            }
        };
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }

    /**
     * Resets the screen. Fails if: screen is inactive, window is 0
     * @return Did it succeed?
     */
    public boolean update() {
        boolean succeed = true;
        if (active) {
            if (isResized) {
                GL11.glViewport(0, 0, WIDTH, HEIGHT);
                isResized = false;
            }
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            timeThisFrame = System.currentTimeMillis();
            nanoTime = System.nanoTime();
            timeDiff = timeThisFrame - timePreviousFrame;
            nanoDiff = nanoTime - pastNanoTime;
            if (timeDiff != 0) {
                double timeDiffSeconds2 = nanoDiff / 1000000000f;
                FPS = 1 / timeDiffSeconds2;
                timePreviousFrame = timeThisFrame;
                pastNanoTime = nanoTime;
            }
        } else {
            succeed = false;
        }
        if (window != 0) {
            GLFW.glfwPollEvents();
        } else {
            succeed = false;
        }
        return succeed;
    }

    /**
     * Sets the background color to a value of red, green, and blue.
     * @param red The red value.
     * @param green The green value.
     * @param blue The blue value.
     * @see #setBackground(Color) setBackgroundColor(Color)
     */
    public void setBackground(int red, int green, int blue) {
        bgColor = new Color(red, green, blue);
        glClearColor(red/255f, green/255f, blue/255f, 1f);
    }

    /**
     * Updates the screen's display by swapping the buffers and showing the screen.
     */
    public void display() {
        if (window != 0) {
            GLFW.glfwShowWindow(window);
        } else {
            System.err.println("An error occurred with the display, as the window wasn't correctly set up.");
        } clearDrawCalls();

        if (window != 0) {
            GLFW.glfwSwapBuffers(window);
        }
    }

    /**
     * Sets the screen's background color.
     * @param color The background color.
     * @see #setBackground(int, int, int) setBackgroundColor(int, int, int)
     */
    public void setBackground(Color color) {
        glClearColor(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
        bgColor = color;
    }

    /**
     * Checks if the screen is fullscreen.
     * @return Boolean showing whether the screen is fullscreen.
     */
    public boolean isFullscreen() {
        return isFullscreen;
    } public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
        isResized = true;
        if (fullscreen) {
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, WIDTH, HEIGHT, 0);
        } else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX, windowPosY, WIDTH, HEIGHT, 0);
        }
    } public boolean isResized() {
        return isResized;
    } public void setResized(boolean resized) {
        isResized = resized;
    }

    public double getFPS() {
        return FPS;
    }

    public long getFrameTimeDifference() {
        return timeDiff;
    }

    public long getTimeThisFrame() {
        return timeThisFrame;
    }
    protected void clearDrawCalls() {
        drawCalls = 0;
    } public void addCall() {
        drawCalls++;
    }

    /**
     * Returns the amount of draw calls that were made this frame.
     * @return The amount of draw calls this frame
     */
    public int getDrawCalls() {
        return drawCalls;
    } public void takeScreenshot() {
        Screenshot.takeScreenshot("Screenshots/Screenshot_"+System.currentTimeMillis()+".png", getWidth(), getHeight());
    } public void destroy() {
        GLFW.glfwDestroyWindow(this.window);
        active = false;
    } public void takeScreenshot(String path) {
        Screenshot.takeScreenshot(path, getWidth(), getHeight());
    }
}