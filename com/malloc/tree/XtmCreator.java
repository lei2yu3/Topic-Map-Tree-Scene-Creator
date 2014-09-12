package com.malloc.tree;

import java.io.File;
import java.io.IOException;

import com.malloc.tree.Node.NodeType;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class XtmCreator {

    private static String XTM = "haha.xtm";

    public String getXTM(){
        return XTM;
    }
    
    //
    TopicMapIF cTopicmap = null;
    XTMTopicMapReader cReader = null;
    TopicMapBuilderIF cBuilder = null;

    // 
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

    XtmCreator() throws IOException {

        // create a xtm with empty topic map
        new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
                .getTopicMap());

        // read xtm
        cReader = new XTMTopicMapReader(new File(XTM));
        cTopicmap = cReader.read();
        cBuilder = cTopicmap.getBuilder();
        System.out.println("xtm1 " + cTopicmap.getTopics().size() + " TOPICS");

        // node role type
        topicRoot = cBuilder.makeTopic();
        TopicNameIF tnRoot = cBuilder.makeTopicName(topicRoot, "Root");
        /*
        topicParentScene = cBuilder.makeTopic();
        TopicNameIF tnParentScene = cBuilder.makeTopicName(topicParentScene,
                "ParentScene");

        topicLeafScene = cBuilder.makeTopic();
        TopicNameIF tnLeafScene = cBuilder.makeTopicName(topicLeafScene,
                "LeafScene");

        topicScene = cBuilder.makeTopic();
        TopicNameIF tnScene = cBuilder.makeTopicName(topicScene, "Scene");

        topicData = cBuilder.makeTopic();
        TopicNameIF tnData = cBuilder.makeTopicName(topicData, "Data");

        topicValue = cBuilder.makeTopic();
        TopicNameIF tnValue = cBuilder.makeTopicName(topicValue, "Value");

        // node association type
        topicRS = cBuilder.makeTopic();
        TopicNameIF tnRS = cBuilder.makeTopicName(topicRS, "Root-Scene");

        topicSS = cBuilder.makeTopic();
        TopicNameIF tnSS = cBuilder.makeTopicName(topicSS, "Scene-Scene");

        topicSD = cBuilder.makeTopic();
        TopicNameIF tnSD = cBuilder.makeTopicName(topicSD, "Scene-Data");

        topicDV = cBuilder.makeTopic();
        TopicNameIF tnDV = cBuilder.makeTopicName(topicDV, "Data-Value");
        */
        // write topic map to xtm
        new XTMTopicMapWriter(XTM).write(cTopicmap);

    }

    public static void main(String[] args) throws IOException {

        XtmCreator x = new XtmCreator();
        // new XTMTopicMapWriter("asda.xtm").write(new
        // InMemoryTopicMapStore().getTopicMap());

    }
}
