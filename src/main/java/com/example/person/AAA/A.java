package com.example.person.AAA;





public class A {
    private  String[] data=new String[100];
    public  int h=0;
    public  int t=0;

    public  void foo(String x) {
        h=(h+1)%100; // % = modulo (e.g. 8%4=0, 12%10=2, 2%5=2)
        if (h==t) {
            h=(h+99)%100;
            return;
        }
        data[h]=x;
    }

    public String bar() {
        if (h==t) return "";
        t=(t+1)%100;
        return data[t];
    }




}

