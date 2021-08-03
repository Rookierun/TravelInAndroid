package com.rookie.travelinandroid.super_algorithm.test_08_01;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 */
public class Test_Merge_Two_Sorted_Linked_List {
    public static void main(String[] args) {
        mergeTwoSortedLinkedList(null,null);
        mergeTwoSortedLinkedListByIterator(null,null);
    }

    private static ListNode mergeTwoSortedLinkedListByIterator(ListNode l1,ListNode l2) {
        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = l1 == null ? l2 : l1;

        return prehead.next;


    }

    /**
     * 合并连个有序的链表，并且最终的链表仍然有序
     */
    private static ListNode mergeTwoSortedLinkedList(ListNode l1,ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoSortedLinkedList(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoSortedLinkedList(l1, l2.next);
            return l2;
        }



    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
