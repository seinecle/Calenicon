/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMPEditor;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author C. Levallois
 */
public class VRAProperties {

    private static Set<String> vraProperties;

    public static void populate() {
        vraProperties = new TreeSet();
        vraProperties.add("work.agent");
        vraProperties.add("work.title");
        vraProperties.add("work.date");
        vraProperties.add("work.worktype");
        vraProperties.add("work.material");
        vraProperties.add("work.inscription");
        vraProperties.add("work.subject");
        vraProperties.add("work.locationSite");
        vraProperties.add("work.relation");
        vraProperties.add("work.description");
        vraProperties.add("work.technique");
        vraProperties.add("image.title");
        vraProperties.add("image.source");
        vraProperties.add("image.refid");
        vraProperties.add("image.date");
        vraProperties.add("image.worktype");
        vraProperties.add("image.agent");
        vraProperties.add("image.partialView");
        vraProperties.add("image.relatedTo");
    }

    public static Set<String> get() {
        return vraProperties;
    }
}
