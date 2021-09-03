package com.rookie.travelinandroid.super_algorithm.test_09_03;

/**
 * 排序
 * 快速排序
 * 堆排序
 * 希尔排序
 */
public class SortTest {
    public static void main(String[] args) {
        int[] arr = {5, 4, 2, 1, 6, 3, 7};
//        shellSort(arr);
//        int[] result = mergeSort(arr, 0, arr.length - 1);
        int[] result = quickSort(arr, 0, arr.length - 1);
        printArr(result);
    }

    private static int[] quickSort(int[] arr, int start, int end) {
        int pivot = arr[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (i < j && arr[j] > pivot) {
                j--;
            }
            while (i < j && arr[i] < pivot) {
                i++;
            }
            if (arr[i] == arr[j] && i < j) {
                i++;
            } else {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        if (i - 1 > start) {
            arr = quickSort(arr, start, i - 1);
        }
        if (j + 1 < end) {
            arr = quickSort(arr, j + 1, end);
        }
        return arr;

    }

    /**
     * 归并排序
     *
     * @param arr
     * @return
     */
    private static int[] mergeSort(int[] arr, int left, int right) {
        if (left == right) {
            return new int[]{arr[left]};
        }
        int mid = left + (right - left) / 2;
        int[] leftArr = mergeSort(arr, left, mid);
        int[] rightArr = mergeSort(arr, mid + 1, right);
        int[] newArr = new int[leftArr.length + rightArr.length];
        int m = 0;
        int i = 0;
        int j = 0;
        while (i < leftArr.length && j < rightArr.length) {
            newArr[m++] = leftArr[i] < rightArr[j] ? leftArr[i++] : rightArr[j++];
        }
        while (i < leftArr.length) {
            newArr[m++] = leftArr[i++];
        }
        while ((j < rightArr.length)) {
            newArr[m++] = rightArr[j++];
        }
        return newArr;
    }

    /**
     * 1.选定间隔
     * 2。分组排序
     * 3。间隔减半
     * 4。继续排序
     * 5。重复1-4
     *
     * @param arr
     */
    private static void shellSort(int[] arr) {
        int length = arr.length;
        int gap = (int) Math.floor(length / 2.0);
        for (; gap > 0; gap = (int) Math.floor(gap / 2.0f)) {
            for (int i = gap; i < length; i++) {
                int j = i;
                int currentNum = arr[i];
                while (j - gap >= 0 && currentNum < arr[j - gap]) {
                    arr[j] = arr[j - gap];
                    j = j - gap;
                }
                arr[j] = currentNum;
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
