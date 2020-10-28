package com.example.person.AAA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;





public class Lab {

    private static Integer num=0;


    public void incrementNum(){
        num++;
        System.out.println("now num="+num);
    }


    public void runParallel(){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(this::incrementNum);
        executor.shutdownNow();
        System.out.println("FINISH");
    }

    public static void main(String[] args) {

        Lab lab = new Lab();
        lab.runParallel();
        System.out.println("finally num="+num);
    }
}
