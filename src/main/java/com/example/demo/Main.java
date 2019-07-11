package com.example.demo;


import java.util.ArrayList;

public class Main {//тест

    static int method1(int i)
    {
        return method2(i *= 11);
    }

    static int method2(int i)
    {
        return method3(i /= 11);
    }

    static int method3(int i)
    {
        return method4(i -= 11);
    }

    static int method4(int i)
    {
        return i += 11;
    }


    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> objects = new ArrayList<>();
        int a = 2;
    }

}
