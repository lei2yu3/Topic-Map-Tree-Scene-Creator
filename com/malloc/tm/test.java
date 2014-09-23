package com.malloc.tm;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.malloc.tm.Creator.TopicType;

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

    public static void main(String[] args) throws IOException, InvalidQueryException {
//        new XTMTopicMapWriter(XTM).write(new InMemoryTopicMapStore()
//                .getTopicMap());

        XTMTopicMapReader r = new XTMTopicMapReader(new File(XTM));
        TopicMapIF tm = r.read();
        TopicMapBuilderIF b = tm.getBuilder();

        System.out.println("size of topic = " + tm.getTopics().size());
        String ss =
        // "select $b from subject-identifier($a,$b)?";
        // "subject-identifier($topic,\"http://topic321/\")?";
//        "import \"http://psi.ontopia.net/tolog/string/\" as str select $topic from topic-name($topic, $name), value($name, $s), str:contains($s, \"2\")?";
                
//                "id18($p: id12, $c: id14)?";
        "select $r, $p, $c from subject-identifier($p, \"http://topic3\"),id16( $r:id6,$p:id4 ),id18($p: id12, $c: id14)?";
        /*
        //==============================================================
        int delIndex = 0;
        String delSI = "http://" + String.valueOf(delIndex);
        TopicIF topicDelete = tm
                .getTopicBySubjectIdentifier(new URILocator(delSI));
//        subject-identifier($topic, SI)?
        
        String query = "select $topic1 from subject-identifier($topic1, \"http://topic3/\"), role-player($role1, $topic1), association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $topic2), $role1 /= $role2, not(association($Association), type($Association, $type), topic-name($type, $string), value($string, \"Root-Scene\"))?";
        
        TopicType tt = TopicType.Scene;
        if (tt == TopicType.Scene) {


        } else if (tt == TopicType.Data) {


        } else if (tt == TopicType.Value) {


        }
*/

        System.out.println("===== QUERY 2 =====");

        // query 2
        {
            QueryProcessorIF proc = QueryUtils.getQueryProcessor(tm);

            QueryResultIF result = proc.execute(ss);
            String[] variables = result.getColumnNames();
            for (int ix = 0; ix < variables.length; ix++) {
                System.out.println(variables[ix]);
            }

            Object[] row = new Object[result.getWidth()];
            while (result.next()) {
                result.getValues(row);
                for (int ix = 0; ix < variables.length; ix++) {
                    System.out.println("=1== " + row[ix]);
                }
            }
            result.close();
        }
        
        //==============================================================
        
        System.out.println("===== QUERY 1 =====");
        // query 1
        {
            QueryWrapper wrapper = new QueryWrapper(tm);

            @SuppressWarnings("unchecked")
            List<TopicIF> list =
            // wrapper.queryForMaps(ss);
            wrapper.queryForList(ss);

            for (int q = 0; q < list.size(); q++) {
                System.out.println(list.get(q).getObjectId());
                System.out.println(list.get(q).getSubjectIdentifiers());
                list.get(q).remove();
            }
        }

        //==============================================================
        new XTMTopicMapWriter(XTM).write(tm);
        
        System.out.println("===== Done =====");
        System.out.println("size of topic = " + tm.getTopics().size());
    }
}
