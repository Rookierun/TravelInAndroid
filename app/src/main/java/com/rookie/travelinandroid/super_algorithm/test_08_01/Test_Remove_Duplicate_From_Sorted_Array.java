package com.rookie.travelinandroid.super_algorithm.test_08_01;

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ，
 * 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 */
public class Test_Remove_Duplicate_From_Sorted_Array {
    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(removeDuplicateFromSortedArray(nums));
    }

    /**
     * 关键是不能使用新的数据结构
     * 使用双指针，快指针用来遍历，慢指针用来记录可以被覆盖的位置，每遇到一个重复元素时，就把slow处的数据覆盖，并且前移slow指针
     *
     * @param nums
     * @return
     */
    private static int removeDuplicateFromSortedArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        int fast = 1, slow = 1;
        while (fast < length) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }
}
