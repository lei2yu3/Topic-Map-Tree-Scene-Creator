package com.malloc.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class tree {

    // node a = new node(11, "apple");
    // node b = new node(21, "banana");
    // node c = new node(31, "cell");
    // node d = new node(41, "devil");

    static TopicMapIF aTopicmap = null;
    static XTMTopicMapReader aReader = null;
    static TopicMapBuilderIF aBuilder = null;

    ArrayList<node> an = new ArrayList<node>();

    public tree() throws IOException {

        aReader = new XTMTopicMapReader(new File("hoho.xtm"));
        aTopicmap = aReader.read();
        aBuilder = aTopicmap.getBuilder();
        // an.add(a);
        // an.add(b);
        // an.add(c);
        // an.add(d);

        // for (node n : an) {
        // System.out.println("#" + n.index + " node == " + n.name);
        // System.out.println(n.topic.getTopicNames());
        // }
    }

    public void addNode(int ii, String ss) throws IOException {

        TopicIF tt = aBuilder.makeTopic();
        aBuilder.makeTopicName(tt, ss);

        node n = new node(ii, ss, tt);
        an.add(n);

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

    }

    public void deleteNode(int ii, ArrayList<node> a) throws IOException {

        for (node o : a) {
            if (o.index == ii) {
                o.topic.remove();
            }
        }

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

    }

    public static void main(String[] args) throws IOException {

        tree t = new tree();
        t.addNode(12, "kaka");
        t.addNode(22, "haha");
        t.addNode(32, "yoyo");

        /*        
                for(int i = 0; i < t.an.size(); i++){

                    System.out.println("$$$$" + i + " " + aTopicmap.getTopics().size());
                    t.an.get(i).topic.remove();
                }
        */
        System.out.println("$$$$1 " + aTopicmap.getTopics().size());
        // t.an.get(0).topic.remove();
        t.deleteNode(12, t.an);

        System.out.println("$$$$2 " + aTopicmap.getTopics().size());
        // t.an.get(1).topic.remove();
//        t.deleteNode(22, t.an);

        System.out.println("$$$$3 " + aTopicmap.getTopics().size());
        // t.an.get(2).topic.remove();
//        t.deleteNode(32, t.an);

        System.out.println("$$$$4 " + aTopicmap.getTopics().size());

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        System.out.println("\n tree DONE");
    }
}
