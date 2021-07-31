package com.rookie.travelinandroid.super_algorithm.test_07_30;

import java.util.HashMap;

/**
 * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * <p>
 * 你可以按任意顺序返回答案。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Test_Sum_Of_Two_Num {
    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 4, 5, 6};
        int target = 5;
        calculateForced(nums, target);
//        calculateByHashMap(nums, target);
    }

    /**
     * 暴力法，双重遍历
     * 时间复杂度 O（n^2）
     *
     * @param nums
     * @param target
     */
    private static void calculateForced(int[] nums, int target) {
        int[] result = new int[2];
        outer:
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    break outer;
                }
            }
        }
        System.out.println("i:" + result[0] + ",j:" + result[1]);

    }

    /**
     * 2次遍历
     * 第一次，将值为value，索引为key放入map中
     * 第二次，遍历map，找到map中是否存在target-current的值，如果存在就满足条件，找到了
     *
     * @param nums
     * @param target
     */
    private static void calculateByHashMap(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            if (map.containsKey(target - current)) {
                result[0] = i;
                result[1] = map.get(target - current);
                System.out.println("i:" + nums[0] + ",j:" + nums[1]);
                return;
            }
            map.put(nums[i], i);
        }
    }


}
