package com.rookie.travelinandroid.super_algorithm.test_07_30;

public class Test {
    public static void main(String[] args) {
        moveZeroes();
    }

    /**
     * https://leetcode-cn.com/problems/move-zeroes/
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * <p>
     * 示例:
     * <p>
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/move-zeroes
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    private static void moveZeroes() {
        int[] nums = {2, 1, 4, 3, 12};
//        moveZeroesForced(nums);
        moveZeroesByTowPointers(nums);
//        moveZeroesByTowPointers1(nums);
        printArr(nums);
    }

    private static void printArr(int[] nums) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (i == 0) {
                stringBuilder.append("[ ");
            }
            if (i != nums.length - 1) {
                stringBuilder.append(num).append(",");
            } else {
                stringBuilder.append(num);
            }

            if (i == nums.length - 1) {
                stringBuilder.append(" ]");
            }
        }
        System.out.println(stringBuilder.toString());
    }

    /**
     * 双指针的办法
     * 指针i用于遍历nums，指针j用来记录下一个非零元素的存储位置
     *
     * @param nums
     */

    private static void moveZeroesByTowPointers(int[] nums) {
        int slow = 0;
        int length = nums.length;
        for (int fast = 0; fast < length; fast++) {
            if (nums[fast] != 0) {
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = temp;
                slow++;
            }
        }
    }

    /**
     * leetCode
     *
     * @param nums
     */
    private static void moveZeroesByTowPointers1(int[] nums) {
        int j = 0;
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                if (i != j) {
                    nums[i] = 0;
                }
                j++;
            }
        }
        System.out.println("j:" + j);
    }

    /**
     * 开辟一个新数组，然后将非零元素向里面加
     *
     * @param nums
     */

    private static void moveZeroesForced(int[] nums) {
        int length_original = nums.length;
        int[] temp = new int[length_original];
        int j = 0;
        for (int i = 0; i < length_original; i++) {
            if (nums[i] != 0) {
                temp[j] = nums[i];
                j++;
            }
        }
        System.out.println(temp);
    }
}
