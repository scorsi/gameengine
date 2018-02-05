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

    static String[] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>()

        for (int i = 0; i < data.length; i++)
            if (!data[i].equals(""))
                result.add(data[i])

        String[] res = new String[result.size()]
        result.toArray(res)

        return res
    }

}
