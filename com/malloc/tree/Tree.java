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

        // ��ȡxtm�е�tm ����
        /*
         * // ԭʼ�汾 TopicMapStoreIF tStore = new InMemoryTopicMapStore();
         * TopicMapIF tTopicmap = tStore.getTopicMap();
         * 
         * TopicMapImporterIF tReader = new XTMTopicMapReader(new
         * File("TREE.xtm")); tReader.importInto(tTopicmap);
         * 
         * TopicMapBuilderIF tBuilder = tTopicmap.getBuilder();
         */
        // ����1
        /*
         * XTMTopicMapReader tReader = new XTMTopicMapReader(new
         * File("TREE.xtm")); TopicMapIF tTopicmap = tReader.read();
         * TopicMapBuilderIF tBuilder = tTopicmap.getBuilder();
         */

        // ����2
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

    // ���� ��ǰ�ڵ㣨�����ڵ㣩(index, nName)��������Ľڵ�Ĳ���(index, name, nodetype)
    // ���� �Ƿ���ӳɹ������ڵ��getChildList�����Ƿ��һ
    // ���� ����pNode.getIndex()�ҵ����ڵ㣬���pNode.getChildList().add(addNode);
    // ��ӵ�����Ȼ�����pNode��addNode�޸�TM
    // add
    public boolean AddNode(int pIndex, String pName, int addIndex,
            String addName, NodeType nt) throws IOException {

        int treeSize = getSize();

        TopicIF topicAdd = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicAdd, addName);

        // �����ĸ��ڵ���ӽڵ㣬�����ڵ�����ΪkamiNode
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

            // ���Ǹ��ڵ㣬�������index���ҽ�� TODO
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

    // ���� ��ǰ�ڵ㣨���ӽڵ㣩
    // ���� �Ƿ�ɾ���ɹ������ڵ��getChildList�Ƿ�Ϊ��
    // ���� ������delNodeΪ��������������ɾ��
    // delete
    public boolean DeleteNode(int delIndex, String delName) throws IOException {

        int treeSize = getSize();
        int deteleSize = 0;

        Node delParentNode = FindNode(delIndex).getParentNode();

        // TODO ������ȱ�����delNodeΪ����������������ΪArrayList������ɾ��
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
                    // TODO �ݹ�
                }
            }
        }

        // modify tm, save to xtm
        new XTMTopicMapWriter(XTM).write(aTopicmap);

        int newTreeSize = getSize();
        return newTreeSize == treeSize - deteleSize ? true : false;

    }

    // ��ȱ�������ָ��index��Node�����ظýڵ�
    // find
    // public Node FindNode(int targetNodeIndex, Node r) throws IOException {
    public Node FindNode(int targetNodeIndex) throws IOException {

        Node targetNode = FindNode(targetNodeIndex);

        // TODO ������ȱ�����������ָ��index�Ľڵ�
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
        return targetNode;// TODO ���ز��ҵ��Ľڵ�
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
