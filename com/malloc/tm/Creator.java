package com.malloc.tm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import net.ontopia.infoset.impl.basic.URILocator;
import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Creator {
    private static final String XTM = "hoho.xtm";

    //
    static TopicMapIF cTopicmap = null;
    static XTMTopicMapReader cReader = null;
    static TopicMapBuilderIF cBuilder = null;

    public TopicIF topicBigBro = null;

    public static TopicIF topicRS = null;
    public static TopicIF topicRoot = null;
    public static TopicIF topicScene = null;

    TopicIF topicSS = null;
    TopicIF topicParentScene = null;
    TopicIF topicChildScene = null;

    public static void main(String[] args) throws IOException {

        Creator c = new Creator();
        
        int a = AddTopic("topic0", "123");
        System.out.println(a);

        AddTopic("topic0", "aaa");AddTopic("123", "222");
        
        System.out.println("\n Creator Done");

    }

    public static int AddTopic(String pIndex, String addIndex /*, TopicType addTT*/)
            throws IOException {

//        String.valueOf(int);
        
        String pSI = "http://" + pIndex;
        String addSI = "http://" + addIndex;

        // 根据SI查找父节点，根据类型创建新节点，添加关联
        TopicIF topicParent = cTopicmap
                .getTopicBySubjectIdentifier(new URILocator(pSI));

        TopicIF topicAdd = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicAdd, addIndex);
        topicAdd.addSubjectIdentifier(new URILocator(addSI));

        AssociationIF aRS1 = cBuilder.makeAssociation(topicRS);
        cBuilder.makeAssociationRole(aRS1, topicRoot, topicParent);
        cBuilder.makeAssociationRole(aRS1, topicScene, topicAdd);

        new XTMTopicMapWriter(XTM).write(cTopicmap);
        
        return cTopicmap.getTopics().size();
    }

    public Creator() throws IOException {

        new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
                .getTopicMap());

        cReader = new XTMTopicMapReader(new File(XTM));
        cTopicmap = cReader.read();
        cBuilder = cTopicmap.getBuilder();

        //
        topicBigBro = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicBigBro, "BigBro");
        topicBigBro.addSubjectIdentifier(new URILocator("http://topic0"));

        //
        topicRS = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicRS, "Root-Scene");
        topicRS.addSubjectIdentifier(new URILocator("http://topicRS"));

        topicScene = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicScene, "Scene");
        topicScene.addSubjectIdentifier(new URILocator("http://topicScene"));

        topicRoot = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicRoot, "Root");
        topicRoot.addSubjectIdentifier(new URILocator("http://topicRoot"));
/*
        topicSS = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicSS, "Scene-Scene");
        topicSS.addSubjectIdentifier(new URILocator("http://topicSS"));

        topicParentScene = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicParentScene, "ParentScene");
        topicParentScene.addSubjectIdentifier(new URILocator(
                "http://topicParentScene"));

        topicChildScene = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicChildScene, "ChildScene");
        topicChildScene.addSubjectIdentifier(new URILocator(
                "http://topicChildScene"));
        
                //
                TopicIF topic1 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic1, "#1");
                topic1.addSubjectIdentifier(new URILocator("http://topic1"));

                TopicIF topic2 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic2, "@2");
                topic2.addSubjectIdentifier(new URILocator("http://topic2"));

                TopicIF topic3 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic3, "$3");
                topic3.addSubjectIdentifier(new URILocator("http://topic3"));

                TopicIF topic21 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic21, "@21");
                topic21.addSubjectIdentifier(new URILocator("http://topic21"));

                TopicIF topic22 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic22, "@22");
                topic22.addSubjectIdentifier(new URILocator("http://topic22"));

                TopicIF topic31 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic31, "$31");
                topic31.addSubjectIdentifier(new URILocator("http://topic31"));

                TopicIF topic32 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic32, "$32");
                topic32.addSubjectIdentifier(new URILocator("http://topic32"));

                TopicIF topic321 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic321, "$321");
                topic321.addSubjectIdentifier(new URILocator("http://topic321"));

                TopicIF topic311 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic311, "$311");
                topic311.addSubjectIdentifier(new URILocator("http://topic311"));

                TopicIF topic4 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic4, "%4");
                topic4.addSubjectIdentifier(new URILocator("http://topic4"));

                TopicIF topic322 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic322, "$322");
                topic322.addSubjectIdentifier(new URILocator("http://topic322"));

                TopicIF topic312 = cBuilder.makeTopic();
                cBuilder.makeTopicName(topic312, "$312");
                topic312.addSubjectIdentifier(new URILocator("http://topic312"));

                //
                AssociationIF aRS1 = cBuilder.makeAssociation(topicRS);
                cBuilder.makeAssociationRole(aRS1, topicRoot, topicBigBro);
                cBuilder.makeAssociationRole(aRS1, topicScene, topic1);

                AssociationIF aRS2 = cBuilder.makeAssociation(topicRS);
                cBuilder.makeAssociationRole(aRS2, topicRoot, topicBigBro);
                cBuilder.makeAssociationRole(aRS2, topicScene, topic2);

                AssociationIF aRS3 = cBuilder.makeAssociation(topicRS);
                cBuilder.makeAssociationRole(aRS3, topicRoot, topicBigBro);
                cBuilder.makeAssociationRole(aRS3, topicScene, topic3);

                AssociationIF aRS4 = cBuilder.makeAssociation(topicRS);
                cBuilder.makeAssociationRole(aRS4, topicRoot, topicBigBro);
                cBuilder.makeAssociationRole(aRS4, topicScene, topic4);

                //
                AssociationIF cSS1 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS1, topicParentScene, topic2);
                cBuilder.makeAssociationRole(cSS1, topicChildScene, topic21);

                AssociationIF cSS2 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS2, topicParentScene, topic2);
                cBuilder.makeAssociationRole(cSS2, topicChildScene, topic22);

                AssociationIF cSS3 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS3, topicParentScene, topic3);
                cBuilder.makeAssociationRole(cSS3, topicChildScene, topic31);

                AssociationIF cSS4 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS4, topicParentScene, topic3);
                cBuilder.makeAssociationRole(cSS4, topicChildScene, topic32);

                AssociationIF cSS5 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS5, topicParentScene, topic31);
                cBuilder.makeAssociationRole(cSS5, topicChildScene, topic311);

                AssociationIF cSS6 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS6, topicParentScene, topic31);
                cBuilder.makeAssociationRole(cSS6, topicChildScene, topic312);

                AssociationIF cSS7 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS7, topicParentScene, topic32);
                cBuilder.makeAssociationRole(cSS7, topicChildScene, topic321);

                AssociationIF cSS8 = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(cSS8, topicParentScene, topic32);
                cBuilder.makeAssociationRole(cSS8, topicChildScene, topic312);
        */
        new XTMTopicMapWriter(XTM).write(cTopicmap);

    }
}
