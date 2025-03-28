import Engine.Core.Display.*;
import Engine.Core.Input.*;
import Engine.Core.Input.Events.*;
import Engine.Graphics3D.*;

import static Engine.Core.Input.Input.*;
import static Engine.Extra.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;

public class Main {
    public static final Screen screen = new Screen();
    public static final Console console = new Console();
    public static final Shader shader = new Shader(
            "resources/Shaders/mainVertex.glsl",
            "resources/Shaders/mainFragment.glsl");
    public static final Renderer renderer = new Renderer(shader);
    public static void main(String[] args) {
        screen.create(console);
        shader.create();
        screen.setFullscreen(true);

        Mesh mesh = new Mesh(new Vertex[] {
                //FRONT SIDE:0;1;2;3
                new Vertex(new Vector3(-.5f, .5f, 0),
                        new Vector3(1,0,0)),
                new Vertex(new Vector3(.5f, .5f, 0),
                        new Vector3(1,0,0)),
                new Vertex(new Vector3(.5f, -.5f, 0),
                        new Vector3(1,0,0)),
                new Vertex(new Vector3(-.5f, -.5f, 0),
                        new Vector3(1,0,0)),
                //BACK SIDE:4;5;6;7
                new Vertex(new Vector3(-.5f, .5f, -1),
                        new Vector3(0,1,0)),
                new Vertex(new Vector3(.5f, .5f, -1),
                        new Vector3(0,1,0)),
                new Vertex(new Vector3(.5f, -.5f, -1),
                        new Vector3(0,1,0)),
                new Vertex(new Vector3(-.5f, -.5f, -1),
                        new Vector3(0,1,0)),
                //LEFT SIDE:8;9;10;11
                new Vertex(new Vector3(.5f, -.5f, 0),
                        new Vector3(1,1,0)),
                new Vertex(new Vector3(-.5f, -.5f, 0),
                        new Vector3(1,1,0)),
                new Vertex(new Vector3(-.5f, -.5f, -1),
                        new Vector3(1,1,0)),
                new Vertex(new Vector3(.5f, -.5f, -1),
                        new Vector3(1,1,0)),
                //RIGHT SIDE:12;13;14;15
                new Vertex(new Vector3(.5f, .5f, 0),
                        new Vector3(1,0,1)),
                new Vertex(new Vector3(-.5f, .5f, 0),
                        new Vector3(1,0,1)),
                new Vertex(new Vector3(-.5f, .5f, -1),
                        new Vector3(1,0,1)),
                new Vertex(new Vector3(.5f, .5f, -1),
                        new Vector3(1,0,1)),
                //TOP SIDE:16;17;18;19
                new Vertex(new Vector3(.5f, .5f, 0),
                        new Vector3(0,0,1)),
                new Vertex(new Vector3(.5f, -.5f, 0),
                        new Vector3(0,0,1)),
                new Vertex(new Vector3(.5f, -.5f, -1),
                        new Vector3(0,0,1)),
                new Vertex(new Vector3(.5f, .5f, -1),
                        new Vector3(0,0,1)),
                //BOTTOM SIDE:20;21;22;23
                new Vertex(new Vector3(-.5f, .5f, 0),
                        new Vector3(0,1,1)),
                new Vertex(new Vector3(-.5f, -.5f, 0),
                        new Vector3(0,1,1)),
                new Vertex(new Vector3(-.5f, -.5f, -1),
                        new Vector3(0,1,1)),
                new Vertex(new Vector3(-.5f, .5f, -1),
                        new Vector3(0,1,1)),
        }, new int[] {
                4,5,6,
                4,7,6,
                8,9,10,
                8,10,11,
                12,13,14,
                12,14,15,
                16,17,18,
                16,18,19,
                20,21,22,
                20,22,23,
                0,1,2,
                0,3,2
        });
        screen.setFullscreen(true);
        mesh.create();
        boolean click = false;
        Camera camera = new Camera(0,0,0);
        while (screen.update()) {
            for (Event event : console.input.events.get()) {
                if (event.type == KEY_DOWN) {
                    if (event.key == GLFW_KEY_ESCAPE) {
                        screen.destroy();
                    } else if (event.key == GLFW_KEY_P && isKeyDown(GLFW_KEY_LEFT_CONTROL)) {
                        screen.takeScreenshot();
                    }
                }
            } Vector3 movement = new Vector3(0,0,0);
            if (isKeyDown(GLFW_KEY_LEFT)) movement.setX((float) (movement.getX()+1/screen.getFPS()));
            if (isKeyDown(GLFW_KEY_RIGHT)) movement.setX((float) (movement.getX()-1/screen.getFPS()));
            if (isKeyDown(GLFW_KEY_DOWN)) movement.setZ((float) (movement.getZ()+1/screen.getFPS()));
            if (isKeyDown(GLFW_KEY_UP)) movement.setZ((float) (movement.getZ()-1/screen.getFPS()));
            camera.move(movement);
            if (isMouseDown(GLFW_MOUSE_BUTTON_1)) click = !click;
            Vertex[] vertices = mesh.getVertices();
            for (Vertex v : vertices) {
                v.getPosition().move(movement);
                if (!click) {
                    v.getPosition().rotateX((float) (PI / screen.getFPS()));
                    v.getPosition().rotateY((float) (PI / screen.getFPS()));
                    v.getPosition().rotateZ((float) (PI / screen.getFPS()));
                } else v.resetRelative();
            } mesh.destroy();
            mesh.create();
            renderer.render(mesh);
            //screen.draw.text("This is text", (float) -sqrt(3)/2, -font.getHeight(font.getSizeForWidth("Text", (float) sqrt(3)))/2, font, font.getSizeForWidth("Text", (float) sqrt(3)), BLACK);
            screen.display();
        }
    }
}
