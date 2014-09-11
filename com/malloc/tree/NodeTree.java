package com.malloc.tree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.malloc.tree.Node.NodeType;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class NodeTree {

    // tree size
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

    // private static Node kamiNode = null;

    // topic
    private static TopicIF topicRoot = null;
    private static TopicIF topicParentScene = null;
    private static TopicIF topicLeafScene = null;
    private static TopicIF topicScene = null;
    private static TopicIF topicData = null;
    private static TopicIF topicValue = null;
    private static TopicIF topicSD = null;
    private static TopicIF topicSS = null;
    private static TopicIF topicDV = null;
    private static TopicIF topicRS = null;

    // 初始化，根节点，xtm
    NodeTree() throws IOException {

        // TODO 重做
        XTMTopicMapReader tReader = new XTMTopicMapReader(new File("TREE.xtm"));
        TopicMapIF tTopicmap = tReader.read();
        TopicMapBuilderIF tBuilder = tTopicmap.getBuilder();

        /*        
                // read topic map from xtm
                TopicMapStoreIF tStore = new InMemoryTopicMapStore();
                TopicMapIF tTopicmap = tStore.getTopicMap();

                TopicMapImporterIF tReader = new XTMTopicMapReader(new File("TREE.xtm"));
                tReader.importInto(tTopicmap);

                TopicMapBuilderIF tBuilder = tTopicmap.getBuilder();
        */
        /*
        // node role type
        topicRoot = tBuilder.makeTopic();
        TopicNameIF tnRoot = tBuilder.makeTopicName(topicRoot, "Root");
                
        topicParentScene = tBuilder.makeTopic();
        TopicNameIF tnParentScene = tBuilder.makeTopicName(topicParentScene,
                "ParentScene");

        topicLeafScene = tBuilder.makeTopic();
        TopicNameIF tnLeafScene = tBuilder.makeTopicName(topicLeafScene,
                "LeafScene");

        topicScene = tBuilder.makeTopic();
        TopicNameIF tnScene = tBuilder.makeTopicName(topicScene, "Scene");

        topicData = tBuilder.makeTopic();
        TopicNameIF tnData = tBuilder.makeTopicName(topicData, "Data");

        topicValue = tBuilder.makeTopic();
        TopicNameIF tnValue = tBuilder.makeTopicName(topicValue, "Value");

        // node association type
        topicRS = tBuilder.makeTopic();
        TopicNameIF tnRS = tBuilder.makeTopicName(topicRS, "Root-Scene");

        topicSD = tBuilder.makeTopic();
        TopicNameIF tnSD = tBuilder.makeTopicName(topicSD, "Scene-Data");

        topicSS = tBuilder.makeTopic();
        TopicNameIF tnSS = tBuilder.makeTopicName(topicSS, "Scene-Scene");

        topicDV = tBuilder.makeTopic();
        TopicNameIF tnDV = tBuilder.makeTopicName(topicDV, "Data-Value");
                */
        // System.out.println(getSize());

        // TODO bug 无法写入TREE.xtm
        System.out.println("NT1" + tTopicmap.getTopics().size() + " TOPICS");
        Node kamiNode = new Node(-1, "KAMI", NodeType.Other, null, null);
        sizeIncrease();
        // System.out.println(getSize());

        // write topic map to xtm
        new XTMTopicMapWriter("TREE.xtm").write(tTopicmap);

        System.out.println("NT2" + tTopicmap.getTopics().size() + " TOPICS");

        // tStore.commit();
        // tStore.close();
    }

    // 参数 当前节点（即父节点），待加入的节点的参数(index;name;topic;nodetype;)
    // 返回 是否添加成功，父节点的getLeafList长度是否加一
    // 流程 根据pNode.getIndex()找到父节点，添加pNode.getLeafList().add(addNode);
    // 添加到树，然后根据pNode与addNode修改TM
    // add
    // public void AddNode(Node pNode, Node addNode) {
    public boolean AddNode(int pIndex, String pName, int addIndex,
            String addName, NodeType nt) throws IOException {
        int treeSize = getSize();
        int newTreeSize = 0;

        Node pNode = FindNode(pIndex);

        // TODO 在输的根节点添加节点，将父节点设置为kamiNode
        if (pIndex == -1) {
            // pNode = kamiNode;
        }

        if (pNode.getLeafList() == null) {
            // pNode.getLeafList() = new ArrayList<Node>();
        }

        // TODO new topic addTopic
        Node addNode = new Node(addIndex, addName, nt, pNode, null);

        pNode.getLeafList().add(addNode);
        sizeIncrease();

        if (nt == NodeType.Scene) {
            // TODO meke association ss
        } else if (nt == NodeType.Data) {
            // TODO meke association sd

        } else if (nt == NodeType.Value) {
            // TODO meke association dv

        }
        // TODO modify tm, save to xtm

        return newTreeSize == treeSize + 1 ? true : false;
    }

    // 参数 当前节点（即子节点）
    // 返回 是否删除成功，父节点的getLeafList是否为空
    // 流程 遍历以delNode为根的子树，依次删除
    // delete
    public boolean DeleteNode(int delIndex, String delName) throws IOException {
        int treeSize = 0;
        int newTreeSize = 0;

        Node delNode = FindNode(delIndex);

        // TODO 深度优先遍历以delNode为根的子树
        // sizeDecrease();

        // TODO modify tm, save to xtm

        return newTreeSize == treeSize - 1 ? true : false;
    }

    // 深度遍历查找指定index的Node，返回该节点
    // find
    public Node FindNode(int fIndex) throws IOException {

        Node goalNode = null;

        // TODO 深度优先遍历树

        return goalNode;// TODO 返回查找到的节点
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
    public static void fun(int iii, String sss) throws IOException {
        TopicMapStoreIF xStore = new InMemoryTopicMapStore();
        TopicMapIF xTopicmap = xStore.getTopicMap();

        TopicMapImporterIF xReader = new XTMTopicMapReader(new File("TREE.xtm"));
        xReader.importInto(xTopicmap);

        TopicMapBuilderIF xBuilder = xTopicmap.getBuilder();

        // TopicIF hh = xBuilder.makeTopic();
        // xBuilder.makeTopicName(hh, "hooooo");
        Node s = new Node(iii, sss, NodeType.Scene, null, null);
        //
        // System.out.println(s.getIndex());
        // System.out.println(s.getName());
        // System.out.println(s.getNodeType());
        // System.out.println(s.getTopic().getTopicNames());
        // System.out.println(s.getTopic().getObjectId());
        // System.out.println(s.getTopic().getItemIdentifiers());
        // System.out.println("=================");

        // new XTMTopicMapWriter("TREE.xtm").write(xTopicmap);

        xStore.commit();
        xStore.close();
    }

    // for test
    public static void main(String[] args) throws IOException {

        // TODO 重做
        XTMTopicMapReader mReader = new XTMTopicMapReader(new File("TREE.xtm"));
        TopicMapIF mTopicmap = mReader.read();
        TopicMapBuilderIF mBuilder = mTopicmap.getBuilder();
        /*
        TopicMapStoreIF mStore = new InMemoryTopicMapStore();
        TopicMapIF mTopicmap = mStore.getTopicMap();

        TopicMapImporterIF mReader = new XTMTopicMapReader(new File("TREE.xtm"));
        mReader.importInto(mTopicmap);

        TopicMapBuilderIF mBuilder = mTopicmap.getBuilder();
        System.out.println("mmmmm " + mTopicmap.getTopics().size() + " TOPICS");
        */
        /*
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
            System.out.println("=================");
        */

        System.out.println("main1 " + mTopicmap.getTopics().size() + " TOPICS");
        NodeTree tree = new NodeTree();
        System.out.println("main2 " + mTopicmap.getTopics().size() + " TOPICS");
        // Node kamiNode = new Node(-1, "Kami", NodeType.Other, null, null);
        // mStore.commit();
        // mStore.close();
        // fun(222,"hoooo");
        // fun(33,"Boocoo");
        // fun(123,"kaaaami");

        // Node kamiNode = new Node(-1, "Kami", NodeType.Other, null, null);
        // Node hh = new Node(11, "haha", NodeType.Data, null, null);
        System.out.println(tree.getSize());

        // System.out.println("mmmmm " + mTopicmap.getTopics().size() +
        // " TOPICS");

        System.out.println("\n nodetree DONE");
    }
}
