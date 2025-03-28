package Engine.Graphics3D;

import static org.lwjgl.opengl.GL46.*;

public class Renderer {
    protected Shader shader;
    public Renderer(Shader s) {
        shader = s;
    }
    public void render(Mesh mesh) {
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        shader.bind();
        glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
        shader.unbind();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
