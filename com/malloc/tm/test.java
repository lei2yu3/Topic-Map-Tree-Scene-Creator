package com.malloc.tm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.malloc.tm.Creator.TopicType;

import net.ontopia.infoset.core.LocatorIF;
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

public class test {
    private static final String XTM = "xixi.xtm";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException,
            InvalidQueryException {
        // new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
        // .getTopicMap());

        XTMTopicMapReader r = new XTMTopicMapReader(new File(XTM));
        TopicMapIF tm = r.read();
        TopicMapBuilderIF b = tm.getBuilder();

        System.out.println("size of topic = " + tm.getTopics().size());
        String ss = "select $s from root-scene($r : root, $s : scene)?";
        // "select $p from scene-scene($p : parentscene, $c : childscene)?";

        // "select $b from subject-identifier($a,$b)?";
        // "subject-identifier($topic,\"http://topic3/\")?";
        // "import \"http://psi.ontopia.net/tolog/string/\" as str select $topic from topic-name($topic, $name), value($name, $s), str:contains($s, \"2\")?";

        // "id18($p: id12, $c: id14)?";
        // "select $r, $p, $c from subject-identifier($p, \"http://topic3\"),id16( $r:id6,$p:id4 ),id18($p: id12, $c: id14)?";
        // "select $topic1 from subject-identifier($topic1, \"http://topic3/\"), role-player($role1, $topic1), association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $topic2), $role1 /= $role2, not(association($Association), type($Association, $type), topic-name($type, $string), value($string, \"Root-Scene\"))?";

        /*
        System.out.println("===== QUERY 2 =====");

        // query 2
        {
            QueryProcessorIF proc = QueryUtils.getQueryProcessor(tm);

            QueryResultIF result = proc.execute(ss);
            String[] variables = result.getColumnNames();
            for (int ix = 0; ix < variables.length; ix++) {
                System.out.println(variables[ix]);
            }

        //            Object[] row = new Object[result.getWidth()];
            TopicIF[] row = new TopicIF[result.getWidth()];
            while (result.next()) {
                result.getValues(row);
                for (int ix = 0; ix < variables.length; ix++) {
                    System.out.println("== " + row[ix].getObjectId());
                    System.out.println("== " + row[ix].getSubjectIdentifiers());
                }
            }
            result.close();
        }
        */
        // ==============================================================

        System.out.println("===== QUERY 1 =====");
        // query 1
        {

            TopicType tt = TopicType.Value;
            // 需要删除的子树的根节点的id
            int delIndex = 3;
            // 将id转换为SI
            String delSI = "http://topic" + String.valueOf(delIndex);
            // 在tm中找到给节点
            TopicIF topicDelete = tm
                    .getTopicBySubjectIdentifier(new URILocator(delSI));
            System.out.println("treeroot = " + topicDelete.getTopicNames());

            // tolog查询语句，和该节点具有某个关联的主题

            // 查询结果保存在list
            QueryWrapper wrapper = new QueryWrapper(tm);
            List<TopicIF> list = new ArrayList<TopicIF>();// wrapper.queryForList(query);
            // List<TopicIF> list = wrapper.queryForMaps(query);

            // 子树根节点入栈
            Stack<TopicIF> delStack = new Stack<TopicIF>();
            delStack.push(topicDelete);

            while (!delStack.isEmpty()) {

                System.out
                        .println("==============================================");
                String SI = delStack.peek().getSubjectIdentifiers().toString();
                System.out.println(SI);
                // int siSize = SI.length();
                SI = SI.substring(5, SI.length() - 1);
                System.out.println(SI);

                String sss = null;

                if (tt == TopicType.Scene) {

                    sss = "select $c from subject-identifier($t, \""
                            + SI
                            + "\"), scene-scene($t : parentscene, $c : childscene)?";

                    sss = "select $d from subject-identifier($t, \"" + SI
                            + "\"), scene-data($t : scene, $d : data)?";
//                    subject-identifier($t, "http://topic3")
//                    ,{ scene-scene($t : parentscene, $c : childscene)| scene-data($t : scene, $d : data)}?
                    // 查询ss和sd关联
                    list = wrapper.queryForList(sss);
                } else if (tt == TopicType.Data) {

                    sss = "select $v from subject-identifier($t, \"" + SI
                            + "\"), data-value($t : scene, $v : value)?";

                    // 查询dv关联
                    list = wrapper.queryForList(sss);
                } else if (tt == TopicType.Value) {

                    // 直接删除节点
                }

                System.out.println("childrenlist size = " + list.size());

                if (list.size() == 0) {
                    TopicIF tempTopic = delStack.pop();
                    tempTopic.remove();
                    // // TopicIF tempTopic = delStack.peek();
                    System.out.println(tempTopic.getTopicNames());
                    System.out.println("IF " + delStack.size());
                } else {
                    for (TopicIF t : list) {
                        delStack.push(t);
                    }
                    System.out.println("ELSE " + delStack.size());
                }
            }
        }

        // ==============================================================
        // new XTMTopicMapWriter(XTM).write(tm);

        System.out.println("===== Done =====");
        System.out.println("size of topic = " + tm.getTopics().size());
    }
}
