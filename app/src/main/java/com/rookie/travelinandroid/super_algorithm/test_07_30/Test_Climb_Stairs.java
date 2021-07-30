package com.rookie.travelinandroid.super_algorithm.test_07_30;

import java.util.HashMap;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * <p>
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * <p>
 * 注意：给定 n 是一个正整数。
 * <p>
 * 示例 1：
 * <p>
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 * 示例 2：
 * <p>
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/climbing-stairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Test_Climb_Stairs {
    public static void main(String[] args) {
        int n = 10;
//        climbStairsForced(n);
//        climbStairsWithThreeNum(n);
     System.out.println(   climbStairsWithCache(n));
    }

    static HashMap<Integer, Integer> cachedMap = new HashMap<>();

    private static int climbStairsWithCache(int n) {
        if (n <= 3) {
            return n;
        }
        if (cachedMap.containsKey(n)){
            return cachedMap.get(n);
        }else {
            int resultOfNMinus1 = climbStairsForced(n - 1);
            int resultOfMinus2 = climbStairsForced(n - 2);
            cachedMap.put(n-1,resultOfNMinus1);
            cachedMap.put(n-2,resultOfMinus2);
            return resultOfNMinus1 + resultOfMinus2;
        }
    }

    /**
     * 只保留最近使用的3个数
     *
     * @param n
     * @return
     */
    private static int climbStairsWithThreeNum(int n) {
        int f1 = 1;
        int f2 = 2;
        int f3 = 3;
        if (n <= 3) {
            return n;
        } else {
            for (int i = 4; i < n + 1; i++) {
                f3 = f1 + f2;
                f1 = f2;
                f2 = f3;
            }
            return f3;
        }
    }

    /**
     * 暴力法，即斐波那契数列
     * 这种暴力法可以求解，但是时间复杂度确实2^n，因为有很多都是重复计算了多次
     *
     * @param n
     */
    private static int climbStairsForced(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return climbStairsForced(n - 1) + climbStairsForced(n - 2);
    }


}
