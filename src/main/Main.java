package main;

import materials.MaterialManager;

public class Main {

    static Window window = new Window();

    public static void main(String[] args) {
        MaterialManager.CreateAllMaterials();

        Thread t1 = new Thread(window);
        t1.start();
    }

}
