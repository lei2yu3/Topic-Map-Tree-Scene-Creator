package com.malloc.tree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.malloc.tree.Node.NodeType;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
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

    private int sizeIncrease() {
        return ++size;
    }

    private int sizeDecrease() {
        return --size;
    }

    // topic map
    static TopicMapIF aTopicmap = null;
    XTMTopicMapReader aReader = null;
    TopicMapBuilderIF aBuilder = null;

    // basic topic
    static TopicIF topicRoot = null;
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

    // parameter 当前节点（即父节点）的(index, name)，待添加节点的(index, name, nodetype)
    // return 返回树的大小是否加一
    // 查找父节点，添加子节点到父节点，添加关联到TM
    public boolean AddNode(int pIndex, String pName, int addIndex,
            String addName, NodeType nt) throws IOException {

        int treeSize = getSize();

        // new a topic
        TopicIF topicAdd = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicAdd, addName);

        // 在树的根节点添加节点，将父节点直接设置为kamiNode
        if (pIndex == -1) {
            // meke association rs
            Node addNode = new Node(addIndex, addName, topicAdd, nt, nodeKami,
                    null);

            AssociationIF aRS = aBuilder.makeAssociation(topicRS);
            // AssociationRoleIF arRoot =
            aBuilder.makeAssociationRole(aRS, topicRoot, topicKami);
            // AssociationRoleIF arScene =
            aBuilder.makeAssociationRole(aRS, topicScene, topicAdd);

            addNode.setParentNode(nodeKami);
            nodeKami.getChildList().add(addNode);
            sizeIncrease();
        } else {

            // 不是根节点，则需根据index查找结点
            Node pNode = FindNode(pIndex);

            if (pNode == null)
                return false;

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

                addNode.setParentNode(pNode);
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

                addNode.setParentNode(pNode);
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

                addNode.setParentNode(pNode);
                pNode.getChildList().add(addNode);
                sizeIncrease();

            }
        }

        // modify tm, save to xtm
        new XTMTopicMapWriter(XTM).write(aTopicmap);

        int newTreeSize = getSize();

        return newTreeSize == treeSize + 1 ? true : false;

    }

    // TODO delete
    private ArrayList<Node> deleteList = new ArrayList<Node>();

    // 遍历子节点，不包含根节点
    private void findChildTreeNode(Node startNode) {

        if (startNode.getChildList() != null
                && startNode.getChildList().size() != 0) {
            for (Node n : startNode.getChildList()) {
                deleteList.add(n);
                findChildTreeNode(n);
            }
        }
    }

    // parameter 当前节点（即子节点）的(index, name)
    // return 返回树的大小是否为与被删除节点个数相等
    // 找到待删除子树的根节点，深度优先遍历该子树，存储到arraylist，再有后向前依次删除
    public boolean DeleteNode(int delIndex, String delName) throws IOException {

        int treeSize = getSize();

        Node delNode = FindNode(delIndex);

        if (delNode == null)
            return false;

        Node pDelNode = delNode.getParentNode();

        System.out.println("Delete : " + delNode.getName());
        System.out.println("Parent : " + pDelNode.getName());

        findChildTreeNode(delNode);

        ArrayList<Node> dl = deleteList;// = findChildTree(num, delNode);
        dl.add(delNode);

        int deteleSize = dl.size();
        // System.out.println("deleteList size = " + dl.size());

        for (int k = dl.size() - 1; k >= 0; k--) {

            System.out.println("Child " + k + " " + dl.get(k).getName());
            // 子节点的父节点置空，子节点topic删除，父节点的childlist删除子节点,树大小减一
            dl.get(k).getTopic().remove();
            dl.get(k).setParentNode(null);
            dl.remove(k);
            sizeDecrease();
        }

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        int newTreeSize = getSize();
        return newTreeSize == treeSize - deteleSize ? true : false;
    }

    // TODO 节点没找到时的处理
    public Node FindNode(int targetNodeInde) throws IOException {
        return FindNode(targetNodeInde, nodeKami);
    }

    // parameter 查找结点index，开始查找的node（当前根节点）
    // return 查找到则返回返回node，否则为null
    // 深度遍历查找指定index的node，返回该节点
    public Node FindNode(int findIndex, Node startNode) throws IOException {

        Node targetNode = null;

        if (startNode.getIndex() == findIndex) {
            // System.out.println("find " + startNode.getIndex() + " ," +
            // startNode.getName());
            targetNode = startNode;
        } else if (!startNode.getChildList().isEmpty()) {

            for (int j = 0; j < startNode.getChildList().size()
                    && targetNode == null; ++j) {
                // System.out.println("esle " +
                // startNode.getChildList().get(j).getIndex() + " ,"
                // + startNode.getChildList().get(j).getName());
                targetNode = FindNode(findIndex, startNode.getChildList()
                        .get(j));
            }
        }

        return targetNode;
    }

    // for test
    public void init() throws IOException {

        // ParentScene-ChildScene, Root-Scene, Scene-Data, Data-Value

        // root node in r-s
        topicRoot = aBuilder.makeTopic();
        // TopicNameIF tnRoot =
        aBuilder.makeTopicName(topicRoot, "Root");

        // parent scene node in s-s
        topicParentScene = aBuilder.makeTopic();
        // TopicNameIF tnParentScene =
        aBuilder.makeTopicName(topicParentScene, "ParentScene");

        // child scene node in s-s
        topicChildScene = aBuilder.makeTopic();
        // TopicNameIF tnChildScene =
        aBuilder.makeTopicName(topicChildScene, "ChildScene");

        // scene node in s-d or r-s
        topicScene = aBuilder.makeTopic();
        // TopicNameIF tnScene =
        aBuilder.makeTopicName(topicScene, "Scene");

        // data node in s-d or d-v
        // topicData = aBuilder.makeTopic();
        // // TopicNameIF tnData = 
        // aBuilder.makeTopicName(topicData, "Data");

        // value node in d-v
        // topicValue = aBuilder.makeTopic();
        // // TopicNameIF tnValue =
        // aBuilder.makeTopicName(topicValue, "Value");

        // association type node r-s
        topicRS = aBuilder.makeTopic();
        // TopicNameIF tnRS =
        aBuilder.makeTopicName(topicRS, "Root-Scene");

        // association type node s-s
        topicSS = aBuilder.makeTopic();
        // TopicNameIF tnSS =
        aBuilder.makeTopicName(topicSS, "Scene-Scene");

        // association type node s-d
        // topicSD = aBuilder.makeTopic();
        // // TopicNameIF tnSD =
        // aBuilder.makeTopicName(topicSD, "Scene-Data");
        //
        // association type node d-v
        // topicDV = aBuilder.makeTopic();
        // // TopicNameIF tnDV =
        // aBuilder.makeTopicName(topicDV, "Data-Value");

        new XTMTopicMapWriter(XTM).write(aTopicmap);

    }

    public void query() {
        // TODO 相关联节点的查找
        // public void query(int nodeIndex, String nodeName, NodeType nt) throws
        // IOException {
        /*
        if (nodeIndex == -1) {
         // 输出R-S关联的Scene
        }
        Node queryNode = FindNode(nodeIndex);
        
        if(queryNode.getNodeType() == NodeType.Scene){
            // 输出S-D关联的Data，输出PS-CS关联的ChildScene(roletype != ParentScene)
        }else if(queryNode.getNodeType() == NodeType.Data){
            // 输出D-V关联的Value
        }else if(queryNode.getNodeType() == NodeType.Value){
            // 直接输出
        }
        */

        String keyWord = "$3";
        String ss =
        // ("item-identifier($obj, \"file:/X:/TopicMap/ontopia-5.3.0/topicmaps/hoho.xtm#id4\")?");
        // "select $Topic, $RoleType1, $AssociationType, $RoleTopic2, $RoleType2 from topic-name($Topic, $name), value($name, \""
        "select $RoleTopic2, $RoleType2 from topic-name($Topic, $name), value($name, \""
                + keyWord
                + "\"), role-player($role1, $Topic), association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $RoleTopic2), $Topic /= $RoleTopic2, type($role1, $RoleType1), type($role2, $RoleType2), type($Association, $AssociationType) ?";
        System.out.println("topic in tm: " + aTopicmap.getTopics().size());

        QueryWrapper wrapper = new QueryWrapper(aTopicmap);

        // List list = wrapper.queryForMaps(ss);
        @SuppressWarnings("unchecked")
        List<TopicIF> list = wrapper.queryForList(ss);

        for (int q = 0; q < list.size(); q++) {
            // System.out.println(list.get(q));
            System.out.println(list.get(q).getTopicNames());
        }
    }

    // for test
    public static void main(String[] args) throws IOException {

        System.out.println("========== Initial ==========\n");
        Tree t = new Tree();

        // initialize topic map with basic topics and associations
        t.init();

        System.out.println("\n========== Add ==========\n");
        // System.out.println(t.FindNode(-1).getName().toString());

        t.AddNode(-1, "Kami", 1, "@1", NodeType.Scene);
        t.AddNode(-1, "Kami", 2, "#2", NodeType.Scene);
        t.AddNode(-1, "Kami", 3, "$3", NodeType.Scene);
        t.AddNode(2, "#2", 21, "#21", NodeType.Scene);
        System.out.println(t.AddNode(2, "#2", 22, "#22", NodeType.Scene));
        t.AddNode(3, "$3", 31, "$31", NodeType.Scene);
        t.AddNode(3, "$3", 32, "$32", NodeType.Scene);
        t.AddNode(31, "$31", 311, "$311", NodeType.Scene);
        t.AddNode(-1, "Kami", 4, "%4", NodeType.Scene);
        t.AddNode(32, "$32", 321, "$321", NodeType.Scene);
        t.AddNode(32, "$32", 322, "$322", NodeType.Scene);

        System.out.println("tree size = " + t.getSize());
        System.out.println("topicmap size = " + aTopicmap.getTopics().size());

        System.out.println("\n========== Delete ==========\n");
        // t.DeleteNode(3, "$3");
        // t.DeleteNode(1, "@1");
        // System.out.println(t.DeleteNode(2, "#2"));
        // System.out.println(t.DeleteNode(3, "$3"));
        // t.DeleteNode(31, "$31");

        System.out.println("tree size = " + t.getSize());
        System.out.println("topicmap size = " + aTopicmap.getTopics().size());

        System.out.println("\n========== Query ==========\n");
        t.query();

        System.out.println("\n========== DONE==========");

        System.out.println(topicRoot);
        System.out.println(topicRoot.getTopicNames());

        System.out.println(topicRoot.getItemIdentifiers());
        System.out.println(topicRoot.getRoles());
        System.out.println(topicRoot.getOccurrences());
        System.out.println(topicRoot.getAssociations());
        System.out.println(topicRoot.getSubjectLocators());
        System.out.println(topicRoot.getSubjectIdentifiers());
        System.out.println(topicRoot.getTypes());
        System.out.println(topicRoot.getReified());

    }
}
/*
topic-name($topic,$name),
value($name, "Root"),
item-identifier($topic,$locator)? 



select $Topic, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 

from topic-name($Topic, $name), 
value($name, "$31"), 
role-player($role1, $Topic), 
association-role($Association, $role1), 
association-role($Association, $role2),
role-player($role2, $RoleTopic2), 
$Topic /= $RoleTopic2, 
type($role1, $RoleType1),
type($role2, $RoleType2), 
type($Association, $AssociationType)

order by $Topic?";
*/