package com.rookie.travelinandroid.super_algorithm.test_08_09;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 49. 字母异位词分组
 * 给定一个字符串数组，将字母异位词组合在一起。可以按任意顺序返回结果列表。
 * <p>
 * 字母异位词指字母相同，但排列不同的字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/group-anagrams
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-anagram
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Test_Group_Anagram {
    public static void main(String[] args) {
        String text1 = "aacc";
        String text2 = "ccac";
        String[] arrs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagram(arrs));
        groupAnagramByArray(arrs);
    }

    private static boolean groupAnagram(String[] arrs) {
        return false;
    }

    /**
     * 利用HashMap+数组排序
     *
     * @param strs
     * @return
     */
    private static List<List<String>> groupAnagramByArray(String[] strs) {
        if (strs == null || strs.length == 0)
            return null;
        HashMap<String, List<String>> resultMap = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            System.out.println(chars);
            if (resultMap.containsKey(String.valueOf(chars))) {
                resultMap.get(String.valueOf(chars)).add(str);
            } else {
                ArrayList<String> value = new ArrayList<>();
                value.add(str);
                resultMap.put(String.valueOf(chars), value);
            }

        }
        return new ArrayList<>(resultMap.values());
    }


}
