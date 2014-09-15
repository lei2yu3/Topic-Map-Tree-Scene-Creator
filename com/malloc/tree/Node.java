package com.malloc.tree;

//import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
//import net.ontopia.topicmaps.core.TopicMapBuilderIF;
//import net.ontopia.topicmaps.core.TopicMapIF;
//import net.ontopia.topicmaps.core.TopicMapImporterIF;
//import net.ontopia.topicmaps.core.TopicMapStoreIF;
//import net.ontopia.topicmaps.core.TopicNameIF;
//import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
//import net.ontopia.topicmaps.xml.XTMTopicMapReader;
//import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Node {

	// node type
	public enum NodeType {
		Scene("scene"), Data("data"), Value("value"), Other("other");

		private String type;

		private NodeType() {
			this(null);
		}

		private NodeType(String s) {
			this.type = s;
		}

		public String getString() {
			return this.type;
		}
	}

	//
	private int index;
	private String name;
	private TopicIF topic;
	private NodeType nodeType;
	private Node parentNode;
	private ArrayList<Node> leafList;

	public Node() throws IOException {
		this(0, null, null, null, null, null);
	}

	public Node(int nIndex, String nName, TopicIF nTopic, NodeType nNodeType,
			Node nParentNode, ArrayList<Node> nLeafList) throws IOException {

		//
		this.index = nIndex;
		this.name = nName;
		this.topic = nTopic;
		this.nodeType = nNodeType;
		this.parentNode = nParentNode;
		this.leafList = nLeafList;
	}

	public int getIndex() {
		return this.index;
	}

	public String getName() {
		return this.name;
	}

	public TopicIF getTopic() {
		return this.topic;
	}

	public NodeType getNodeType() {
		return this.nodeType;
	}

	public Node getParentNode() {
		return this.parentNode;
	}

	public ArrayList<Node> getLeafList() {
		return this.leafList;
	}

	// for test
	public static void main(String[] args) throws IOException {

		System.out.println("\n Node DONE!!");
	}
}

// scene node
class Scene extends Node {

	Scene() throws IOException {
		super();
	}

	Scene(int sIndex, String sName, Node sParentNode) throws IOException {
		super(sIndex, sName, null, NodeType.Value, sParentNode, null);
	}
}

// data node
class Data extends Node {

	Data() throws IOException {
		super();
	}

	Data(int dIndex, String dName, Node dParentNode) throws IOException {
		super(dIndex, dName, null, NodeType.Value, dParentNode, null);
	}
}

// value node
class Value extends Node {

	Value() throws IOException {
		super();
	}

	Value(int vIndex, String vName, Node vParentNode) throws IOException {
		super(vIndex, vName, null, NodeType.Value, vParentNode, null);
	}
}
