package com.rookie.travelinandroid.super_algorithm.test_08_01;

/**
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 * 进阶：
 *
 * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
 * 你可以使用空间复杂度为O(1) 的原地算法解决这个问题吗？
 * 
 * 
 *
 * 示例 1:
 *
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右旋转 1 步: [7,1,2,3,4,5,6]
 * 向右旋转 2 步: [6,7,1,2,3,4,5]
 * 向右旋转 3 步: [5,6,7,1,2,3,4]
 *提示：
 *
 * 1 <= nums.length <= 2 * 104
 * -231 <= nums[i] <= 231 - 1
 * 0 <= k <= 105
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotate-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Test_Rotate_Array {
    public static void main(String[] args) {
        int []nums={5,6,7,1,2,3,4};
        int count =3;
        rotateArray(nums,count);
//        rotateArrByNew(nums,count);
    }

    /**
     * 循环count，然后用nums[length-count]与nums[0]交换
     * @param nums
     * @param count
     */
    private static void rotateArray(int[] nums, int count) {
        int start = count + 1;
        int length=nums.length;
        for (int i = start; i < nums.length; i++) {
//            System.out.println(nums[i]);

        }
        int temp=-1;
        for (int j = 0; j < length-1;j++) {

            if (j==length-2){
                //最后一个数字
                nums[0]=nums[j];
                printArr(nums);
            }else{
                if (temp!=-1){
                    nums[j]=temp;
                }else {
                    temp=nums[j+1];
                }
                nums[j+1]=nums[j];

                printArr(nums);
                System.out.println("----------------------");
            }
        }
        printArr(nums);
    }
    private static void rotateArrByNew(int [] nums,int count){
        int length = nums.length;
        int [] result=new int[length];
        for (int i = 0; i < length; i++) {
            result[(i+count)%length]=nums[i];
        }
        System.arraycopy(result,0,nums,0,length);
        printArr(result);
    }
    private static void printArr(int[] nums){
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i < nums.length; i++) {
            if (i==0){
                buffer.append("[ ");
            }
            if (i!= nums.length-1){
                buffer.append(nums[i]).append(",");
            }
            if (i==nums.length-1){
                buffer.append(nums[i]);
                buffer.append(" ]");
            }
        }
        System.out.println(buffer.toString());
    }
}
