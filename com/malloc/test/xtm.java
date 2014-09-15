package com.malloc.test;

import java.io.File;
import java.io.IOException;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class xtm {

    TopicMapIF aTopicmap = null;
    XTMTopicMapReader aReader = null;
    TopicMapBuilderIF aBuilder = null;

    TopicIF association = null;
    TopicIF scene = null;
    TopicIF data = null;
    TopicIF root = null;

    public xtm() throws IOException {
        System.out.println("xtm gouzao ");
        aReader = new XTMTopicMapReader(new File("hoho.xtm"));
        aTopicmap = aReader.read();
        aBuilder = aTopicmap.getBuilder();


    }

    public void init() throws IOException {

        association = aBuilder.makeTopic();
        aBuilder.makeTopicName(association, "S=D");

        scene = aBuilder.makeTopic();
        aBuilder.makeTopicName(scene, "scene");

        data = aBuilder.makeTopic();
        aBuilder.makeTopicName(data, "data");

        root = aBuilder.makeTopic();
        aBuilder.makeTopicName(root, "root");

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);
    }

    public static void main(String[] args) throws IOException {


        xtm x = new xtm();
        x.init();
        
        System.out.println("\n xtm DONE");
    }
}
