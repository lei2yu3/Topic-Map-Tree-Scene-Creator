package com.malloc.test;

import java.io.File;
import java.io.IOException;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class hoho {

    public static void main(String[] args) throws IOException {

        haha h = new haha();

        System.out.println("=================================");

        // XTMTopicMapReader aReader = new XTMTopicMapReader(new
        // File("haha.xtm"));
        // h.nTopicmap = h.nReader.read();
        TopicMapBuilderIF aBuilder = h.nTopicmap.getBuilder();
        System.out.println("hoho1 " + h.nTopicmap.getTopics().size()
                + " TOPICS");

        TopicIF topicValue = aBuilder.makeTopic();
        TopicNameIF tnValue = aBuilder.makeTopicName(topicValue, "Value");
        //
        // new XTMTopicMapWriter("haha.xtm").write(aTopicmap);
        System.out.println("hoho2 " + h.nTopicmap.getTopics().size()
                + " TOPICS");

        System.out.println("==" + h.topicRoot.getTopicNames());
        h.topicRoot.remove();
        new XTMTopicMapWriter("haha.xtm").write(h.nTopicmap);
        System.out.println("==" + h.topicRoot.getTopicNames());
        System.out.println("hoho3 " + h.nTopicmap.getTopics().size()
                + " TOPICS");

        topicValue.remove();
        System.out.println("hoho4 " + h.nTopicmap.getTopics().size()
                + " TOPICS");

        System.out.println("\nhoho DONE!!");
    }
}
