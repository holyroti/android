package nl.carlodvm.androidapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.4f,0,0.4f,0);
        int buffers[] = new int[1];

        float positions[] = {
                -0.5f, -0.5f,
                 0.0f,  0.5f,
                 0.5f, -0.5f
        };

        GLES20.glGenBuffers(1, buffers, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, 6 * 4, FloatBuffer.wrap(positions), GLES20.GL_STATIC_DRAW);

        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 2, GLES20.GL_FLOAT, false, 8, 0);

        String vertexShader = "#version 320 es\n" +
                "layout(location = 0) in vec4 position;" +
                "\n" +
                "void main(){" +
                "gl_Position = position;" +
                "}";

        String fragmentShader = "#version 320 es\n" +
                "precision mediump float;" +
                "layout(location = 0) out vec4 color;" +
                "\n" +
                "void main(){" +
                "color = vec4(1.0, 0.0, 0.0, 1.0);" +
                "}";

        int shader = MainActivity.CreateShader(vertexShader, fragmentShader);
        GLES20.glUseProgram(shader);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

    }
}
