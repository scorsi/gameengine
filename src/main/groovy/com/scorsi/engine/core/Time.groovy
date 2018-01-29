package com.scorsi.engine.core

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Time {

    public static final long SECOND = 1000000000L

    static double getActualTime() {
        return System.nanoTime() / SECOND
    }

}
