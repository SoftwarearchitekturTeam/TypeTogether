package de.hswhameln.typetogether.client;

import com.mycompany.myproject.library.MyLibrary;

public class Main {

    public static void main(String[] args) {
        System.out.println(new MyLibrary().ping());
    }

    public String ping() {
        return new MyLibrary().ping();
    }
}