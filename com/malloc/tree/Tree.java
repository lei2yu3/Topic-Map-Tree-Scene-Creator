package com.malloc.tree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;






import com.malloc.test.node;
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

    // parameter ��ǰ�ڵ㣨�����ڵ㣩��(index, name)������ӽڵ��(index, name, nodetype)
    // return �������Ĵ�С�Ƿ��һ
    // ���Ҹ��ڵ㣬����ӽڵ㵽���ڵ㣬��ӹ�����TM
    public boolean AddNode(int pIndex, String pName, int addIndex,
            String addName, NodeType nt) throws IOException {

        int treeSize = getSize();

        // new a topic
        TopicIF topicAdd = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicAdd, addName);

        // �����ĸ��ڵ���ӽڵ㣬�����ڵ�ֱ������ΪkamiNode
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

            // ���Ǹ��ڵ㣬�������index���ҽ��
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
    
    // TODO delete ����������֤
    private ArrayList<Node> deleteList = new ArrayList<Node>();

    private void findChildTreeNode(Node startNode ){
//        private void findChildTreeNode(int findIndex, Node startNode ){

//        deleteList.add(startNode);
        if (startNode.getChildList() != null && startNode.getChildList().size() != 0) {
            for (Node n : startNode.getChildList()) {
            	System.out.println("n =" + n.getName());
                deleteList.add(n);
//                if(!n.getChildList().isEmpty()){
                	// TODO ERROR!!
                	findChildTreeNode(n);	
//                }
            }
        }
    }
    
    // parameter ��ǰ�ڵ㣨���ӽڵ㣩��(index, name)
    // return �������Ĵ�С�Ƿ�Ϊ�뱻ɾ���ڵ�������
    // �ҵ���ɾ�������ĸ��ڵ㣬������ȱ������������洢��arraylist�����к���ǰ����ɾ��
    public boolean DeleteNode(int delIndex, String delName) throws IOException {

    	Node delNode = FindNode(delIndex);
    	
    	findChildTreeNode(delNode);
    	
    	ArrayList<Node> dl = deleteList;// = findChildTree(num, delNode);
    	dl.add(delNode);
    	System.out.println("deleteList size = " + dl.size());
    	for(int k = dl.size() - 1; k >= 0; k--){

    		System.out.println("==== " + dl.get(k).getName());
    		// �ӽڵ�ĸ��ڵ��ÿգ��ӽڵ�topicɾ�������ڵ��childlistɾ���ӽڵ�,����С��һ
    		dl.get(k).getTopic().remove();
    		dl.get(k).setParentNode(null);
    		dl.remove(k);
    		sizeDecrease();
    	}

    	new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);
    	
    	return delNode.getChildList().isEmpty();
    }
/*	
        int treeSize = getSize();
        int deteleSize = 0;

        Node delNode = FindNode(delIndex);
        Node delParentNode = delNode.getParentNode();
                
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
                    // ɾ�����ӽڵ��б�
                    sizeDecrease();
                    delParentNode.getChildList().clear();
                    deteleSize += delParentNode.getChildList().size();
                }
            }
        }

        // modify tm, save to xtm
        new XTMTopicMapWriter(XTM).write(aTopicmap);

        int newTreeSize = getSize();
        return newTreeSize == treeSize - deteleSize ? true : false;

    }
*/
    // TODO �ڵ�û�ҵ�ʱ�Ĵ���
    public Node FindNode(int targetNodeInde) throws IOException {
        return FindNode(targetNodeInde, nodeKami);
    }
    
    // parameter ���ҽ��index����ʼ���ҵ�node����ǰ���ڵ㣩 
    // return ���ҵ��򷵻ط���node������Ϊnull
    // ��ȱ�������ָ��index��node�����ظýڵ�
    public Node FindNode(int findIndex, Node startNode) throws IOException {

        Node targetNode = null;

        if (startNode.getIndex() == findIndex) {
//            System.out.println("find " + startNode.getIndex() + " ," + startNode.getName());
            targetNode = startNode;
        } else if (!startNode.getChildList().isEmpty()) {

            for (int j = 0; j < startNode.getChildList().size() && targetNode == null; ++j) {
//                System.out.println("esle " + startNode.getChildList().get(j).getIndex() + " ,"
//                        + startNode.getChildList().get(j).getName());
                targetNode = FindNode(findIndex, startNode.getChildList().get(j));
            }
        }

        return targetNode;
    }

    // for test
    public void init() throws IOException {

        // ParentScene-ChildScene, Root-Scene, Scene-Data, Data-Value
    	
    	// root node in r-s
    	topicRoot = aBuilder.makeTopic();
        TopicNameIF tnRoot = aBuilder.makeTopicName(topicRoot, "Root");
        
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
//        topicData = aBuilder.makeTopic();
//        TopicNameIF tnData = aBuilder.makeTopicName(topicData, "Data");
//
//        // value node in d-v
//        topicValue = aBuilder.makeTopic();
//        TopicNameIF tnValue = aBuilder.makeTopicName(topicValue, "Value");

        // association type node r-s
        topicRS = aBuilder.makeTopic();
        TopicNameIF tnRS = aBuilder.makeTopicName(topicRS, "Root-Scene");

        // association type node s-s
        topicSS = aBuilder.makeTopic();
        TopicNameIF tnSS = aBuilder.makeTopicName(topicSS, "Scene-Scene");

        // association type node s-d
//        topicSD = aBuilder.makeTopic();
//        TopicNameIF tnSD = aBuilder.makeTopicName(topicSD, "Scene-Data");
//
//        // association type node d-v
//        topicDV = aBuilder.makeTopic();
//        TopicNameIF tnDV = aBuilder.makeTopicName(topicDV, "Data-Value");

        new XTMTopicMapWriter(XTM).write(aTopicmap);

    }

    // for test
    public static void main(String[] args) throws IOException {

        Tree t = new Tree();
        t.init();
        
        System.out.println(" main\n");
        System.out.println(t.FindNode(-1).getName().toString());

        t.AddNode(-1, "Kami", 1, "@1", NodeType.Scene);
        t.AddNode(-1, "Kami", 2, "#2", NodeType.Scene);
        t.AddNode(-1, "Kami", 3, "$3", NodeType.Scene);
        t.AddNode(2, "#2", 21, "#21", NodeType.Scene);
        t.AddNode(2, "#2", 22, "#22", NodeType.Scene);
        t.AddNode(3, "$3", 31, "$31", NodeType.Scene);
        t.AddNode(3, "$3", 32, "$32", NodeType.Scene);
        t.AddNode(31, "$31", 311, "$311", NodeType.Scene);
        t.AddNode(-1, "Kami", 4, "%4", NodeType.Scene);
        t.AddNode(32, "$32", 321, "$321", NodeType.Scene);
        t.AddNode(32, "$32", 322, "$322", NodeType.Scene);

        t.DeleteNode(3, "$3");
        t.DeleteNode(1, "@1");
//        t.DeleteNode(2, "#2");
//        t.DeleteNode(31, "$31");
        
        System.out.println(t.getSize());
        System.out.println(aTopicmap.getTopics().size());
        
        
        // initialize topic map with basic topics and associations
        // nt.init();

        // add and delete
        // nt.AddNode(0, null, 0, null, NodeType.Scene);
        // nt.DeleteNode(0, null);

        System.out.println("\n Tree DONE");

    }
}
