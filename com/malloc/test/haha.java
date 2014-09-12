package com.malloc.test;

import java.io.File;
import java.io.IOException;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class haha {
    TopicIF topicRoot = null;
    TopicMapIF nTopicmap = null;
    XTMTopicMapReader nReader = null;
    TopicMapBuilderIF nBuilder = null;

    public haha() throws IOException {

        nReader = new XTMTopicMapReader(new File("haha.xtm"));
        nTopicmap = nReader.read();
        nBuilder = nTopicmap.getBuilder();
        System.out.println("haha1 " + nTopicmap.getTopics().size() + " TOPICS");

        topicRoot = nBuilder.makeTopic();
        TopicNameIF tnRoot = nBuilder.makeTopicName(topicRoot, "Root");
        System.out.println("==" + topicRoot.getTopicNames());

        TopicIF topicScene = nBuilder.makeTopic();
        TopicNameIF tnScene = nBuilder.makeTopicName(topicScene, "Scene");

        new XTMTopicMapWriter("haha.xtm").write(nTopicmap);
        TopicIF topicData = nBuilder.makeTopic();
        TopicNameIF tnData = nBuilder.makeTopicName(topicData, "Data");

        System.out.println("haha2 " + nTopicmap.getTopics().size() + " TOPICS");

        topicScene.remove();
    }

    public static void main(String[] args) throws IOException {

        System.out.println("\nhaha DONE!!");
    }
}
