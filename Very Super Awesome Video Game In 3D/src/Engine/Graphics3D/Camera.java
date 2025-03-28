package Engine.Graphics3D;

public class Camera {
    protected Vector3 position;
    public Camera(float x, float y, float z) {
        position  = new Vector3(x,y,z);
    } public void move(Vector3 movement) {
        position.move(movement);
    }

    public Vector3 getPosition() {
        return position;
    }
}
