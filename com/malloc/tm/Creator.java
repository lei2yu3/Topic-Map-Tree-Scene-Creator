package com.malloc.tm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

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

    public static TopicIF topicBigBro = null;

    public static TopicIF topicRoot = null;
    public static TopicIF topicScene = null;
    static TopicIF topicParentScene = null;
    static TopicIF topicChildScene = null;
    static TopicIF topicData = null;
    static TopicIF topicValue = null;

    public static TopicIF topicRS = null;
    static TopicIF topicSS = null;
    static TopicIF topicSD = null;
    static TopicIF topicDV = null;

    public enum TopicType {
        Scene("scene"), Data("data"), Value("value"), Root("root");

        private String type;

        private TopicType() {
            this(null);
        }

        private TopicType(String s) {
            this.type = s;
        }

        public String getString() {
            return this.type;
        }
    }

    public static void main(String[] args) throws IOException {

        Creator c = new Creator();

        // AddTopic(-1, 665, TopicType.Scene);
        // AddTopic(-1, 345, TopicType.Scene);
        // AddTopic(345, 77, TopicType.Scene);
        // AddTopic(345, 737, TopicType.Scene);
        // AddTopic(665, 97, TopicType.Scene);

        System.out.println("\n Creator Done");

    }

    public static boolean AddTopic(int pIndex, int addIndex, TopicType tt)
            throws IOException {

        // XTMTopicMapReader cReader = new XTMTopicMapReader(new File(XTM));
        // TopicMapIF cTopicmap = cReader.read();
        // TopicMapBuilderIF cBuilder = cTopicmap.getBuilder();

        int oldSize = cTopicmap.getTopics().size();

        String pSI = "http://" + String.valueOf(pIndex);
        TopicIF topicParent = cTopicmap
                .getTopicBySubjectIdentifier(new URILocator(pSI));

        TopicIF topicAdd = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicAdd, String.valueOf(addIndex));
        String addSI = "http://" + String.valueOf(addIndex);
        topicAdd.addSubjectIdentifier(new URILocator(addSI));

        System.out.println(topicParent);
        System.out.println(topicAdd);
        if (pIndex == -1) {
            // TODO
            // creator构造中添加的“基本”topic，与，addtopic中添加的topic和association，不在同一个TM中，
            // 要么使用全局TM，都使用从TopicMap；或者，add中使用aTopicMap，这时需要添加如下语句
            // TopicIF topicRS = aTopicMap.getTopicBySubjectIdentifier(new
            // URILocator("http://topicrs"));
            // TopicIF topicRoot = aTopicMap.getTopicBySubjectIdentifier(new
            // URILocator("http://topicroot"));
            // TopicIF topicScene = aTopicMap.getTopicBySubjectIdentifier(new
            // URILocator("http://topicscene"));

            AssociationIF aRS = cBuilder.makeAssociation(topicRS);
            cBuilder.makeAssociationRole(aRS, topicRoot, topicParent);
            cBuilder.makeAssociationRole(aRS, topicScene, topicAdd);

        } else {

            if (tt == TopicType.Scene) {

                AssociationIF aSS = cBuilder.makeAssociation(topicSS);
                cBuilder.makeAssociationRole(aSS, topicParentScene, topicParent);
                cBuilder.makeAssociationRole(aSS, topicChildScene, topicAdd);

            } else if (tt == TopicType.Data) {

                AssociationIF aSD = cBuilder.makeAssociation(topicSD);
                cBuilder.makeAssociationRole(aSD, topicScene, topicParent);
                cBuilder.makeAssociationRole(aSD, topicData, topicAdd);

            } else if (tt == TopicType.Value) {

                AssociationIF aDV = cBuilder.makeAssociation(topicDV);
                cBuilder.makeAssociationRole(aDV, topicData, topicParent);
                cBuilder.makeAssociationRole(aDV, topicValue, topicAdd);

            }
        }

        new XTMTopicMapWriter(XTM).write(cTopicmap);

        int newSize = cTopicmap.getTopics().size();

        return newSize - oldSize == 1 ? true : false;
    }

    public static boolean DeleteTopic(int delIndex, TopicType tt)
            throws IOException {

        XTMTopicMapReader dReader = new XTMTopicMapReader(new File(XTM));
        TopicMapIF dTopicmap = dReader.read();
        TopicMapBuilderIF dBuilder = dTopicmap.getBuilder();

        int oldSize = dTopicmap.getTopics().size();

        String delSI = "http://" + String.valueOf(delIndex);

        TopicIF topicDelete = dTopicmap
                .getTopicBySubjectIdentifier(new URILocator(delSI));

        ArrayList<TopicIF> delList = new ArrayList<TopicIF>();
        String query = "";
        // TODO 删除子树
        if (tt == TopicType.Scene) {

            // TODO 若是scene，删除与该节点有sd关联的主题，删除与该节点有ss关联且扮演childscene角色的主题
            // 需递归
            query = "select $d from subject-identifier($d, \"http://topic3\"), scene-scene( $p:parentscene, $c:childscene), root-scene($s:scene, $d:data)?";

        } else if (tt == TopicType.Data) {

            // TODO 若是data，删除与该节点有dv关联的主题
            query = "select $d from subject-identifier($d, \"http://topic3\"), data-value( $d:data, $v:value)?";
            
        } else if (tt == TopicType.Value) {

            // 若是value，可直接remove
            delList.add(topicDelete);
//            topicDelete.remove();

        }

        for(TopicIF t : delList){
            t.remove();
        }
        
        
        new XTMTopicMapWriter(XTM).write(dTopicmap);

        int newSize = dTopicmap.getTopics().size();
        return newSize - oldSize == 1 ? true : false;
    }

    public static ArrayList<TopicIF> ShowTopic(int showIndex, TopicType tt)
            throws MalformedURLException {

        ArrayList<TopicIF> showList = new ArrayList<TopicIF>();

        String sSI = "http://" + String.valueOf(showIndex);
        TopicIF topicShow = cTopicmap
                .getTopicBySubjectIdentifier(new URILocator(sSI));

        if (tt == TopicType.Scene) {

            // TODO 遍历！！查找topicShow所有子节点存入showList

        } else if (tt == TopicType.Data) {

            // TODO 查找topicShow所有子节点存入showList

        } else if (tt == TopicType.Value) {

            showList.add(topicShow);

        }

        return showList;
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
        topicBigBro.addSubjectIdentifier(new URILocator("http://-1"));

        //
        topicScene = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicScene, "Scene");
        topicScene.addSubjectIdentifier(new URILocator("http://topicScene"));

        topicRoot = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicRoot, "Root");
        topicRoot.addSubjectIdentifier(new URILocator("http://topicRoot"));
        /*
        topicData = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicData, "Data");
        topicData.addSubjectIdentifier(new URILocator("http://topicData"));

        topicValue = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicValue, "Value");
        topicValue.addSubjectIdentifier(new URILocator("http://topicValue"));
        */
        topicParentScene = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicParentScene, "ParentScene");
        topicParentScene.addSubjectIdentifier(new URILocator(
                "http://topicParentScene"));

        topicChildScene = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicChildScene, "ChildScene");
        topicChildScene.addSubjectIdentifier(new URILocator(
                "http://topicChildScene"));

        topicRS = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicRS, "Root-Scene");
        topicRS.addSubjectIdentifier(new URILocator("http://topicRS"));

        topicSS = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicSS, "Scene-Scene");
        topicSS.addSubjectIdentifier(new URILocator("http://topicSS"));
        /*
        topicSD = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicSD, "Scene-Data");
        topicSD.addSubjectIdentifier(new URILocator("http://topicSD"));

        topicDV = cBuilder.makeTopic();
        cBuilder.makeTopicName(topicDV, "Data-Value");
        topicDV.addSubjectIdentifier(new URILocator("http://topicDV"));
        */
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

        TopicIF topic41 = cBuilder.makeTopic();
        cBuilder.makeTopicName(topic41, "%41");
        topic41.addSubjectIdentifier(new URILocator("http://topic41"));

        TopicIF topic411 = cBuilder.makeTopic();
        cBuilder.makeTopicName(topic411, "%411");
        topic411.addSubjectIdentifier(new URILocator("http://topic411"));
        /*
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
        cBuilder.makeAssociationRole(cSS8, topicChildScene, topic322);

        AssociationIF cSS9 = cBuilder.makeAssociation(topicSS);
        cBuilder.makeAssociationRole(cSS9, topicParentScene, topic4);
        cBuilder.makeAssociationRole(cSS9, topicChildScene, topic41);

        AssociationIF cSS0 = cBuilder.makeAssociation(topicSS);
        cBuilder.makeAssociationRole(cSS0, topicParentScene, topic41);
        cBuilder.makeAssociationRole(cSS0, topicChildScene, topic411);
        */

        new XTMTopicMapWriter(XTM).write(cTopicmap);

    }
}

