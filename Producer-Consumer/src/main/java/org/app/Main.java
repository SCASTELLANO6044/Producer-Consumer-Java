package org.app;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        Producer miproductor;
        Consumer miconsumidor;
        miproductor = new Producer (buffer, 1000, 20);
        miproductor.start();
        miconsumidor = new Consumer (buffer, 3000, 20);
        miconsumidor.start();
    }
}