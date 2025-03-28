package Engine.Graphics3D;

import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    protected Vertex[] vertices;
    protected int[] indices;
    protected int VAO, IBO, PBO, CBO;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }  public void create() {
        VAO = glGenVertexArrays();
        glBindVertexArray(VAO);
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length*3);
        float[] positionData = new float[vertices.length*3];
        for (int i = 0; i < vertices.length; i++) {
            positionData[i*3] = vertices[i].getPosition().getX();
            positionData[i*3+1] = vertices[i].getPosition().getY();
            positionData[i*3+2] = vertices[i].getPosition().getZ();
        } positionBuffer.put(positionData).flip();
        PBO = storeData(positionBuffer, 0, 3);
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length*3);
        float[] colorData = new float[vertices.length*3];
        for (int i = 0; i < vertices.length; i++) {
            colorData[i*3] = vertices[i].getColor().getX();
            colorData[i*3+1] = vertices[i].getColor().getY();
            colorData[i*3+2] = vertices[i].getColor().getZ();
        } colorBuffer.put(colorData).flip();
        CBO = storeData(colorBuffer, 1, 3);

        FloatBuffer absolutePositionBuffer = MemoryUtil.memAllocFloat(vertices.length*3);
        float[] absolutePositionData = new float[vertices.length*3];
        for (int i = 0; i < vertices.length; i++) {
            absolutePositionData[i*3] = vertices[i].getAbsolutePosition().getX();
            absolutePositionData[i*3+1] = vertices[i].getAbsolutePosition().getY();
            absolutePositionData[i*3+2] = vertices[i].getAbsolutePosition().getZ();
        } absolutePositionBuffer.put(absolutePositionData).flip();
        PBO = storeData(absolutePositionBuffer, 2, 3);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        IBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return VAO;
    }

    public int getIBO() {
        return IBO;
    } protected int storeData(FloatBuffer fb, int index, int size) {
        int bufferID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, bufferID);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public int getCBO() {
        return CBO;
    }

    public int getPBO() {
        return PBO;
    } public void destroy() {
        glDeleteBuffers(PBO);
        glDeleteBuffers(CBO);
        glDeleteBuffers(IBO);
        glDeleteVertexArrays(VAO);
    }
}
