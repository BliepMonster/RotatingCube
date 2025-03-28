package Engine.Graphics3D;

import static java.lang.Math.*;

public class Vector3 {
    protected float x, y, z;
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    } public void rotateY(float rad) {
        float x2 = (float) (x*cos(rad)+z*sin(rad));
        z = (float) (-x*sin(rad)+z*cos(rad));
        x = x2;
    } public void rotateX(float rad) {
        float y2 = (float) (y*cos(rad)-z*sin(rad));
        z = (float) (y*sin(rad)+z*cos(rad));
        y = y2;
    } public void rotateZ(float rad) {
        float x2 = (float) (x*cos(rad) - y*sin(rad));
        y = (float) (x*sin(rad) + y*cos(rad));
        x = x2;
    } public void move(Vector3 move) {
        x += move.getX();
        y += move.getY();
        z += move.getZ();
    }
}
