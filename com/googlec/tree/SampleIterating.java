package com.googlec.tree;

class SampleIterating {

    public static void main(String[] args) {
        TreeNode<String> treeRoot = SampleData.getSet1();
//        TreeNode<String> treeRoot = SampleData.getSetSOF();
        for (TreeNode<String> node : treeRoot) {
            String indent = createIndent(node.getLevel());
            System.out.println(indent + node.data);
        }
    }

    private static String createIndent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

}