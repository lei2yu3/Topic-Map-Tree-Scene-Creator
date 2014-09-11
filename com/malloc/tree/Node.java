package com.malloc.tree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Node {
    public enum NodeType {
        // Scene(1, "scene"), Data(2, "data"), Value(3, "value");
        Scene("scene"), Data("data"), Value("value"), Other("other");

        // private int id;
        private String type;

        private NodeType() {
            // this(0, null);
            this(null);
        }

        // private NodeType(int i, String s) {
        private NodeType(String s) {
            // this.id = i;
            this.type = s;
        }

        // public int getIndex() {
        // return this.id;
        // }

        public String getString() {
            return this.type;
        }
    }

    //
    private int index;
    private String name;
    private TopicIF topic;
    private NodeType nodetype;

    private Node parentNode;
    private ArrayList<Node> leafList;

    public Node() throws IOException {
        this(0, null, null, null, null);
    }

    public Node(int i, String s, NodeType n, Node pn, ArrayList<Node> ll)
            throws IOException {

        // TODO жизі
        // read topic map from xtm
        TopicMapStoreIF nStore = new InMemoryTopicMapStore();
        TopicMapIF nTopicmap = nStore.getTopicMap();

        TopicMapImporterIF nReader = new XTMTopicMapReader(new File("TREE.xtm"));
        nReader.importInto(nTopicmap);

        TopicMapBuilderIF nBuilder = nTopicmap.getBuilder();
        // System.out.println("nnnn " + nTopicmap.getTopics().size() +
        // " TOPICS");

        //
        this.index = i;
        this.name = s;

        this.topic = nBuilder.makeTopic();
        nBuilder.makeTopicName(this.topic, s);

        this.nodetype = n;
        this.parentNode = pn;
        this.leafList = ll;

        // write topic map to xtm
        new XTMTopicMapWriter("TREE.xtm").write(nTopicmap);

        // System.out.println(this.index);
        // System.out.println(this.name);
        // System.out.println(this.nodetype);
        // System.out.println(this.topic.getTopicNames());
        // System.out.println(this.topic.getObjectId());
        // System.out.println(this.topic.getItemIdentifiers());
        // System.out.println("=================");

        nStore.commit();
        nStore.close();
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
        return this.nodetype;
    }

    public Node getParentNode() {
        return this.parentNode;
    }

    public ArrayList<Node> getLeafList() {
        return this.leafList;
    }

    // for test
    public static void main(String[] args) throws IOException {
        /*
                // Node r = new Node(-1, "root", NodeType.Other, null, null);
                // Node q = new Scene(1, "haha", null);
                // Node w = new Value(2, "asas", null);

                ArrayList<Node> al = new ArrayList<Node>();
                // al.add(q);
                // al.add(w);
                // al.add(r);

                for (Node s : al) {
                    System.out.println(s.getIndex());
                    System.out.println(s.getName());
                    System.out.println(s.getNodeType());
                    System.out.println(s.getTopic());
                    System.out.println(s.getParentNode());
                    System.out.println(s.getLeafList());
                    System.out.println(s.toString());
                    System.out.println("=================");
                }
        */
        NodeTree xx = new NodeTree();
        // xx.fun(123, "asd");
        // xx.fun(332, "ggd");

        System.out.println("\nDONE!!");
    }

}

// scene node
class Scene extends Node {

    Scene() throws IOException {
        super();
    }

    Scene(int i, String s, Node pNode) throws IOException {
        super(i, s, NodeType.Scene, pNode, null);
    }
}

// data node
class Data extends Node {

    Data() throws IOException {
        super();
    }

    Data(int i, String s, Node pNode) throws IOException {
        super(i, s, NodeType.Data, pNode, null);
    }
}

// value node
class Value extends Node {

    Value() throws IOException {
        super();
    }

    Value(int i, String s, Node pNode) throws IOException {
        super(i, s, NodeType.Value, pNode, null);
    }
}