/*


tolog根据si查询topic
subject-identifier($topic,"http://topic321/")?




具体某个关联相关的一对topic
root-scene, root, scene 是xtm文件中关联和关联角色对应的topic id
root-scene( $R:root,$S:scene )?




查找和指定topic具有某个关联相关的topic
subject-identifier($S, "http://topic3"),
root-scene( $R:root,$S:scene )?




查找所有与指定topic有关联的topic
select $Topic, $RoleType1, $AssociationType, $Topic2, $RoleType2 from

subject-identifier($Topic, "http://topic2"),

role-player($role1, $Topic), 
association-role($Association, $role1), 
association-role($Association, $role2), 
role-player($role2, $Topic2), 
$Topic /= $Topic2, 
type($role1, $RoleType1), 
type($role2, $RoleType2), 
type($Association, $AssociationType)
?




排除与指定topic具有某个关联的topic剩下的所有topic

select $Topic, $RoleType1, $AssociationType, $Topic2, $RoleType2 from

subject-identifier($Topic, "http://topic3"),

role-player($role1, $Topic), 
association-role($Association, $role1), 
association-role($Association, $role2), 
role-player($role2, $Topic2), 
$Topic /= $Topic2, 
type($role1, $RoleType1), 
type($role2, $RoleType2), 
type($Association, $AssociationType),

not(
subject-identifier($RoleType1, "http://topicscene")
)?


 
*/
