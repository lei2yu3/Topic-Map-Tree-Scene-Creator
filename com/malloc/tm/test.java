package com.malloc.tm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
//        TopicMapBuilderIF b = tm.getBuilder();

        System.out.println("size of topic = " + tm.getTopics().size());
        
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

        System.out.println("===== DELETE TOPIC =====");
        // query 1
        {
            TopicType tt = TopicType.Value;
            // 需要删除的子树的根节点的id
            int delIndex = 3;
			
			
        // XTMTopicMapReader dReader = new XTMTopicMapReader(new File(XTM));
        // TopicMapIF dTopicMap = dReader.read();
        // TopicMapBuilderIF dBuilder = dTopicMap.getBuilder();
			
            // 将id转换为SI
            String delSI = "http://topic" + String.valueOf(delIndex);
            // 在tm中找到该节点
            TopicIF topicDelete = tm
                    .getTopicBySubjectIdentifier(new URILocator(delSI));
            System.out.println("treeroot = " + topicDelete.getTopicNames());
            

            // 查询结果保存在list
            QueryWrapper wrapper = new QueryWrapper(tm);
          List<TopicIF> delList = new ArrayList<TopicIF>();
//            List<TopicIF> list = new ArrayList<TopicIF>();
            // List<HashMap <String, TopicIF> > list = wrapper.queryForMaps(query);

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
                
                // 判断栈顶元素的类型
                if(topicDelete.getRolesByType(tm.getTopicBySubjectIdentifier(new URILocator("http://topicchildscene"))).size() != 0){
                    System.out.println("type = Cscene");
                    tt = TopicType.Scene;
                    
                }else if(topicDelete.getRolesByType(tm.getTopicBySubjectIdentifier(new URILocator("http://topicparentscene"))).size() != 0){
                    System.out.println("type = Pscene");
                    tt = TopicType.Scene;
                    
                }else if(topicDelete.getRolesByType(tm.getTopicBySubjectIdentifier(new URILocator("http://topicscene"))).size() != 0){
                    System.out.println("type = Scene");
                    tt = TopicType.Scene;
                    
                }else if(topicDelete.getRolesByType(tm.getTopicBySubjectIdentifier(new URILocator("http://topicdata"))).size() != 0){
                    System.out.println("type = Data");
                    tt = TopicType.Data;

                }else if(topicDelete.getRolesByType(tm.getTopicBySubjectIdentifier(new URILocator("http://topicvalue"))).size() != 0){
                    System.out.println("type = Value");
                    tt = TopicType.Value;

                }else if(topicDelete.getRolesByType(tm.getTopicBySubjectIdentifier(new URILocator("http://topicroot"))).size() != 0){
                    System.out.println("type = Root");
                    tt = TopicType.Root;
                }else{
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
                    
                    delList = n;
                } else if (tt == TopicType.Data) {

                    sss = "select $v from subject-identifier($t, \"" + SI
                            + "\"), data-value($t : scene, $v : value)?";

                    // 查询dv关联
//                    List<TopicIF> list
                    delList = wrapper.queryForList(sss);
                } else if (tt == TopicType.Value) {

                    // 无关联
                    
                }

                System.out.println("childrenlist size = " + delList.size());

                if (delList.size() == 0) {
                    TopicIF tempTopic = delStack.pop();
                    tempTopic.remove();
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
        }

        // ==============================================================
         new XTMTopicMapWriter(XTM).write(tm);

        System.out.println("===== Done =====");
        System.out.println("size of topic = " + tm.getTopics().size());
    }
}
