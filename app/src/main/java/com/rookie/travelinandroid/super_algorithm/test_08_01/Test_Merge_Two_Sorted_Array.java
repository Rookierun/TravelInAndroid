package com.rookie.travelinandroid.super_algorithm.test_08_01;

import java.util.Arrays;

/**
 * 给你两个有序整数数组nums1 和 nums2，请你将 nums2 合并到nums1中，使 nums1 成为一个有序数组。
 * <p>
 * 初始化nums1 和 nums2 的元素数量分别为m 和 n 。
 * 你可以假设nums1 的空间大小等于m + n，这样它就有足够的空间保存来自 nums2 的元素。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * 输出：[1,2,2,3,5,6]
 * 示例 2：
 * <p>
 * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
 * 输出：[1]
 */
public class Test_Merge_Two_Sorted_Array {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int n = 3;
        int[] nums2 = {2, 5, 6};
        mergeForced(nums1, m, nums2, n);
        mergeForcedOriginal(nums1, m, nums2, n);
    }

    /**
     * 暴力法，不开辟新数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    private static void mergeForcedOriginal(int[] nums1, int m, int[] nums2, int n) {
        //新的数组的长度是m+n
        //所以在原数组的基础上操作的话，原数组的后n为需要用nums2的前n位替换
        int newLength = m + n;
        for (int i = m; i < newLength; i++) {
            nums1[i]=nums2[newLength-i-1];
        }
        Arrays.sort(nums1);
    }

    /**
     * 暴力法，开辟新数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    private static void mergeForced(int[] nums1, int m, int[] nums2, int n) {
        int[] result = new int[m + n];
        for (int i = 0; i < m; i++) {
            result[i] = nums1[i];
        }
        for (int j = m; j < m + n; j++) {
            result[j] = nums2[j - m];
        }
        Arrays.sort(result);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            if (i == 0) {
                builder.append("[");
            }
            builder.append(result[i]);
            if (i != result.length - 1) {
                builder.append(",");
            }
            if (i == result.length - 1) {
                builder.append("]");
            }
        }
        System.out.println(builder.toString());

    }
}
