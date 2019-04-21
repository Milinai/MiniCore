package main;

import graphics.Model;
import graphics.Texture;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main{
	private long window;
	public void run(){
		init();
		loop();
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	private void init(){
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit())
			throw new IllegalStateException("Unable to initializeGLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		window = glfwCreateWindow(500, 400, "Application", NULL, NULL);
		if(window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		/*glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true);
		});*/
		try(MemoryStack stack = stackPush()){
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
	}
	private void loop(){
		GL.createCapabilities();
		float[] vertices = new float[]{
			-1f,  1f,
			 1f,  1f,
			 1f, -1f,
			 1f, -1f,
			-1f, -1f,
			-1f,  1f
		};
		float[] texture = new float[]{
			0f,0f,1f,0f,1f,1f,
			1f,1f,0f,1f,0f,0f
		};
		Model model = new Model(vertices, texture);
		Texture tex = new Texture("D:/Games/Fighter/sprites/stone.png");
		while(!glfwWindowShouldClose(window)){
			glfwPollEvents();
			glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
			tex.bind();
			model.render();
			glfwSwapBuffers(window);
		}
	}
	public static void main(String[] args){
		new Main().run();
	}
}
