/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import CKANClient.CKANCaller;
import CKANClient.Resource;
import Model.Property;
import XMPEditor.XMPController;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@SessionScoped
public class ControllerBean implements Serializable {

    private List<Resource> listResources;
    private String dataset;
    private Map<String, Map<String, String>> mapPicsProperties;
    private Map<String, List<Property>> listProperties;
    private CKANCaller caller;
    private String apiKey;

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public List<Resource> getListResources() {
        return listResources;
    }

    public Map<String, Map<String, String>> getMapPicsProperties() {
        return mapPicsProperties;
    }

    public Map<String, List<Property>> getListProperties() {
        return listProperties;
    }

    public void setListProperties(Map<String, List<Property>> listProperties) {
        this.listProperties = listProperties;
    }

    public CKANCaller getCaller() {
        return caller;
    }

    public String getDatasetFromCKAN() throws ImageReadException, IOException, ImageWriteException, URISyntaxException {
        caller = new CKANCaller(dataset);
        listResources = caller.callCkanApi(apiKey);
        if (listResources == null || listResources.isEmpty()) {
            return null;
        }
        Collections.sort(listResources);
        System.out.println("number of resources found after the call: " + listResources.size());
        mapPicsProperties = new TreeMap();
        //these lines will be useful when we will actually write metadata into pics - that's not yet the case
        //        for (Resource resource : listResources) {
//            mapPicsProperties.put(resource.getUrl(), new XMPController().readXMP(resource.getUrl()));
//        }
//        transformMapToList();
        return "gallerydatahub?faces-redirect=true";
//        for (Resource resource : listFiles) {
//            try {
//                XMPController.readXMP(resource.getUrl());
//            } catch (ImageReadException ex) {
//                Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ImageWriteException ex) {
//                Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (URISyntaxException ex) {
//                Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }

    private void transformMapToList() {
        List<Property> list;
        Map<String, String> map;
        Map<String, List<Property>> mapReturned = new HashMap();
        Property property;
        for (String string : mapPicsProperties.keySet()) {
            list = new ArrayList();
            map = mapPicsProperties.get(string);
            for (String string2 : map.keySet()) {
                property = new Property();
                property.setTag(string2);
                property.setValue(map.get(string2));
                property.setFamily("VRACore");
                list.add(property);
            }
            mapReturned.put(string, list);

        }
        listProperties = mapReturned;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
