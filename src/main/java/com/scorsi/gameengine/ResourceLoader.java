package com.scorsi.gameengine;

import java.io.BufferedReader;
import java.io.FileReader;

public class ResourceLoader {

    public static String loadShader(String filename) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;
        try {
            shaderReader = new BufferedReader(new FileReader("./res/shaders/" + filename));
            String line;
            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            shaderReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return shaderSource.toString();
    }

}
