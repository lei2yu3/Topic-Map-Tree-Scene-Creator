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

	// xtm file
	private static final String XTM = "SceneCreatorSystem.xtm";

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
	TopicIF topicLeafScene = null;
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
	public NodeTree() throws IOException {

		// TODO 读取xtm中的tm 重做
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

		// create a xtm with empty topic map
		new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
				.getTopicMap());

		// read topic map from xtm
		aReader = new XTMTopicMapReader(new File(XTM));
		aTopicmap = aReader.read();
		aBuilder = aTopicmap.getBuilder();

		// root node
		topicKami = aBuilder.makeTopic();
		aBuilder.makeTopicName(topicKami, "KamiNode");
		nodeKami = new Node(-1, "Kami", topicKami, NodeType.Other, null, null);

		// write topic map to xtm
		new XTMTopicMapWriter(XTM).write(aTopicmap);

		System.out.println("nodetree2" + aTopicmap.getTopics().size()
				+ " TOPICS");

	}

	// 参数 当前节点（即父节点）(index, nName)，待加入的节点的参数(index, name, nodetype)
	// 返回 是否添加成功，父节点的getLeafList长度是否加一
	// 流程 根据pNode.getIndex()找到父节点，添加pNode.getLeafList().add(addNode);
	// 添加到树，然后根据pNode与addNode修改TM
	// add
	public boolean AddNode(int pIndex, String pName, int addIndex,
			String addName, NodeType nt) throws IOException {
		int treeSize = getSize();
		int newTreeSize = 0;

		Node pNode = FindNode(pIndex);

		// TODO 在树的根节点添加节点，将父节点设置为kamiNode
		if (pIndex == -1) {
			// pNode = kamiNode;
		}

		if (pNode.getLeafList() == null) {
			// pNode.getLeafList() = new ArrayList<Node>();
		}

		// TODO new topic addTopic
		// Node addNode = new Node(addIndex, addName, nt, pNode, null);

		// pNode.getLeafList().add(addNode);
		sizeIncrease();

		if (nt == NodeType.Scene) {
			// TODO meke association ss
		} else if (nt == NodeType.Data) {
			// TODO meke association sd

		} else if (nt == NodeType.Value) {
			// TODO meke association dv

		}

		// TODO modify tm, save to xtm
		new XTMTopicMapWriter(XTM).write(aTopicmap);

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

		// TODO 深度优先遍历以delNode为根的子树，（保存为ArrayList）依次删除
		// sizeDecrease();

		// TODO modify tm, save to xtm
		new XTMTopicMapWriter(XTM).write(aTopicmap);

		return newTreeSize == treeSize - 1 ? true : false;
	}

	// 深度遍历查找指定index的Node，返回该节点
	// find
	public Node FindNode(int targetNodeIndex) throws IOException {

		Node targetNode = null;

		// TODO 深度优先遍历树，查找指定index的节点

		return targetNode;// TODO 返回查找到的节点
	}

	// for test
	public void init() throws IOException {
		// ParentScene-LeafScene, Root-Scene, Scene-Data, Data-Value

		// parent scene node in s-s
		topicParentScene = aBuilder.makeTopic();
		TopicNameIF tnParentScene = aBuilder.makeTopicName(topicParentScene,
				"ParentScene");

		// leaf scene node in s-s
		topicLeafScene = aBuilder.makeTopic();
		TopicNameIF tnLeafScene = aBuilder.makeTopicName(topicLeafScene,
				"LeafScene");

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

		NodeTree nt = new NodeTree();

		// initialize topic map with basic topics and associations
		// nt.init();

		// add and delete
		// nt.AddNode(0, null, 0, null, NodeType.Scene);
		// nt.DeleteNode(0, null);

		System.out.println("\n nodetree DONE");
	}
}
