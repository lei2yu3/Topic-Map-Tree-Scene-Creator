package com.malloc.test;

import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;

public class node {

    public int index;
    public String name;
    public TopicIF topic;
    public node parentNode;
    public ArrayList<node> childList;

    public node() throws IOException {
        this(0, null, null, null, null);
    }

    public node(int i, String s, TopicIF t, node p, ArrayList<node> c)
            throws IOException {

        this.index = i;
        this.name = s;
        this.topic = t;
        this.parentNode = p;
        this.childList = new ArrayList<node>();
    }

    public node getParentNode() {
        return this.parentNode;
    }

    public void setParentNode(node n) {
        this.parentNode = n;
    }

    // for test
    public static void main(String[] args) throws IOException {
        
        System.out.println("\n node DONE");
    }
}
