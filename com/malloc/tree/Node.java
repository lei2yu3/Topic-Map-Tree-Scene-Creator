package com.malloc.tree;

import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;

public class Node {
    public enum NodeType {
        // Scene(1, "scene"), Data(2, "data"), Value(3, "value");
        Scene("scene"), Data("data"), Value("value");

        // private int id;
        private String type;

        private NodeType() {
            // this(0, null);
            this(null);
        }

        // private NodeType(int i, String s) {
        private NodeType(String s) {
            // this.id = i;
            this.type = s;
        }

        // public int getNodeTypeIndex() {
        // return this.id;
        // }

        public String getNodeTypeString() {
            return this.type;
        }
    }

    private int index;
    private String name;
    private TopicIF topic;
    private NodeType nodetype;

    private Node parentNode;
    private ArrayList<Node> leafList;

    public Node() {
        this(0, null, null, null);
    }

    public Node(int i, String s, TopicIF t, NodeType n) {
        this.index = i;
        this.name = s;
        this.topic = t;
        this.nodetype = n;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public TopicIF getTopic() {
        return this.topic;
    }

    public NodeType getNodeType() {
        return this.nodetype;
    }

    public Node getParentNode() {
        return this.parentNode;
    }

    public ArrayList<Node> getLeafList() {
        return this.leafList;
    }

    // for test
    public static void main(String[] args) {
        TopicIF t;
        Node r = new Node(-1, "root", null, null);

        TopicIF t1 = null;
        Node n = new Scene(1, "ssads", t1);

        ArrayList<Node> al = new ArrayList<Node>();
        al.add(n);
        al.add(r);

        for (Node s : al) {
            System.out.println(s.getIndex());
            System.out.println(s.getName());
            System.out.println(s.getNodeType());
            System.out.println(s.getTopic());
            System.out.println(s.toString());
        }
    }
}

class Scene extends Node {

    Scene() {
        super();
    }

    Scene(int i, String s, TopicIF t) {
        super(i, s, t, NodeType.Scene);
    }
}

class Data extends Node {

    Data() {
        super();
    }

    Data(int i, String s, TopicIF t) {
        super(i, s, t, NodeType.Data);
    }
}

class Value extends Node {

    Value() {
        super();
    }

    Value(int i, String s, TopicIF t) {
        super(i, s, t, NodeType.Value);
    }
}
