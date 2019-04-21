package graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Model{
	private int drawCount;
	private int vertexId;
	private int textureId;
	private int size = 2;
	public Model(float[] vertices, float[] texture){
		drawCount = vertices.length / size;
		vertexId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
		textureId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(texture), GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	public void render(){
		glEnable(GL_VERTEX_ARRAY);
		glEnable(GL_TEXTURE_COORD_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vertexId);
		glVertexPointer(size, GL_FLOAT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, textureId);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		glDrawArrays(GL_TRIANGLES, 0, drawCount);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisable(GL_VERTEX_ARRAY);
		glDisable(GL_TEXTURE_COORD_ARRAY);
	}
	public FloatBuffer createBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
