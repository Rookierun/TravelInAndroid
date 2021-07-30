package com.rookie.travelinandroid.super_algorithm.test_07_30;

/**
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * <p>
 * 说明：你不能倾斜容器。
 * <p>
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/container-with-most-water
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Test_Contains_Most_Water {
    public static void main(String[] args) {
        int[] nums = {1, 8, 6, 2, 5, 4, 8, 3, 7};
//        containsMostWaterForced(nums);
        containsMostWaterOptimized(nums);
//        System.out.println(maxArea(nums));
    }

    /**
     * 优化法求解
     * 暴力法求解因为涉及到嵌套循环，所以暴力求解发的时间复杂度是O(n^2)
     * 优化思路
     * 只用一层遍历，左右收敛，哪边的边界较小，哪边就向中间收敛，当两个边界相遇时，结束
     *
     * @param nums
     */
    private static void containsMostWaterOptimized(int[] nums) {
        int maxArea = 0;
        int length = nums.length;
        //第三个不写，代表无限循环
        for (int i = 0, j = length - 1; i < j; ) {
            int minHeight = (nums[i] < nums[j]) ? nums[i++] : nums[j--];
            int area = minHeight * (j - i + 1);//因为上面做了--or++，所以这里需要+1
            maxArea = Math.max(maxArea, area);
        }
    }

    /**
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {
        int max = 0;
        int length = height.length;
        for (int i = 0, j = length - 1; i < j; ) {
            int left = height[i];
            int right = height[j];
            int minHeight = Math.min(left, right);
            int width = (j - i);
            int area = minHeight * width;
            if (left < right) {
                i++;
            } else {
                j--;
            }
            max = Math.max(area, max);
        }
        return max;
    }

    /**
     * 暴力法求解
     * 双重遍历数组，求出任意两个数之间的面积，取较大值
     * 可以求解，时间复杂度不满足要求
     *
     * @param nums
     */
    private static void containsMostWaterForced(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int minHeight = Math.min(nums[i], nums[j]);
                int area = (j - i) * minHeight;
                max = Math.max(max, area);
            }
        }
    }

    /**
     * 嵌套循环，边界不能重复
     */
    private static void doubleLoop() {
        int[] nums = {1, 2, 3, 4, 5};
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {

            }
        }
    }

}
