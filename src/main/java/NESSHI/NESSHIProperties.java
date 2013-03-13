/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NESSHI;

import XMPEditor.*;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author C. Levallois
 */
public class NESSHIProperties {

    private static Set<String> nesshiProperties;

    public static void populate() {
        nesshiProperties = new TreeSet();
        nesshiProperties.add("nesshi.category");
        nesshiProperties.add("nesshi.topics");
        nesshiProperties.add("nesshi.contexts");
        nesshiProperties.add("nesshi.techniques");
        nesshiProperties.add("nesshi.persons");
        nesshiProperties.add("nesshi.author");
        nesshiProperties.add("nesshi.places");
        nesshiProperties.add("nesshi.emotions");
        nesshiProperties.add("nesshi.events");
        nesshiProperties.add("nesshi.objects");
        nesshiProperties.add("nesshi.title");
        nesshiProperties.add("nesshi.titleContext");
        nesshiProperties.add("nesshi.title_en");
        nesshiProperties.add("nesshi.notesAnne");
        nesshiProperties.add("nesshi.notesClement");
        nesshiProperties.add("nesshi.notesSarah");
        nesshiProperties.add("nesshi.notesW");
        nesshiProperties.add("nesshi.notesX");
        nesshiProperties.add("nesshi.notesY");
        nesshiProperties.add("nesshi.notesZ");
    }

    public static Set<String> get() {
        return nesshiProperties;
    }
}
