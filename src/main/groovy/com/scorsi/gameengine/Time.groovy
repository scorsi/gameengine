package com.scorsi.gameengine

class Time {

    public static final long SECOND = 1000000000L

    static double delta

    static long getTime() {
        return System.nanoTime()
    }

    static double getDelta() {
        return delta
    }

}
