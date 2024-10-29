package ngen;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;
    private float r, g, b, a;

    private static Window window = null;
    private boolean fadeToBlack = false;

    private Window()
    {
        this.height = 1080;
        this.width = 1920;
        this.title = "Mario";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
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


        //Freeing memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate glfw and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
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

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);        // lambda expression java8
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

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

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(fadeToBlack)
            {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }

            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE))
            {
                fadeToBlack = true;
                System.out.println("PRessed");
            }

            glfwSwapBuffers(glfwWindow);
        }
    }

}
