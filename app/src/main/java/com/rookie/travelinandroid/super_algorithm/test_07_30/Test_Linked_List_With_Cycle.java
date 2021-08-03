package com.rookie.travelinandroid.super_algorithm.test_07_30;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 141. 环形链表
 * 给定一个链表，判断链表中是否有环。
 * <p>
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
 * 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 * <p>
 * 如果链表中存在环，则返回 true 。 否则，返回 false 。
 * <p>
 * <p>
 * 示例 1：
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 */
public class Test_Linked_List_With_Cycle {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode fourth = new ListNode(4);
        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = second;

        System.out.println("有环吗？：" + hasCycleByForced(head));
        System.out.println("有环吗？：" + hasCycleByLeetCode(head));
        System.out.println("有环吗？：" + hasCycleByFastSlowPointer(head));
    }

    /**
     * 快慢指针
     * 套圈法
     * 如果存在环，那么快指针一定会先进入环，等到满指针也进入环后，那么快指针一定会在某一刻追上满指针
     * 执行用时：0 ms, 在所有 Java 提交中击败了100.00%的用户
     *
     * @param head
     * @return
     */
    private static boolean hasCycleByFastSlowPointer(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fastNode = head;
        ListNode slowNode = head;
        do {
            if (fastNode.next == null || slowNode == null) {
                return false;
            }
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        } while (fastNode != slowNode);
        return true;
    }

    /**
     * 执行用时：
     * 4 ms, 在所有 Java 提交中击败了20.15%
     *
     * @param head
     * @return
     */
    private static boolean hasCycleByLeetCode(ListNode head) {
        if (head == null) {
            return false;
        }
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            boolean add = set.add(head);
            if (!add) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 思路：遍历链表，添加到HashMap中，遍历HashMap，
     * 执行用时：781 m
     *
     * @param head
     * @return
     */
    private static boolean hasCycleByForced(ListNode head) {

        if (head == null) {
            return false;
        }
        int j = 0;
        HashMap<Integer, ListNode> map = new HashMap();
        //这里需要注意，如果有环的话，这里会陷入死循环的
        while (head != null) {
            if (map.containsValue(head)) {
                //这里代表已经添加过了，但是要判断这个已经添加过的就是目标position
                return true;
            }
            map.put(j++, head);
            head = head.next;
        }


        return false;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
            next = null;
        }

    }
}
