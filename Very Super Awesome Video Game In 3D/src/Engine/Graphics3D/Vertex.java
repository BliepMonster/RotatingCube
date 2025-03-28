package Engine.Graphics3D;

public class Vertex {
    protected Vector3 position, color, absolutePosition;
    public Vertex(float x, float y, float z) {
        position = new Vector3(x,y,z);
        absolutePosition = new Vector3(x, y, z);
    } public Vertex(Vector3 v, Vector3 c) {
        position = v;
        color = c;
        absolutePosition = new Vector3(v.getX(), v.getY(), v.getZ());
    } public Vertex(Vector3 v) {
        position = v;
        color = new Vector3(0,0,0);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getColor() {
        return color;
    }

    public void setColor(Vector3 color) {
        this.color = color;
    } public void setAbsolute() {
        absolutePosition.setX(position.getX());
        absolutePosition.setY(position.getY());
        absolutePosition.setZ(position.getZ());
    } public void resetRelative() {
        position = new Vector3( absolutePosition.getX(),
                                absolutePosition.getY(),
                                absolutePosition.getZ() );
    } public Vector3 getAbsolutePosition() {
        return absolutePosition;
    }
}
