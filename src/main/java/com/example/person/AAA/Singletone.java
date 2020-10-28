package com.example.person.AAA;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

public class Singletone {


    private static Singletone INSTANCE=null;

    private Singletone(){
    }


    private static void createInstanceIfNotExist(){
        ExecutorService executor = Executors.newFixedThreadPool(100);


//        if(INSTANCE==null ){
//            Lock. {
//                if(INSTANCE!=null){
//                    INSTANCE=  new Singletone();
//                }
//            }
//        }
    }


    public static Singletone getINSTANCE(){
        createInstanceIfNotExist();
        return INSTANCE;
    }




}
