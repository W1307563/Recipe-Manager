package com.example.cmis202project;

public class RecipeBinarySearchTree{
    class Node {
        String key;
        Node left, right;

        public Node(String item) {
            key = item;
            left = right = null;
        }
    }

    Node root;

    RecipeBinarySearchTree() {
        root = null;
    }

    RecipeBinarySearchTree (String value) {
        root = new Node(value);
    }

    void insert(String key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, String key) {
        if (root == null) {
            root = new Node(key);
            return root;
        } else if (key.compareTo(root.key) < 0) {
            root.left = insertRec(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }

    void inorder() {
        inorderRec(root);
    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.key);
            inorderRec(root.right);
        }
    }

//    public static void main(String[] args) {
//        RecipeBinarySearchTree tree = new RecipeBinarySearchTree();
//
//        tree.insert("Pasta");
//        tree.insert("Pizza");
//        tree.insert("Candy");
//
//        tree.inorder();
//    }
}
