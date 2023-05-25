package org.example1;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread producerThread1 = new Thread(new Producer(buffer));
        Thread producerThread2 = new Thread(new Producer(buffer));
        Thread readerThread1 = new Thread(new Reader(buffer));
        Thread readerThread2 = new Thread(new Reader(buffer));

        producerThread1.start();
        producerThread2.start();
        readerThread1.start();
        readerThread2.start();
    }
}
class Buffer {
    private List<String> wordList;

    private boolean writing;

    public Buffer() {
        this.wordList = new ArrayList<>();
        this.writing = false;
    }

    public synchronized void insertWord(String word) throws InterruptedException {
        while (writing || !wordList.isEmpty()) {
            wait(); // Espera hasta que no haya otro Producer escribiendo y el buffer esté vacío
        }
        writing = true;
        wordList.add(word);
        writing = false;
        notifyAll(); // Notifica a los hilos en espera
    }
    public synchronized List<String> getWordList() throws InterruptedException {
        while (writing || wordList.isEmpty()) {
            wait(); // Espera hasta que no haya otro Producer escribiendo y haya palabras en el buffer
        }
        return new ArrayList<>(wordList);
    }

}
class Producer implements Runnable {

    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random random = new Random();
        String word = generateRandomWord();
        try {
            buffer.insertWord(word);
            System.out.println("Producer inserted word: " + word);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private String generateRandomWord() {
        // Genera una palabra aleatoria de longitud 5
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

}
class Reader implements Runnable {

    private Buffer buffer;

    public Reader(Buffer buffer) {
        this.buffer = buffer;
    }
    @Override
    public void run() {
        try {
            List<String> wordList = buffer.getWordList();
            System.out.println("Reader received word list: " + wordList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

