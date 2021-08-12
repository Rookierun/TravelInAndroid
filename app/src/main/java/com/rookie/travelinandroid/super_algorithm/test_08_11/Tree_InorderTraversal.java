package com.rookie.travelinandroid.super_algorithm.test_08_11;

import java.util.ArrayList;
import java.util.List;

/**
 * 94. 二叉树的中序遍历
 * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
 * <p>
 * 输入：root = [1,null,2,3]
 * 输出：[1,3,2]
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 */
public class Tree_InorderTraversal {
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode();
        List<Integer> result = new ArrayList<>();
        inOrderTraversal(result, treeNode);
    }

    /**
     * 中序遍历
     * 左-》根-》右
     * 递归
     *
     * @param result
     * @param treeNode
     */
    private static void inOrderTraversal(List<Integer> result, TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        inOrderTraversal(result, treeNode.leftChild);
        result.add(treeNode.val);
        inOrderTraversal(result, treeNode.rightChild);
    }

    public static class TreeNode {
        private int val;
        private TreeNode leftChild;
        private TreeNode rightChild;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode leftChild, TreeNode rightChild) {
            this.val = val;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }
}
