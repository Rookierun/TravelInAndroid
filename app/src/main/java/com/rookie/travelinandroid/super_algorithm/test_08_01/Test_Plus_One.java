package com.rookie.travelinandroid.super_algorithm.test_08_01;

/**
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 *
 * 示例 1：
 *
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/plus-one
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Test_Plus_One {
    public static void main(String[] args) {
        int [] nums={1,2,3,4};
        plusOne(nums);

    }

    /**
     * 最后一位，如果是9，那么+1则=10，进1位，然后进入前面一位继续判断是否为9.那么+1则=10，继续进位，如此循环
     * @param nums
     */
    private static int[] plusOne(int[] nums) {
        int length = nums.length;
        for (int i = length-1; i >=0; i--) {
           if (nums[i]==9){
               nums[i]=0;
           }else {
                nums[i]+=1;
                return nums;
           }
        }
        nums=new int[nums.length+1];
        nums[0]=1;
        return nums;
    }
}
