package com.rookie.travelinandroid.super_algorithm.test_07_30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 *  参考：https://www.cnblogs.com/qxlxi/p/12860695.html
 */
public class Test_Sum_Of_Three_Num {
    public static void main(String[] args) {
        int[] nums = {-1, -2, 2, 3, 4, 5, 6};
        nums = new int[]{-1, 0, 1, 2, -1, -4};
        nums = new int[]{-1,0,1,2,-1,-4,-2,-3,3,0,4};
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
     *
     * @param nums
     * @param target
     */
    private static void calculateByHashMap(int[] nums, int target) {

    }

    /**
     * 三重循环，分别拿到三重循环的三个数字
     * 如何去重是核心
     *
     * @param nums
     * @param target
     */
    private static List calculateForced(int[] nums, int target) {


        if(nums == null || nums.length <=2){
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        Set<List<Integer>> result = new LinkedHashSet<>();
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                for(int k = j+1;k<nums.length;k++){
                    if(nums[i]+nums[j]+nums[k] == 0){
                        List<Integer> list = Arrays.asList(nums[i],nums[j],nums[k]);
                        result.add(list);
                    }
                }
            }
        }
        ArrayList resultList = new ArrayList(result);
        System.out.println(resultList);
        return resultList;
    }


}
