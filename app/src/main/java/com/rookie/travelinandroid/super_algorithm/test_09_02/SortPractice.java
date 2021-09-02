package com.rookie.travelinandroid.super_algorithm.test_09_02;

/**
 * 排序
 * 1。简单的
 * 冒泡，快速，选择，插入，归并，希尔
 */
public class SortPractice {
    public static void main(String[] args) {
        int[] arr = {5, 4, 2, 1, 6, 3, 7};
//        bubbleSort(arr);
        selectionSort(arr);
//        insertSort(arr);
        printArr(arr);
    }

    private static void insertSort(int[] arr) {
        int length = arr.length;
        int preIndex, current;
        for (int i = 1; i < length; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
    }

    /**
     * 每次把找到的最小的放到前面
     *
     * @param arr
     */
    private static void selectionSort(int[] arr) {
        int length = arr.length;
        int minIndex;
        for (int i = 0; i < length; i++) {
            minIndex = i;
            for (int j = i; j < length; j++) {
                int current = arr[j];
                int minNum = arr[minIndex];
                if (current < minNum) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    /**
     * 两两比较，较小的放到前面
     *
     * @param arr
     */
    private static void bubbleSort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                int current = arr[j];
                int next = arr[j + 1];
                if (current > next) {
                    arr[j + 1] = current;
                    arr[j] = next;
                }
            }
        }
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
