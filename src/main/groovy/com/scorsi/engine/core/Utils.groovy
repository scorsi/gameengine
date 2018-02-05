package com.scorsi.engine.core

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Utils {

    static String loadResource(String fileName) throws Exception {
        System.out.println("Loading: " + fileName)
        String result = ""
        new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(fileName))).withCloseable { reader ->
            def line
            while ((line = reader.readLine()) != null) {
                result += line + "\n"
            }
        }
        return result
    }

    static List<String> readAllLines(String fileName) throws Exception {
        System.out.println("Loading: " + fileName)
        def lines = new ArrayList<String>()
        new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(fileName))).withCloseable { reader ->
            String line
            while ((line = reader.readLine()) != null) {
                lines.add(line)
            }
        }
        return lines
    }

}
