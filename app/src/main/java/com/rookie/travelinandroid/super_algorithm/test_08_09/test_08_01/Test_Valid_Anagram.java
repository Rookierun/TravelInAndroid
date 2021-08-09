package com.rookie.travelinandroid.super_algorithm.test_08_09.test_08_01;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * <p>
 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 * 示例 1:
 * <p>
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-anagram
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Test_Valid_Anagram {
    public static void main(String[] args) {
        String text1 = "aacc";
        String text2 = "ccac";
        System.out.println(validAnagram(text1, text2));
        boolean valid = validAnagramByArray(text1, text2);
    }

    /**
     * 数组+排序+Arrays.equals(arr1,arr2)
     * 执行用时：
     * 3 ms
     * , 在所有 Java 提交中击败了
     * 82.56%
     * 的用户
     * @param text1
     * @param text2
     * @return
     */
    private static boolean validAnagramByArray(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return false;
        }
        if (text1.length() != text2.length()) {
            return false;
        }
        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, (chars2));
    }

    /**
     * 两次循环+HashMap
     * 执行用时：
     * 16 ms
     * , 在所有 Java 提交中击败了
     * 20.74%
     * 的用户
     *
     * @param text1
     * @param text2
     * @return
     */
    private static boolean validAnagram(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return false;
        }
        if (text1.length() != text2.length()) {
            return false;
        }
        HashMap<Character, Integer> map1 = new HashMap<>();
        for (int i = 0; i < text1.length(); i++) {
            char key = text1.charAt(i);
            boolean contains = map1.containsKey(key);
            if (contains) {
                Integer integer = map1.get(key);
                map1.put(key, ++integer);
            } else {
                map1.put(key, 1);
            }
        }
        for (int i = 0; i < text2.length(); i++) {
            char key = text2.charAt(i);
            if (map1.containsKey(key)) {
                Integer value = map1.get(key);
                if (value > 1) {
                    map1.put(key, --value);
                } else {
                    map1.remove(key);
                }
            } else {
                map1.remove(key);
            }
        }


        return map1.isEmpty();
    }


}
