package com.rookie.travelinandroid.super_algorithm.test_08_11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的遍历
 */
public class Tree_Traversal {
    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.leftChild = new TreeNode(2);
        treeNode.rightChild = new TreeNode(3);
        List<Integer> result = new ArrayList<>();
//        inOrderTraversal(result, treeNode);
//        preOrderTraversal(result, treeNode);
//        postOrderTraversal(result, treeNode);
        layerOrderTraversal(treeNode);
    }

    /**
     * 层序遍历
     *
     * @param treeNode
     */
    private static void layerOrderTraversal(TreeNode treeNode) {
        LinkedList<TreeNode> linkedList = new LinkedList<>();
        linkedList.add(treeNode);
        while (!linkedList.isEmpty()) {
            treeNode = linkedList.poll();
            if (treeNode == null) {
                continue;
            }
            System.out.println(treeNode.val+",");
            if (treeNode.leftChild != null) {
                linkedList.offer(treeNode.leftChild);
            }
            if (treeNode.rightChild != null) {
                linkedList.offer(treeNode.rightChild);
            }
        }
    }

    /**
     * 后序遍历
     * 左右根
     *
     * @param result
     * @param treeNode
     */
    private static void postOrderTraversal(List<Integer> result, TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        postOrderTraversal(result, treeNode.leftChild);
        postOrderTraversal(result, treeNode.rightChild);
        result.add(treeNode.val);
    }

    /**
     * 前序遍历
     * 根左右
     * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/
     *
     * @param result
     * @param treeNode
     */
    private static void preOrderTraversal(List<Integer> result, TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        result.add(treeNode.val);
        preOrderTraversal(result, treeNode.leftChild);
        preOrderTraversal(result, treeNode.rightChild);
    }

    /**
     * 中序遍历
     * 左-》根-》右
     * 递归
     * 94. 二叉树的中序遍历
     * * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
     * * <p>
     * * 输入：root = [1,null,2,3]
     * * 输出：[1,3,2]
     * * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
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
