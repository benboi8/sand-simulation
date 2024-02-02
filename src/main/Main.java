package main;

public class Main {

    static Window window = new Window();

    public static void main(String[] args) {


        Thread t1 = new Thread(window);
        t1.start();
    }

}
