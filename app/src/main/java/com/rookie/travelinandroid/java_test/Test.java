package com.rookie.travelinandroid.java_test;

public class Test {
    public static void main(String[] args) {
        double rad = Math.toRadians(30);
        double sin = Math.sin(rad);
        System.out.println("sin:" + sin);
        int n = 100;

        for (int i = 0; i < n; i = i * 2) {

        }
    }

    /**
     * 斐波那契数列
     * @param n
     * @return
     */
    int fib(int n) {
        if (n <= 2) {
            return n;
        }
        return fib(n - 1) + fib(n - 2);
    }
}
