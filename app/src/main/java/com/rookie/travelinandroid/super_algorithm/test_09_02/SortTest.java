package com.rookie.travelinandroid.super_algorithm.test_09_02;

/**
 * 排序
 * 1。简单的
 * 冒泡，快速，选择，插入，归并，希尔
 */
public class SortTest {
    public static void main(String[] args) {
        int[] arr = {5, 4, 2, 1, 6, 3, 7};
//        bubbleSort(arr);
//        selectionSort(arr);
        insertSort(arr);
        printArr(arr);
    }

    /**
     * 插入排序
     * 双重循环
     * 构建有序序列，对于未排序的数据，在已排序的序列中从后向前扫描，找到相应的位置并插入
     * 步骤：
     * 1。从第一个元素开始，该元素可以认为已经被排序
     * 2。取出下一个元素，在已经排序的元素序列中从后向前扫描
     * 3。如果该元素大雨新元素，则将新元素移到下一位置
     * 4。重复步骤3，知道找到已排序的元素小于或者等于新元素的位置
     * 5。将新元素插入到该位置后面
     * 6。重复2-5
     *
     * @param arr
     */
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
     * 选择排序
     * 双重循环
     * 每次选择最小的元素放到起始位置，然后再后面的元素中继续重复此操作
     *
     * @param arr
     */
    private static void selectionSort(int[] arr) {
        int length = arr.length;
        int minIndex;
        int temp;
        for (int i = 0; i < length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < length; j++) {
                int current = arr[j];
                int minNum = arr[minIndex];//当前记录的最小值
                if (current < minNum) {
                    minIndex = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    /**
     * 冒泡排序
     * 双重循环
     * 从第一个元素开始，两两比较元素，如果逆序就交换
     *
     * @param arr
     */
    private static void bubbleSort(int[] arr) {
        int count = 0;
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                count++;
                int current = arr[j];
                int next = arr[j + 1];
                if (current > next) {
                    arr[j + 1] = current;
                    arr[j] = next;
                }
            }
        }
        System.out.println(count);
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
