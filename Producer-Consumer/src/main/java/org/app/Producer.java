package org.app;

import java.util.Date;

public class Producer extends Thread {
    private Buffer buffer;
    private int milisegundos;
    private int iteraciones;

    public Producer(Buffer b, int s, int n) {
        buffer = b;
        milisegundos = s;
        iteraciones = n;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < iteraciones; i++) {
                buffer.put(new Date().toString());
                Thread.sleep(milisegundos);
            }
        } catch (InterruptedException exc) {
            System.out.println("Interrupted Execption");
        }
    }
}
