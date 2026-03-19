package org.mromichov.guessthesong.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BackgroundExecutor {
    private static final Executor exec = Executors.newCachedThreadPool(runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
    });

    public static void run(Runnable runnable) {
        exec.execute(runnable);
    }

    public static Executor getExecutor() {
        return exec;
    }
}
