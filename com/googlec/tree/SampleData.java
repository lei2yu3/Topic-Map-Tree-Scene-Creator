package com.googlec.tree;

class SampleData {


    public static TreeNode<Point> getPoints() {
        Point r;

        r = new Point(0, 0);
        TreeNode<Point> root = new TreeNode<Point>(r);
        {
            r = new Point(1, 1);
            TreeNode<Point> node0 = root.addChild(r);
            r = new Point(2, 2);
            TreeNode<Point> node1 = root.addChild(r);
            r = new Point(3, 3);
            TreeNode<Point> node2 = root.addChild(r);
            {
                TreeNode<Point> node20 = node2.addChild(null);
                r = new Point(21, 21);
                TreeNode<Point> node21 = node2.addChild(r);
                {
                    r = new Point(210, 210);
                    TreeNode<Point> node210 = node20.addChild(r);
                }
            }
        }
        return root;
    }
    
    public static TreeNode<String> getSet1() {
        TreeNode<String> root = new TreeNode<String>("root");
        {
            TreeNode<String> node0 = root.addChild("node0");
            TreeNode<String> node1 = root.addChild("node1");
            TreeNode<String> node2 = root.addChild("node2");
            {
                TreeNode<String> node20 = node2.addChild(null);
                TreeNode<String> node21 = node2.addChild("node21");
                {
                    TreeNode<String> node210 = node21.addChild("node210");
                    TreeNode<String> node211 = node21.addChild("node211");
                }
            }
            TreeNode<String> node3 = root.addChild("node3");
            {
                TreeNode<String> node30 = node3.addChild("node30");
            }
        }

        return root;
    }

    public static TreeNode<String> getSetSOF() {
        TreeNode<String> root = new TreeNode<String>("root");
        {
            TreeNode<String> node0 = root.addChild("node0");
            TreeNode<String> node1 = root.addChild("node1");
            TreeNode<String> node2 = root.addChild("node2");
            {
                TreeNode<String> node20 = node2.addChild(null);
                TreeNode<String> node21 = node2.addChild("node21");
                {
                    TreeNode<String> node210 = node20.addChild("node210");
                }
            }
        }

        return root;
    }
}