package ngen;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window()
    {
        this.height = 1920;
        this.width = 1080;
        this.title = "Mario";
    }

    public static Window get(){            // Singleton methodology
        if(Window.window == null){
            Window.window = new Window();
        }

        return Window.window;
    }


    public void run(){
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();
    }


    public void init(){
        //Setup error callback

        GLFWErrorCallback.createPrint(System.err).set();

        //Initialise GLFW
        if(!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //creating window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);    //this will return a long value which is a number which tells the address of the location of window in memory space

        if(glfwWindow == NULL)
        {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        //make the openGL context current
        glfwMakeContextCurrent(glfwWindow);

        //enable vsync
        glfwSwapInterval(1);

        //make the window visible
        glfwShowWindow(glfwWindow);

        //this is important for some reason... search on internet
        GL.createCapabilities();
    }


    public void loop(){
        while(!glfwWindowShouldClose(glfwWindow)){
            //poll events
            glfwPollEvents();

            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }

}
