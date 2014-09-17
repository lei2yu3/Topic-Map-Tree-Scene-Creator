package com.malloc.tree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.malloc.tree.Node.NodeType;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Tree {

    // xtm file
    private static final String XTM = "hoho.xtm";

    // public String getXTM(){
    // return XTM;
    // }

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

    // topic map
    TopicMapIF aTopicmap = null;
    XTMTopicMapReader aReader = null;
    TopicMapBuilderIF aBuilder = null;

    // basic topic
    TopicIF topicRoot = null;
    TopicIF topicParentScene = null;
    TopicIF topicChildScene = null;
    TopicIF topicScene = null;
    TopicIF topicData = null;
    TopicIF topicValue = null;
    TopicIF topicRS = null;
    TopicIF topicSS = null;
    TopicIF topicSD = null;
    TopicIF topicDV = null;

    // root node
    Node nodeKami = null;
    TopicIF topicKami = null;

    // initialize root node and xtm file
    public Tree() throws IOException {

        // 读取xtm中的tm 重做
        /*
         * // 原始版本 TopicMapStoreIF tStore = new InMemoryTopicMapStore();
         * TopicMapIF tTopicmap = tStore.getTopicMap();
         * 
         * TopicMapImporterIF tReader = new XTMTopicMapReader(new
         * File("TREE.xtm")); tReader.importInto(tTopicmap);
         * 
         * TopicMapBuilderIF tBuilder = tTopicmap.getBuilder();
         */
        // 重做1
        /*
         * XTMTopicMapReader tReader = new XTMTopicMapReader(new
         * File("TREE.xtm")); TopicMapIF tTopicmap = tReader.read();
         * TopicMapBuilderIF tBuilder = tTopicmap.getBuilder();
         */

        // 重做2
        // XtmCreator xCreator = new XtmCreator();
        // TopicMapBuilderIF xBuilder = nCreator.cTopicmap.getBuilder();
        // System.out.println("nodetree1 " +
        // xCreator.cTopicmap.getTopics().size()
        // + " TOPICS");

        // create xtm with empty topic map
        new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
                .getTopicMap());

        // read topic map from xtm
        aReader = new XTMTopicMapReader(new File(XTM));
        aTopicmap = aReader.read();
        aBuilder = aTopicmap.getBuilder();

        // root node
        topicKami = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicKami, "Kami");
        nodeKami = new Node(-1, "Kami", topicKami, NodeType.Root, null, null);
        // nodeKami = new Node(-1, "Kami", topicKami, NodeType.Other, null);

        // write topic map to xtm
        new XTMTopicMapWriter(XTM).write(aTopicmap);

    }

    // 参数 当前节点（即父节点）(index, nName)，待加入的节点的参数(index, name, nodetype)
    // 返回 是否添加成功，父节点的getChildList长度是否加一
    // 流程 根据pNode.getIndex()找到父节点，添加pNode.getChildList().add(addNode);
    // 添加到树，然后根据pNode与addNode修改TM
    // add
    public boolean AddNode(int pIndex, String pName, int addIndex,
            String addName, NodeType nt) throws IOException {

        int treeSize = getSize();

        TopicIF topicAdd = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicAdd, addName);

        // 在树的根节点添加节点，将父节点设置为kamiNode
        if (pIndex == -1) {
            // meke association rs
            Node addNode = new Node(addIndex, addName, topicAdd, nt, nodeKami,
                    null);

            AssociationIF aRS = aBuilder.makeAssociation(topicSS);
            // AssociationRoleIF arRoot =
            aBuilder.makeAssociationRole(aRS, topicRoot, topicKami);
            // AssociationRoleIF arScene =
            aBuilder.makeAssociationRole(aRS, topicScene, topicAdd);

//          addNode.getParentNode() = nodeKami;
            nodeKami.getChildList().add(addNode);
        } else {

            // 不是根节点，则需根据index查找结点 TODO
            Node pNode = FindNode(pIndex);

            if (nt == NodeType.Scene) {

                // meke association ss
                Node addNode = new Node(addIndex, addName, topicAdd,
                        NodeType.Scene, pNode, null);

                AssociationIF aSS = aBuilder.makeAssociation(topicSS);
                // AssociationRoleIF arParentScene =
                aBuilder.makeAssociationRole(aSS, topicParentScene,
                        pNode.getTopic());
                // AssociationRoleIF arChildScene =
                aBuilder.makeAssociationRole(aSS, topicChildScene, topicAdd);

//              addNode.getParentNode() = pNode;
                pNode.getChildList().add(addNode);
                sizeIncrease();

            } else if (nt == NodeType.Data) {

                // meke association sd
                Node addNode = new Node(addIndex, addName, topicAdd,
                        NodeType.Data, pNode, null);

                AssociationIF aSD = aBuilder.makeAssociation(topicSD);
                // AssociationRoleIF arScene =
                aBuilder.makeAssociationRole(aSD, topicScene, pNode.getTopic());
                // AssociationRoleIF arData =
                aBuilder.makeAssociationRole(aSD, topicData, topicAdd);

//              addNode.getParentNode() = pNode;
                pNode.getChildList().add(addNode);
                sizeIncrease();

            } else if (nt == NodeType.Value) {

                // meke association dv
                Node addNode = new Node(addIndex, addName, topicAdd,
                        NodeType.Value, pNode, null);

                AssociationIF aDV = aBuilder.makeAssociation(topicDV);
                // AssociationRoleIF arData =
                aBuilder.makeAssociationRole(aDV, topicData, pNode.getTopic());
                // AssociationRoleIF arValue =
                aBuilder.makeAssociationRole(aDV, topicValue, topicAdd);

//                addNode.getParentNode() = pNode;
                pNode.getChildList().add(addNode);
                sizeIncrease();

            }

        }
        // modify tm, save to xtm
        new XTMTopicMapWriter(XTM).write(aTopicmap);

        int newTreeSize = getSize();

        return newTreeSize == treeSize + 1 ? true : false;

    }

    // 参数 当前节点（即子节点）
    // 返回 是否删除成功，父节点的getChildList是否为空
    // 流程 遍历以delNode为根的子树，依次删除
    // delete
    public boolean DeleteNode(int delIndex, String delName) throws IOException {

        int treeSize = getSize();
        int deteleSize = 0;

        Node delParentNode = FindNode(delIndex).getParentNode();

        // TODO 深度优先遍历以delNode为根的子树，（保存为ArrayList）依次删除
        if (delParentNode.getChildList() != null
                || delParentNode.getChildList().size() != 0) {

            for (int i = 0; i < delParentNode.getChildList().size(); i++) {
                // if (delParentNode.getChildList().get(i).getChildList() ==
                // null
                // || delParentNode.getChildList().get(i).getChildList().size()
                // == 0) {
                if (delParentNode.getChildList().get(i).getChildList()
                        .isEmpty()) {

                    // remove topic in tm
                    delParentNode.getChildList().get(i).getTopic().remove();

                    // remove node in child list
                    delParentNode.getChildList().remove(i);
//                    delParentNode.getChildList().get(i).getParentNode() = null;
                    sizeDecrease();
                    deteleSize++;

                } else {
                    // TODO 递归
                }
            }
        }

        // modify tm, save to xtm
        new XTMTopicMapWriter(XTM).write(aTopicmap);

        int newTreeSize = getSize();
        return newTreeSize == treeSize - deteleSize ? true : false;

    }

    // 深度遍历查找指定index的Node，返回该节点
    // find
    // public Node FindNode(int targetNodeIndex, Node r) throws IOException {
    public Node FindNode(int targetNodeIndex) throws IOException {

        Node targetNode = FindNode(targetNodeIndex);

        // TODO 深度优先遍历树，查找指定index的节点
        // if (targetNode.getChildList() != null &&
        // targetNode.getChildList().size() != 0) {
        if (!targetNode.getChildList().isEmpty()) {
            for (int i = 0; i < targetNode.getChildList().size(); i++) {

                if (targetNode.getChildList().get(i).getIndex() == targetNodeIndex) {
                    return targetNode.getChildList().get(i);
                } else {
                    // TODO
                    // FindNode(targetNodeIndex, r);
                }
            }

        }
        return targetNode;// TODO 返回查找到的节点
    }

    // for test
    public void init() throws IOException {

        // ParentScene-ChildScene, Root-Scene, Scene-Data, Data-Value

        // parent scene node in s-s
        topicParentScene = aBuilder.makeTopic();
        TopicNameIF tnParentScene = aBuilder.makeTopicName(topicParentScene,
                "ParentScene");

        // child scene node in s-s
        topicChildScene = aBuilder.makeTopic();
        TopicNameIF tnChildScene = aBuilder.makeTopicName(topicChildScene,
                "ChildScene");

        // scene node in s-d or r-s
        topicScene = aBuilder.makeTopic();
        TopicNameIF tnScene = aBuilder.makeTopicName(topicScene, "Scene");

        // data node in s-d or d-v
        topicData = aBuilder.makeTopic();
        TopicNameIF tnData = aBuilder.makeTopicName(topicData, "Data");

        // value node in d-v
        topicValue = aBuilder.makeTopic();
        TopicNameIF tnValue = aBuilder.makeTopicName(topicValue, "Value");

        // association type node r-s
        topicRS = aBuilder.makeTopic();
        TopicNameIF tnRS = aBuilder.makeTopicName(topicRS, "Root-Scene");

        // association type node s-s
        topicSS = aBuilder.makeTopic();
        TopicNameIF tnSS = aBuilder.makeTopicName(topicSS, "Scene-Scene");

        // association type node s-d
        topicSD = aBuilder.makeTopic();
        TopicNameIF tnSD = aBuilder.makeTopicName(topicSD, "Scene-Data");

        // association type node d-v
        topicDV = aBuilder.makeTopic();
        TopicNameIF tnDV = aBuilder.makeTopicName(topicDV, "Data-Value");

        new XTMTopicMapWriter(XTM).write(aTopicmap);

    }

    // for test
    public static void main(String[] args) throws IOException {

        Tree nt = new Tree();

        // initialize topic map with basic topics and associations
        // nt.init();

        // add and delete
        // nt.AddNode(0, null, 0, null, NodeType.Scene);
        // nt.DeleteNode(0, null);

        System.out.println("\n nodetree DONE");

    }
}
