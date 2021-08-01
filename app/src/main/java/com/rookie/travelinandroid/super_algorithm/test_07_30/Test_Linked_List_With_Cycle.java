package com.rookie.travelinandroid.super_algorithm.test_07_30;

/**
 * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 */
public class Test_Linked_List_With_Cycle {
    public static void main(String[] args) {
        int[] nums = {-1, -2, 2, 3, 4, 5, 6};
        int target = 0;
        calculateForced(nums, target);
        calculateByHashMap(nums, target);
        calculateByTwoPointers(nums, target);
    }

    /**
     * 左右下表推进
     *
     * @param nums
     * @param target
     */
    private static void calculateByTwoPointers(int[] nums, int target) {

    }

    /**
     * HashMap记录 a, b,找a+b=-c；
     * @param nums
     * @param target
     */
    private static void calculateByHashMap(int[] nums, int target) {

    }

    /**
     * 三重循环，转换为a+b=-c
     *
     * @param nums
     * @param target
     */
    private static void calculateForced(int[] nums, int target) {

    }


}
