package org.app;

public class Buffer {
    private String[] buffer;
    private volatile int in = -1;
    private volatile int out = -1;
    private volatile int count = 0;

    public Buffer(int size){
        this.buffer = new String[size];
    }

    public synchronized void put (String s ) {
        while (count >= this.buffer.length) {
            try {
                wait();
            } catch (InterruptedException exc) {
                System.out.println("Interrupted Execption");
            }
        }
        count++;
        buffer[++in % buffer.length] = s;
        notifyAll();
    }

    public synchronized String get() {
        while (count == 0){
            try {
                wait();
            }
            catch (InterruptedException exc) {
                System.out.println("Interrupted Execption");
            }
        }
        count--;
        String s = buffer[++out % buffer.length];
        notifyAll();
        return s;
    }
}

