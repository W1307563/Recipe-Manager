package com.example.cmis202project;

import java.util.LinkedList;

public class TaskThreadDemo {
    public static void main(String[] args) {

        LinkedList<String> unfavs = new LinkedList<>();
        unfavs.add("Brussel Sprouts");
        unfavs.add("Olives");
        unfavs.add("Beets");
        unfavs.add("Mushrooms");
        unfavs.add("Mayo");

        LinkedList<String> yourUnFavs = new LinkedList<>();

        Runnable printA = new PrintList(unfavs);
        Runnable printB = new PrintList(yourUnFavs);


        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);

        thread1.start();
        thread2.start();
    }
}

class PrintList implements Runnable {
    private LinkedList<String> linkedList;

    public PrintList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    @Override
    public void run() {
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }
    }
}


