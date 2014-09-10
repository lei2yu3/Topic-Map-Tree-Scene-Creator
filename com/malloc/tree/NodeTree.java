package com.malloc.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.malloc.tree.Node.NodeType;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;

public class NodeTree {

    // 参数   当前节点（即父节点），待加入的节点的参数（index;name;topic;nodetype;）
    // 返回   是否添加成功，父节点的getLeafList长度是否加一
    // 流程   根据pNode.getIndex()找到父节点，添加pNode.getLeafList().add(addNode);
    //      添加到树，然后根据pNode与addNode修改TM
    //          
    public void AddNode(Node pNode, Node addNode) {
        FindNode(pNode.getIndex());
        if (pNode.getLeafList() == null) {
            // pNode.getLeafList() = new ArrayList<Node>();
        }
        pNode.getLeafList().add(addNode);

        // TODO  modify tm, save to xtm
    }

    // 参数   当前节点（即子节点）
    // 返回   是否删除成功，父节点的getLeafList是否为空
    // 流程   遍历以delNode为根的子树，依次删除
    //          
    public void DeleteNode(Node delNode) {
        FindNode(delNode.getIndex());

        // TODO  深度优先遍历以delNode为根的子树

        // TODO  modify tm, save to xtm
    }

    public Node FindNode(int fIndex) {

        // TODO  深度优先遍历树

        return new Node();// TODO 返回查找到的节点
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

        // TODO 读取已存在的XTM文件，在此XTM中进行添加删除
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
        System.out.println(s.getTopic().getObjectId());
        System.out.println(s.getTopic().getItemIdentifiers());

    }
}
