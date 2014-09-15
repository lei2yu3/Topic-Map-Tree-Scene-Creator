package com.malloc.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class node {

    public int index;
    public String name;
    public TopicIF topic;

    // public static xtm x;// = new xtm();

//    static TopicMapIF aTopicmap = null;
//    static XTMTopicMapReader aReader = null;
//    static TopicMapBuilderIF aBuilder = null;

    public node() throws IOException {
        this(0, null, null);
    }

    public node(int i, String s, TopicIF t) throws IOException {
        System.out.println("node gouzao ");

        this.index = i;
        this.name = s;
        this.topic = t;
        // x = new xtm();

//        topic = aBuilder.makeTopic();
//        aBuilder.makeTopicName(topic, this.name);

//        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);
    }

    public static void main(String[] args) throws IOException {
/*
        aReader = new XTMTopicMapReader(new File("hoho.xtm"));
        aTopicmap = aReader.read();
        aBuilder = aTopicmap.getBuilder();
        System.out.println("000:" + aTopicmap.getTopics().size());

        node n = new node(123, "nn");
        node m = new node(455, "mm");

        System.out.println("111:" + aTopicmap.getTopics().size());
        ArrayList<node> kk = new ArrayList<node>();

        kk.add(n);
        kk.add(m);

        // for(node o : kk){
        // System.out.println(o.topic.getTopicNames());
        // o.topic.remove();
        // }
        kk.get(0).topic.remove();
        System.out.println("222:" + aTopicmap.getTopics().size());
        kk.get(1).topic.remove();
        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);
        System.out.println("333:" + aTopicmap.getTopics().size());
*/
        System.out.println("\n node DONE");
    }
}
