package com.malloc.tm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import net.ontopia.infoset.impl.basic.URILocator;
import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Creator {
    private static final String XTM = "xixi.xtm";

    
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

        XTMTopicMapReader r = new XTMTopicMapReader(new File(XTM));
        TopicMapIF tm = r.read();
        TopicMapBuilderIF b = tm.getBuilder();
        System.out.println("size of topic = " + tm.getTopics().size());

        new XTMTopicMapWriter(XTM).write(tm);
        // Creator c = new Creator();

        /*
         AddTopic(-1, 1, TopicType.Scene);
         AddTopic(-1, 2, TopicType.Scene);
         AddTopic(-1, 3, TopicType.Scene);
         AddTopic(-1, 4, TopicType.Scene);

         AddTopic(1, 11, TopicType.Scene);
         AddTopic(1, 12, TopicType.Scene);
         AddTopic(11, 111, TopicType.Scene);
         AddTopic(111, 1111, TopicType.Scene);
         AddTopic(1111, 11111, TopicType.Scene);
         AddTopic(11111, 111111, TopicType.Scene);
         AddTopic(111111, 1111111, TopicType.Data);
         AddTopic(1111111, 11111111, TopicType.Value);
         AddTopic(12, 121, TopicType.Data);
         AddTopic(121, 1211, TopicType.Value);

         AddTopic(2, 21, TopicType.Data);
         AddTopic(2, 22, TopicType.Data);
         AddTopic(2, 23, TopicType.Scene);
         AddTopic(21, 211, TopicType.Value);
         AddTopic(21, 212, TopicType.Value);
         AddTopic(21, 213, TopicType.Value);
         AddTopic(21, 214, TopicType.Value);
         AddTopic(23, 231, TopicType.Scene);
         AddTopic(231, 2311, TopicType.Data);
         AddTopic(2311, 23111, TopicType.Value);
         AddTopic(23, 232, TopicType.Scene);
         AddTopic(232, 2321, TopicType.Data);
         AddTopic(2321, 23211, TopicType.Value);
         AddTopic(23, 233, TopicType.Data);
         AddTopic(23, 234, TopicType.Data);
         AddTopic(23, 235, TopicType.Data);
         AddTopic(235, 2351, TopicType.Value);
         AddTopic(235, 2352, TopicType.Value);
         AddTopic(235, 2353, TopicType.Value);
         AddTopic(235, 2354, TopicType.Value);

         AddTopic(3, 31, TopicType.Data);
         AddTopic(31, 311, TopicType.Value);
         AddTopic(31, 312, TopicType.Value);
         AddTopic(31, 313, TopicType.Value);
        */

//        DeleteTopic(121, TopicType.Scene);
        DeleteTopic(3);
        
        /*
            // test for List<HashMap <String, TopicIF> > list = wrapper.queryForMaps(query);


                XTMTopicMapReader r = new XTMTopicMapReader(new File("xixi.xtm"));
                TopicMapIF tm = r.read();
                TopicMapBuilderIF b = tm.getBuilder();
                
                String query = // "select $r, $c from " +
                "subject-identifier($t, \"http://topic31\")," + 
        //         "{ root-scene($r : root, $t : scene) | scene-scene($t : parentscene, $c : childscene)}?";
        //         "root-scene($r : root, $t : scene), scene-scene($t : parentscene, $c : childscene)?";
                // "root-scene($r : root, $t : scene)?";
                 "scene-scene($t : parentscene, $c : childscene)?";

                ArrayList<TopicIF> l = new ArrayList<TopicIF>(); 
                
                QueryWrapper wrapper = new QueryWrapper(tm);
                List<HashMap <String, TopicIF> > list = wrapper.queryForMaps(query);
                System.out.println("size of topic = " + tm.getTopics().size());
                
        //        System.out.println(list.get(0).get("r") instanceof TopicIF);
                for (HashMap<String, TopicIF> t : list) {
                    if (t.get("r") != null) {
                        System.out.println(t.get("r"));
                        System.out.println(t.get("r") instanceof TopicIF);
                        l.add(t.get("r"));
                    }
                    if (t.get("c") != null) {
                        System.out.println(t.get("c"));
                        System.out.println(t.get("c") instanceof TopicIF);
                        l.add(t.get("c"));
                    }

        //            System.out.println("==" + t);
        //            System.out.println("==" + t.getTopicMap());
        //            System.out.println("==" + t.getSubjectIdentifiers());
        //            System.out.println("==" + t.getObjectId());
                }

                for (TopicIF x : l) {
                    System.out.println(x.getTopicNames());
        //            x.remove();
                }

                new XTMTopicMapWriter("xixi.xtm").write(tm);
                System.out.println("size of topic = " + tm.getTopics().size());
        */
        System.out.println("\n Creator Done");

    }

    public static boolean AddTopic(int pIndex, int addIndex, TopicType tt)
            throws IOException {

         XTMTopicMapReader aReader = new XTMTopicMapReader(new File(XTM));
         TopicMapIF aTopicmap = aReader.read();
         TopicMapBuilderIF aBuilder = aTopicmap.getBuilder();

        int oldSize = aTopicmap.getTopics().size();

        String pSI = "http://" + String.valueOf(pIndex);
        TopicIF topicParent = aTopicmap
                .getTopicBySubjectIdentifier(new URILocator(pSI));

        TopicIF topicAdd = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicAdd, String.valueOf(addIndex));
        String addSI = "http://" + String.valueOf(addIndex);
        topicAdd.addSubjectIdentifier(new URILocator(addSI));

        System.out.println(topicParent);
        System.out.println(topicAdd);
        if (pIndex == -1) {

            // 不要使用全局TM，add时候读取XTM，根据SI获取基本topic
            TopicIF topicKami = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topickami"));
            TopicIF topicRoot = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicroot"));
            TopicIF topicScene = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicscene"));
            TopicIF topicRS = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicrs"));

            AssociationIF aRS = aBuilder.makeAssociation(topicRS);
            aBuilder.makeAssociationRole(aRS, topicRoot, topicKami);
            aBuilder.makeAssociationRole(aRS, topicScene, topicAdd);

        } else {

            if (tt == TopicType.Scene) {

                TopicIF topicParentScene = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicparentscene"));
                TopicIF topicChildScene = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicchildscene"));
                TopicIF topicSS = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicss"));

                AssociationIF aSS = aBuilder.makeAssociation(topicSS);
                aBuilder.makeAssociationRole(aSS, topicParentScene, topicParent);
                aBuilder.makeAssociationRole(aSS, topicChildScene, topicAdd);

            } else if (tt == TopicType.Data) {

                TopicIF topicScene = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicscene"));
                TopicIF topicData = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicdata"));
                TopicIF topicSD = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicsd"));

                AssociationIF aSD = aBuilder.makeAssociation(topicSD);
                aBuilder.makeAssociationRole(aSD, topicScene, topicParent);
                aBuilder.makeAssociationRole(aSD, topicData, topicAdd);

            } else if (tt == TopicType.Value) {

                TopicIF topicData = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicdata"));
                TopicIF topicValue = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicvalue"));
                TopicIF topicDV = aTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicdv"));

                AssociationIF aDV = aBuilder.makeAssociation(topicDV);
                aBuilder.makeAssociationRole(aDV, topicData, topicParent);
                aBuilder.makeAssociationRole(aDV, topicValue, topicAdd);

            }
        }

        new XTMTopicMapWriter(XTM).write(aTopicmap);

        int newSize = aTopicmap.getTopics().size();

        return newSize - oldSize == 1 ? true : false;
    }

    @SuppressWarnings("unchecked")
    public static boolean DeleteTopic(int delIndex)
            throws IOException {

        XTMTopicMapReader dReader = new XTMTopicMapReader(new File(XTM));
        TopicMapIF dTopicmap = dReader.read();
//        TopicMapBuilderIF dBuilder = dTopicmap.getBuilder();

        int oldSize = dTopicmap.getTopics().size();
        int delSize = 0;
        TopicType tt = null;

        // 将id转换为SI
        String delSI = "http://" + String.valueOf(delIndex);
        // 在tm中找到该节点
        TopicIF topicDelete = dTopicmap.getTopicBySubjectIdentifier(new URILocator(delSI));
        System.out.println("treeroot = " + topicDelete.getTopicNames());

        // 查询结果保存在list
        QueryWrapper wrapper = new QueryWrapper(dTopicmap);
        List<TopicIF> delList = new ArrayList<TopicIF>();

        // 子树根节点入栈
        Stack<TopicIF> delStack = new Stack<TopicIF>();
        delStack.push(topicDelete);

        while (!delStack.isEmpty()) {

            System.out.println("==============================================");
            String SI = delStack.peek().getSubjectIdentifiers().toString();
            System.out.println(SI);
            SI = SI.substring(5, SI.length() - 1);
            System.out.println(SI);

            String sss = null;

            // 判断栈顶元素的类型 
            if (delStack.peek().getRolesByType(dTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicparentscene"))).size() != 0
                || delStack.peek().getRolesByType(dTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicchildscene"))).size() != 0
                || delStack.peek().getRolesByType(dTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicscene"))).size() != 0) {
                System.out.println("type = Scene");
                tt = TopicType.Scene;

            } else if (delStack.peek().getRolesByType(dTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicdata"))).size() != 0) {
                System.out.println("type = Data");
                tt = TopicType.Data;

            } else if (delStack.peek().getRolesByType(dTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicvalue"))).size() != 0) {
                System.out.println("type = Value");
                tt = TopicType.Value;

            } else if (delStack.peek().getRolesByType(dTopicmap.getTopicBySubjectIdentifier(new URILocator("http://topicroot"))).size() != 0) {
                System.out.println("type = Root");
                tt = TopicType.Root;
            } else {
                System.out.println("type = else");
            }

            // 根据元素类型进行查找
            // tolog查询语句，和该节点具有某个关联的主题
            if (tt == TopicType.Scene) {

                sss = "select $c, $d from subject-identifier($t, \""
                        + SI
                        + "\"), { scene-scene($t : parentscene, $c : childscene) | scene-data($t : scene, $d : data)}?";

                // 查询ss和sd关联
                List<HashMap<String, TopicIF>> map = wrapper.queryForMaps(sss);
                ArrayList<TopicIF> n = new ArrayList<TopicIF>();

                for (HashMap<String, TopicIF> t : map) {
                    System.out.println("t.d == " + t.get("d") + " t.c == "
                            + t.get("c"));

                    if (t.get("d") != null) {
                        System.out.println(t.get("d"));
                        System.out.println(t.get("d") instanceof TopicIF);
                        n.add(t.get("d"));
                    }

                    if (t.get("c") != null) {
                        System.out.println(t.get("c"));
                        System.out.println(t.get("c") instanceof TopicIF);
                        n.add(t.get("c"));
                    }
                }

                delList = n;
            } else if (tt == TopicType.Data) {

                sss = "select $v from subject-identifier($t, \"" + SI
                        + "\"), data-value($t :data, $v : value)?";

                // 查询dv关联
                // List<TopicIF> list
                delList = wrapper.queryForList(sss);
            } else if (tt == TopicType.Value) {

                // 无关联，delList置空
                delList = new ArrayList<TopicIF>();
            }

            System.out.println("childrenlist size = " + delList.size());

            if (delList.size() == 0) {
                TopicIF tempTopic = delStack.pop();
                tempTopic.remove();
                ++delSize;
                // // TopicIF tempTopic = delStack.peek();
                System.out.println(tempTopic.getTopicNames());
                System.out.println("IF " + delStack.size());
            } else {
                for (TopicIF t : delList) {
                    delStack.push(t);
                }
                System.out.println("ELSE " + delStack.size());
            }
        }

        new XTMTopicMapWriter(XTM).write(dTopicmap);

        int newSize = dTopicmap.getTopics().size();
        return newSize - oldSize == delSize ? true : false;
    }

    @SuppressWarnings("unchecked")
    public static List<TopicIF> ShowTopic(int showIndex, TopicType tt)
            throws IOException {

        XTMTopicMapReader sReader = new XTMTopicMapReader(new File(XTM));
        TopicMapIF sTopicmap = sReader.read();
//        TopicMapBuilderIF sBuilder = sTopicmap.getBuilder();

        List<TopicIF> showList = new ArrayList<TopicIF>();

        String SI = "http://topic" + String.valueOf(showIndex);
//        TopicIF topicShow = cTopicmap.getTopicBySubjectIdentifier(new URILocator(SI));

        QueryWrapper wrapper = new QueryWrapper(sTopicmap);
        String sss = null;
        
        if (tt == TopicType.Scene) {

            sss = "select $c, $d from subject-identifier($t, \""
                    + SI
                    + "\"), { scene-scene($t : parentscene, $c : childscene) | scene-data($t : scene, $d : data)}?";

            // 查询ss和sd关联
            List<HashMap<String, TopicIF>> map = wrapper.queryForMaps(sss);
            ArrayList<TopicIF> n = new ArrayList<TopicIF>();

            for (HashMap<String, TopicIF> t : map) {
              System.out.println("t.d == " + t.get("d") + " t.c == " + t.get("c"));
                
                if (t.get("d") != null) {
                    System.out.println(t.get("d"));
                    System.out.println(t.get("d") instanceof TopicIF);
                    n.add(t.get("d"));
                }
                
                if (t.get("c") != null) {
                    System.out.println(t.get("c"));
                    System.out.println(t.get("c") instanceof TopicIF);
                    n.add(t.get("c"));
                }
            }
            
            showList = n;

        } else if (tt == TopicType.Data) {

            sss = "select $v from subject-identifier($t, \"" + SI
                    + "\"), data-value($t : scene, $v : value)?";

            // 查询dv关联
            showList = wrapper.queryForList(sss);

        } else if (tt == TopicType.Value) {

//            showList.add(topicShow);
            showList = new ArrayList<TopicIF>(); 

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
        topicBigBro.addSubjectIdentifier(new URILocator("http://topicKami"));

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
