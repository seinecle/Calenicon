/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author C. Levallois
 */
public class Image implements Serializable{

    private String url;
    private String title;
    private Map<String, String> VRAProperties;
    private List<Property> listVRAProperties;

    public Image() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getVRAProperties() {
        return VRAProperties;
    }

    public void setVRAProperties(Map<String, String> VRAProperties) {
        this.VRAProperties = VRAProperties;
    }

    public List<Property> getListVRAProperties() {
        return listVRAProperties;
    }

    public void setListVRAProperties(List<Property> listVRAProperties) {
        this.listVRAProperties = listVRAProperties;
    }
    
    
}
