package com.malloc.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class tree {

    private static final String XTM = "hoho.xtm";

    //
    private int size = 0;

    public int getSize() {
        return this.size;
    }

    public int sizeIncrease() {
        return ++size;
    }

    public int sizeDecrease() {
        return --size;
    }

    //
    static TopicMapIF aTopicmap = null;
    static XTMTopicMapReader aReader = null;
    static TopicMapBuilderIF aBuilder = null;

    public static node nodeBigBro = null;
    public TopicIF topicBigBro = null;

    ArrayList<node> an = new ArrayList<node>();

    public tree() throws IOException {

        new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
                .getTopicMap());

        aReader = new XTMTopicMapReader(new File("hoho.xtm"));
        aTopicmap = aReader.read();
        aBuilder = aTopicmap.getBuilder();

        topicBigBro = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicBigBro, "BigBro");
        nodeBigBro = new node(-1, "BigBro", topicBigBro, null, null);
        sizeIncrease();

        TopicIF topic1 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic1, "haha");
        node node1 = new node(1, "haha", topic1, null, null);
        nodeBigBro.childList.add(node1);
        sizeIncrease();

        TopicIF topic2 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic2, "yoyo");
        node node2 = new node(2, "yoyo", topic2, null, null);
        nodeBigBro.childList.add(node2);
        sizeIncrease();

        TopicIF topic3 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic3, "dada");
        node node3 = new node(3, "dada", topic3, null, null);
        nodeBigBro.childList.add(node3);
        sizeIncrease();

        TopicIF topic21 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic21, "yiyi");
        node node21 = new node(21, "yiyi", topic21, null, null);
        node2.childList.add(node21);
        sizeIncrease();

        TopicIF topic22 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic22, "yaya");
        node node22 = new node(22, "yaya", topic22, null, null);
        node2.childList.add(node22);
        sizeIncrease();

        TopicIF topic32 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic32, "didi");
        node node32 = new node(32, "didi", topic32, null, null);
        node3.childList.add(node32);
        sizeIncrease();

        TopicIF topic321 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic321, "dodo");
        node node321 = new node(321, "dodo", topic321, null, null);
        node32.childList.add(node321);
        sizeIncrease();

        new XTMTopicMapWriter(XTM).write(aTopicmap);

    }

    public boolean addNode(int pIndex, String pName, int addIndex,
            String addName) throws IOException {

        int treeSize = getSize();
        int newTreeSize = 0;

        TopicIF tt = aBuilder.makeTopic();
        aBuilder.makeTopicName(tt, addName);

        node n = new node(addIndex, addName, tt, null, null);
        sizeIncrease();

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        return newTreeSize == treeSize + 1 ? true : false;

    }

    public boolean deleteNode(int deleteIndex, String deleteName)
            throws IOException {

        int treeSize = getSize();
        int newTreeSize = 0;
        int deteleSize = 0;

        // sizeDecrease();deteleSize++;

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        return newTreeSize == treeSize - deteleSize ? true : false;

    }

    public node FindNode(int num, node r) throws IOException {

        // node newNode = new node();
        String s = "bb";
        // 以传入的节点为根，深度优先遍历
        if (r.childList != null && r.childList.size() != 0) {
            System.out.println("if r.childList is not null!");
            for (node n : r.childList) {
                System.out.println("n.index = " + n.index + "; n.name = "
                        + n.name);
                
                FindNode(num, n);
                
//                if (n.index == num) {
//                    System.out.println("==" + (n.index == num));
//                    s = n.name.toString();
//                } else {
//                    FindNode(num, n);
//                }
            }
        }

        // 没找到
        return new node();
    }

    public static void main(String[] args) throws IOException {

        tree t = new tree();
        t.FindNode(21, nodeBigBro);
        // System.out.println(t.FindNode(22, nodeBigBro));

        System.out.println("$$$$1 " + aTopicmap.getTopics().size());

        System.out.println("$$$$2 " + aTopicmap.getTopics().size());

        System.out.println("$$$$3 " + aTopicmap.getTopics().size());

        System.out.println("$$$$4 " + aTopicmap.getTopics().size());

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        System.out.println("\n tree DONE");
    }
}
