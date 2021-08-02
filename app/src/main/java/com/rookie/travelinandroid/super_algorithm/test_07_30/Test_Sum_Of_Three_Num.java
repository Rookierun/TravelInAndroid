package com.rookie.travelinandroid.super_algorithm.test_07_30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
 * 参考：https://www.cnblogs.com/qxlxi/p/12860695.html
 */
public class Test_Sum_Of_Three_Num {
    public static void main(String[] args) {
        int[] nums = {-1, -2, 2, 3, 4, 5, 6};
        nums = new int[]{-1, 0, 1, 2, -1, -4};
        nums = new int[]{-1, 0, 1, 2, -1, -4, -2, -3, 3, 0, 4};
        int target = 0;
//        calculateForced(nums, target);
//        calculateByHashMap(nums, target);
//        calculateByTwoPointers(nums, target);
        calculateByLeetCode(nums, target);
    }

    /**
     * 夹逼法，也没懂。。。
     *
     * @param nums
     * @param target
     * @return
     */
    private static List<List<Integer>> calculateByLeetCode(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        // 枚举 a
        for (int first = 0; first < n; ++first) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            int third = n - 1;
             target = -nums[first];
            // 枚举 b
            for (int second = first + 1; second < n; ++second) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 需要保证 b 的指针在 c 的指针的左侧
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    /**
     * 左右下表推进
     * 执行用时： 757 ms , 在所有 Java 提交中击败了6.01%的用户
     * thingking:左右夹逼，三数求和 a+b+c = 0 等价于 a+b=c 将数组进行排序，将c固定 a设置到c的下一个问题，也就是head b设置到最后一个位置 tail位置。
     * <p>
     * 第一次遍历c固定 head 和tail 分别向中间移动，判断如果-sum < target 说明值大 因为是负数。所以tail 左移动，否则就是head右移动。如此一轮后 如果找不到，接着c++ head 和tail接着判断。
     * <p>
     * 就可以找到。时间复杂度相对于上面两种是比较低的。
     * <p>
     * 时间复杂度:O(n)
     *
     * @param nums
     * @param target
     */
    private static List<List<Integer>> calculateByTwoPointers(int[] nums, int target) {
        if (nums == null || nums.length <= 2) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        Set<List<Integer>> result = new LinkedHashSet<>();
        int length = nums.length;
        for (int i = 0; i < length - 2; i++) {
            int head = i + 1;
            int tail = length - 1;
            while (head < tail) {
                int sum = -(nums[head] + nums[tail]);
                if (sum == nums[i]) {
                    List<Integer> list = Arrays.asList(nums[i], nums[head], nums[tail]);
                    result.add(list);
                }
                if (sum <= nums[i]) {
                    tail--;
                } else {
                    head++;
                }
            }
        }
        return new ArrayList<>(result);

    }

    /**
     * HashMap记录 a, b,找a+b=-c；
     * 思路：
     * 执行用时: 1291 ms
     *
     * @param nums
     * @param target
     */
    private static List<List<Integer>> calculateByHashMap(int[] nums, int target) {
        if (nums == null || nums.length <= 2) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        Set<List<Integer>> result = new LinkedHashSet<>();
        int length = nums.length;
        for (int i = 0; i < length - 2; i++) {
            int newTarget = -nums[i];
            HashMap<Integer, Integer> hashMap = new HashMap<>(length - i);
            for (int j = i + 1; j < length; j++) {
                int k = newTarget - nums[j];
                Integer exists = hashMap.get(k);
                if (exists != null) {
                    List<Integer> list = Arrays.asList(nums[i], exists, nums[j]);
                    list.sort(Comparator.naturalOrder());
                    result.add(list);
                } else {
                    hashMap.put(nums[j], nums[j]);
                }
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * 三重循环，分别拿到三重循环的三个数字
     * 如何去重是核心
     *
     * @param nums
     * @param target
     */
    private static List<Integer> calculateForced(int[] nums, int target) {


        if (nums == null || nums.length <= 2) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        Set<List<Integer>> result = new LinkedHashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = Arrays.asList(nums[i], nums[j], nums[k]);
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
