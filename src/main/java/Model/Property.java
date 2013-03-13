/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Beans.GalleriaDatahubBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.markdown4j.Markdown4jProcessor;

/**
 *
 * @author C. Levallois
 */
public class Property {

    private String tag;
    private String value;
    private String valueEdited;
    private String family;
    private List<String> candidateValues;
    private List<String> selectedValues;

    public Property() {
    }

    public Property(String tag, String value, String family) {
        this.tag = tag;
        this.value = value;
        this.valueEdited = value;
        this.family = family;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() throws IOException {
        String html = new Markdown4jProcessor().process(value);
        return html;
    }

    public String getValueWithoutHtml() throws IOException {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueEdited() {

        return valueEdited;
    }

    public void setValueEdited(String valueEdited) {
        this.value = valueEdited;
        this.valueEdited = valueEdited;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public List<String> getCandidateValues() {
        if (candidateValues == null) {
            return new ArrayList();
        } else {
            return candidateValues;
        }
    }

    public void setCandidateValues(List<String> candidateValues) {
        this.candidateValues = candidateValues;
    }

    public List<String> getSelectedValues() {
        return selectedValues;
    }

    public void setSelectedValues(List<String> selectedValues) {
        this.selectedValues = selectedValues;
//        String selection = "";
//        for (String string : selectedValues) {
//            selection = selection + ", " + string;
//            galleriaDatahubBean.addToMap(tag, string);
//        }
//        selection = selection.substring(2);
//        this.value = selection;
//        this.valueEdited = selection;
    }

    @Override
    public String toString() {
        return "key: " + this.tag + ", value: " + this.value + ", family: " + this.family;
    }
}
