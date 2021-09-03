package com.rookie.travelinandroid.super_algorithm.test_09_03;

/**
 * 排序
 * 快速排序
 * 堆排序
 * 希尔排序
 */
public class SortPractice {
    public static void main(String[] args) {
        int[] arr = {5, 4, 2, 1, 6, 3, 7};
        printArr(arr);
    }


    private static void printArr(int[] arr) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for (int i : arr) {
            stringBuilder.append(i).append(",");
        }
        stringBuilder.append(" ]");
        System.out.println(stringBuilder.toString());
    }
}
