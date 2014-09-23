package com.malloc.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ontopia.infoset.impl.basic.URILocator;
import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class tree {

    private static final String XTM = "hoho.xtm";

    //
    private int size = 0;

    public int getSize() {
        return this.size;
    }

    public int sizeIncrease() {
        return ++size;
    }

    public int sizeDecrease() {
        return --size;
    }

    //
    static TopicMapIF aTopicmap = null;
    static XTMTopicMapReader aReader = null;
    static TopicMapBuilderIF aBuilder = null;

    public static node nodeBigBro = null;
    public TopicIF topicBigBro = null;
    public TopicIF topicRS = null;
    public TopicIF topicRoot = null;
    public TopicIF topicScene = null;

    ArrayList<node> an = new ArrayList<node>();

    public tree() throws IOException {

        new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
                .getTopicMap());

        aReader = new XTMTopicMapReader(new File("hoho.xtm"));
        aTopicmap = aReader.read();
        aBuilder = aTopicmap.getBuilder();

        topicBigBro = aBuilder.makeTopic();
        aBuilder.makeTopicName(topicBigBro, "BigBro");
        nodeBigBro = new node(-1, "BigBro", topicBigBro, null, null);
        sizeIncrease();
        topicBigBro.addSubjectIdentifier(new URILocator("http://topic0"));
        /*
        topicRS = aBuilder.makeTopic();
        TopicNameIF tnRS = aBuilder.makeTopicName(topicRS, "Root-Scene");
        topicScene = aBuilder.makeTopic();
        TopicNameIF tnScene = aBuilder.makeTopicName(topicScene, "Scene");
        topicRoot = aBuilder.makeTopic();
        TopicNameIF tnRoot = aBuilder.makeTopicName(topicRoot, "Root");
        */

        TopicIF topic1 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic1, "#1");
        node node1 = new node(1, "#1", topic1, null, null);
        node1.setParentNode(nodeBigBro);
        nodeBigBro.childList.add(node1);
        sizeIncrease();
        topic1.addSubjectIdentifier(new URILocator("http://topic1"));

        TopicIF topic2 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic2, "@2");
        node node2 = new node(2, "@2", topic2, null, null);
        node2.setParentNode(nodeBigBro);
        nodeBigBro.childList.add(node2);
        sizeIncrease();
        topic2.addSubjectIdentifier(new URILocator("http://topic2"));

        TopicIF topic3 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic3, "$3");
        node node3 = new node(3, "$3", topic3, null, null);
        node3.setParentNode(nodeBigBro);
        nodeBigBro.childList.add(node3);
        sizeIncrease();
        topic3.addSubjectIdentifier(new URILocator("http://topic3"));

        TopicIF topic21 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic21, "@21");
        node node21 = new node(21, "@21", topic21, null, null);
        node21.setParentNode(node2);
        node2.childList.add(node21);
        sizeIncrease();
        topic21.addSubjectIdentifier(new URILocator("http://topic21"));

        TopicIF topic22 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic22, "@22");
        node node22 = new node(22, "@22", topic22, null, null);
        node22.setParentNode(node2);
        node2.childList.add(node22);
        sizeIncrease();
        topic22.addSubjectIdentifier(new URILocator("http://topic22"));

        TopicIF topic31 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic31, "$31");
        node node31 = new node(31, "$31", topic31, null, null);
        node31.setParentNode(node3);
        node3.childList.add(node31);
        sizeIncrease();
        topic31.addSubjectIdentifier(new URILocator("http://topic31"));

        TopicIF topic32 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic32, "$32");
        node node32 = new node(32, "$32", topic32, null, null);
        node32.setParentNode(node3);
        node3.childList.add(node32);
        sizeIncrease();
        topic32.addSubjectIdentifier(new URILocator("http://topic32"));

        TopicIF topic321 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic321, "$321");
        node node321 = new node(321, "$321", topic321, null, null);
        node321.setParentNode(node32);
        node32.childList.add(node321);
        sizeIncrease();
        topic321.addSubjectIdentifier(new URILocator("http://topic321"));

        TopicIF topic311 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic311, "$311");
        node node311 = new node(311, "$311", topic311, null, null);
        node311.setParentNode(node31);
        node31.childList.add(node311);
        sizeIncrease();
        topic311.addSubjectIdentifier(new URILocator("http://topic311"));

        TopicIF topic4 = aBuilder.makeTopic();
        aBuilder.makeTopicName(topic4, "%4");
        node node4 = new node(4, "%4", topic4, null, null);
        node4.setParentNode(nodeBigBro);
        nodeBigBro.childList.add(node4);
        sizeIncrease();
        topic4.addSubjectIdentifier(new URILocator("http://topic4"));

        new XTMTopicMapWriter(XTM).write(aTopicmap);

    }

    // ==================================================================================
    public boolean addNode(int pIndex, String pName, int addIndex,
            String addName) throws IOException {

        int treeSize = getSize();

        node p = FindNode(pIndex, nodeBigBro);

        TopicIF tt = aBuilder.makeTopic();
        aBuilder.makeTopicName(tt, addName);

        AssociationIF aRS = aBuilder.makeAssociation(topicRS);
        aBuilder.makeAssociationRole(aRS, topicRoot, topicBigBro);
        aBuilder.makeAssociationRole(aRS, topicScene, tt);

        node add = new node(addIndex, addName, tt, p, null);
        p.childList.add(add);
        sizeIncrease();

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        int newTreeSize = getSize();
        return newTreeSize == treeSize + 1 ? true : false;

    }

    // ==================================================================================
    public node FindNode(int num) throws IOException {
        return FindNode(num, nodeBigBro);
    }

    public node FindNode(int num, node r) throws IOException {

        node newNode = null;

        if (r.index == num) {
            newNode = r;
        } else if (!r.childList.isEmpty()) {

            for (int j = 0; j < r.childList.size() && newNode == null; ++j) {
                // System.out.println("esle " + r.childList.get(j).index + " ,"
                // + r.childList.get(j).name);
                newNode = FindNode(num, r.childList.get(j));
            }
        }
        return newNode;
    }

    // ==================================================================================
    private ArrayList<node> delList = new ArrayList<node>();

    public ArrayList<node> getDelList() {
        return this.delList;
    }

    public void findChildTree(int num, node r) throws IOException {

        // delList.add(r);
        if (r.childList != null && r.childList.size() != 0) {
            for (node n : r.childList) {
                System.out.println("==n.name = " + n.name);

                delList.add(n);

                findChildTree(num, n);
            }
        }
    }

    // delete
    public boolean deleteNode(int num) throws IOException {

        node delNode = FindNode(num);

        findChildTree(num, delNode);

        ArrayList<node> dl = getDelList();
        dl.add(delNode);
        System.out.println("deleteList size = " + dl.size());
        for (int k = dl.size() - 1; k >= 0; k--) {
            System.out.println("==== " + dl.get(k).name);
            // 子节点的父节点置空，子节点topic删除，父节点的childlist删除子节点,树大小减一
            dl.get(k).topic.remove();
            dl.get(k).setParentNode(null);
            dl.remove(k);
            sizeDecrease();
        }

        new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        return delNode.childList.isEmpty();
    }

    // ==================================================================================

    public static void main(String[] args) throws IOException {

        tree t = new tree();

        // t.deleteNode(1);
        // t.deleteNode(4);
        // t.deleteNode(3);

        // System.out.println(t.addNode(-1, "BigBro", 1, "node1"));
        // System.out.println(t.addNode(-1, "BigBro", 2, "node2"));
        // System.out.println(t.addNode(-1, "BigBro", 3, "node3"));
        //
        // t.FindNode(1, nodeBigBro).topic.remove();
        // t.FindNode(1).topic.remove();
        // t.FindNode(2).topic.remove();
        //
        // System.out.println(" === ");
        // TopicIF topic = t.FindNode(1).topic;
        // System.out.println(topic.getObjectId());
        // System.out.println(topic.getTopicNames());
        //
        // System.out.println(" === ");
        // topic = t.FindNode(2).topic;
        // System.out.println(topic.getObjectId());
        // System.out.println(topic.getTopicNames());
        //
        // System.out.println(" === ");
        // topic = t.FindNode(3).topic;
        // System.out.println(topic.getObjectId());
        // System.out.println(topic.getTopicNames());
        //
        // // search
        // String keyWord = "BigBro";
        // String ss =
        // "import \"http://psi.ontopia.net/tolog/string/\" as str select $Topic, $TopicName, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 from topic-name($Topic, $name), value($name, $TopicName), str:contains($TopicName, \""
        // + keyWord +
        // "\"), role-player($role1, $Topic), association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $RoleTopic2), $Topic /= $RoleTopic2, type($role1, $RoleType1), type($role2, $RoleType2), type($Association, $AssociationType) order by $Topic?";
        // System.out.println("topic in tm: " + aTopicmap.getTopics().size());
        //
        // QueryWrapper wrapper = new QueryWrapper(aTopicmap);
        //
        // List list = wrapper.queryForMaps(ss);
        //
        // for (int q = 0; q < list.size(); q++) {
        // System.out.println(list.get(q));
        // }

        // new XTMTopicMapWriter("test.xtm").write(new InMemoryTopicMapStore()
        // .getTopicMap());

        XTMTopicMapReader r = new XTMTopicMapReader(new File("hoho.xtm"));
        TopicMapIF tm = r.read();
        TopicMapBuilderIF b = tm.getBuilder();

        // TopicMapStoreIF s = new InMemoryTopicMapStore();
        // TopicMapIF tm = s.getTopicMap();
        // TopicMapBuilderIF b = tm.getBuilder();

        // TopicIF tr = b.makeTopic();
        // TopicNameIF tnr = b.makeTopicName(tr, "Root");
        //
        // URILocator loc = new URILocator("http://123.3");
        // tr.addSubjectIdentifier(new URILocator("http://Root"));
        //
        // TopicIF tr1 = b.makeTopic();
        // b.makeTopicName(tr1, "aaa");
        // tr1.addSubjectIdentifier(new URILocator("http://aaa"));
        //
        // new XTMTopicMapWriter("test.xtm").write(tm);

        // s.commit();s.close();

        TopicIF nn = tm.getTopicBySubjectIdentifier(new URILocator(
                "http://topic321/"));
        nn.remove();
        System.out.println("topicmap size = " + tm.getTopics().size());

        new XTMTopicMapWriter("hoho.xtm").write(tm);
        // System.out.println(tr);
        // System.out.println(tr.getTopicNames());
        // System.out.println(tr.getObjectId());
        //
        // System.out.println(tr.getSubjectIdentifiers().toString());
        // System.out.println(tr.getSubjectLocators());
        // System.out.println(tr.getItemIdentifiers());
        // new XTMTopicMapWriter("hoho.xtm").write(aTopicmap);

        // for(LocatorIF i : a){
        // System.out.println(i.toString());
        // }

        QueryWrapper wrapper = new QueryWrapper(tm);
        String ss =
        // "select $b from subject-identifier($a,$b)?";
        "subject-identifier($topic,\"http://topic321/\")?";
        // "select $b from subject-locator($a,$b)?";
        // "select $b from item-identifier($a,$b)";

        @SuppressWarnings("unchecked")
        List<TopicIF> list =
        // wrapper.queryForMaps(ss);
        wrapper.queryForList(ss);

        for (int q = 0; q < list.size(); q++) {
            System.out.println(list.get(q).getObjectId());
            System.out.println(list.get(q).getSubjectIdentifiers());
        }

        System.out.println("\n ==tree DONE== \n");

        try {
            QueryProcessorIF proc = QueryUtils.getQueryProcessor(tm);

            QueryResultIF result = proc.execute(ss);
            String[] variables = result.getColumnNames();
            for (int ix = 0; ix < variables.length; ix++) {
                System.out.println(variables[ix]);
            }

            Object[] row = new Object[result.getWidth()];
            while (result.next()) {
                System.out.println(result.getClass());
                result.getValues(row);
                for (int ix = 0; ix < variables.length; ix++) {
                    System.out.println("=1== " + row[ix]);
                }
            }
            result.close();

        } catch (InvalidQueryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
