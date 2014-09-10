package com.malloc.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;

public class NodeTree {

    public void AddNode(Node pNode, Node addNode) {
        FindNode(pNode);
        if (pNode.getLeafList() == null) {
            // pNode.getLeafList() = new ArrayList<Node>();
        }
        pNode.getLeafList().add(addNode);

        // modify tm, save to xtm
    }

    public void DeleteNode(Node delNode) {
        FindNode(delNode);

        // 深度优先遍历以delNode为根的子树

        // modify tm, save to xtm
    }

    public int FindNode(Node fNode) {

        // 深度优先遍历树

        return 1;
    }

    // public boolean isLeaf() {
    // if (childList == null) {
    // return true;
    // } else {
    // if (childList.isEmpty()) {
    // return true;
    // } else {
    // return false;
    // }
    // }
    // }

    // for test
    public static void main(String[] args) {
        

        TopicMapStoreIF store = new InMemoryTopicMapStore();
        TopicMapIF topicmap = store.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();
        

        String name = "qqq";
        TopicIF t1 = builder.makeTopic();
        builder.makeTopicName(t1, name);
        
        Node s = new Scene(1, name, t1);
        System.out.println(s.getIndex());
        System.out.println(s.getName());
        System.out.println(s.getNodeType());
        System.out.println(s.getTopic().getTopicNames());
        
    }
}
