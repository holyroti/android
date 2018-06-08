package nl.carlodvm.androidapp;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGLView;

    private static int CompileShader(String source, int type){
        int id = GLES20.glCreateShader(type);
        GLES20.glShaderSource(id, source);
        GLES20.glCompileShader(id);

        int result[] = new int[1];
        GLES20.glGetShaderiv(id, GLES20.GL_COMPILE_STATUS, result, 0);
        if(result[0] == GLES20.GL_FALSE){
            //TODO Implement all cases
            System.out.println("Failed to compile " +
                    (type == GLES20.GL_VERTEX_SHADER ? "vertex" :
                    type == GLES20.GL_FRAGMENT_SHADER ? "fragment" : "")
                    + " shader!\n" + GLES20.glGetShaderInfoLog(id));
        }



        return id;
    }

    static int CreateShader(String vertexShader, String fragmentShader){
        int program = GLES20.glCreateProgram();
        int vs = CompileShader(vertexShader, GLES20.GL_VERTEX_SHADER);
        int fs = CompileShader(fragmentShader, GLES20.GL_FRAGMENT_SHADER);

        GLES20.glAttachShader(program, vs);
        GLES20.glAttachShader(program, fs);
        GLES20.glLinkProgram(program);
        GLES20.glValidateProgram(program);

        GLES20.glDeleteShader(vs);
        GLES20.glDeleteShader(fs);

        return program;
    }
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
        // Example of a call to a native method
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
