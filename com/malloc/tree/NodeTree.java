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

    // ����   ��ǰ�ڵ㣨�����ڵ㣩��������Ľڵ�Ĳ�����index;name;topic;nodetype;��
    // ����   �Ƿ���ӳɹ������ڵ��getLeafList�����Ƿ��һ
    // ����   ����pNode.getIndex()�ҵ����ڵ㣬���pNode.getLeafList().add(addNode);
    //      ��ӵ�����Ȼ�����pNode��addNode�޸�TM
    //          
    public void AddNode(Node pNode, Node addNode) {
        FindNode(pNode.getIndex());
        if (pNode.getLeafList() == null) {
            // pNode.getLeafList() = new ArrayList<Node>();
        }
        pNode.getLeafList().add(addNode);

        // TODO  modify tm, save to xtm
    }

    // ����   ��ǰ�ڵ㣨���ӽڵ㣩
    // ����   �Ƿ�ɾ���ɹ������ڵ��getLeafList�Ƿ�Ϊ��
    // ����   ������delNodeΪ��������������ɾ��
    //          
    public void DeleteNode(Node delNode) {
        FindNode(delNode.getIndex());

        // TODO  ������ȱ�����delNodeΪ��������

        // TODO  modify tm, save to xtm
    }

    public Node FindNode(int fIndex) {

        // TODO  ������ȱ�����

        return new Node();// TODO ���ز��ҵ��Ľڵ�
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

        // TODO ��ȡ�Ѵ��ڵ�XTM�ļ����ڴ�XTM�н������ɾ��
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
