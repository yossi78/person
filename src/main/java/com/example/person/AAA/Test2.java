package com.example.person.AAA;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class Test2 {



    public interface  Printtable{
        public void print();

    }








    public static void main(String[] args) {

        Printtable printtable = new Printtable() {
            @Override
            public void print() {
                System.out.println("Test print");
            }
        };






    }
}
