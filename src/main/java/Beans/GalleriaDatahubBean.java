/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import CKANClient.CKANException;
import CKANClient.Client;
import CKANClient.Resource;
import Controller.ControllerBean;
import DatahubMethods.ResourcePropertiesIO;
import Model.Property;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@ViewScoped
public class GalleriaDatahubBean implements Serializable {

    @ManagedProperty("#{controllerBean}")
    private ControllerBean controllerBean;
    private int countResources;
    private boolean editMode;
    private List<Resource> listResources;
    private List<Property> listProperties;
    private List<Property> listPropertiesVRACore;
    private List<Property> listPropertiesNESSHI;
    private List<Property> previousPropertiesVRACore;
    private List<Property> nextPropertiesVRACore;
    private List<Property> previousPropertiesNESSHI;
    private List<Property> nextPropertiesNESSHI;
    private List<List<Property>> listListProperties;
    private List<List<Property>> listListPropertiesVRACore;
    private List<List<Property>> listListPropertiesNESSHI;
    private Resource resource;
    private Resource nextResource;
    private Resource previousResource;
    private Client client;
    private Property property;
    private String relatedPics;
    private boolean allPicsVisible;
    private Set<String> setNESSHIProperties;
    private Set<String> setVRACoreProperties;
    private Map<String, List<String>> mapListCandidateValues;
    private String nbVisiblePics;
    private String jumpName;

    public void setControllerBean(ControllerBean controllerBean) {
        this.controllerBean = controllerBean;
    }

    @PostConstruct
    public void init() {
        System.out.println("all pcs visible: " + allPicsVisible);
        client = controllerBean.getCaller().getClient();
        mapListCandidateValues = new HashMap();
        countResources = 0;
        listResources = new ArrayList();
        listResources.addAll(controllerBean.getListResources());
        listListPropertiesVRACore = new ArrayList();
        listListPropertiesNESSHI = new ArrayList();
        Iterator<Resource> listIterator = listResources.iterator();
        while (listIterator.hasNext()) {
            resource = listIterator.next();
            if (!allPicsVisible & resource.getDescription().contains("image.visible -> false")) {
//                System.out.println("pics removed!");
                listIterator.remove();
            }
        }

        setNESSHIProperties = new HashSet();
        setVRACoreProperties = new HashSet();
        System.out.println("number of pics in the list: " + listResources.size());
        for (Resource resource : listResources) {
            listProperties = ResourcePropertiesIO.stringToListProperties(resource.getDescription());
            listPropertiesVRACore = new ArrayList();
            listPropertiesNESSHI = new ArrayList();
            for (Property property : listProperties) {
                try {
                    addToMap(property.getTag(), property.getValue());
                } catch (IOException ex) {
                    Logger.getLogger(GalleriaDatahubBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Property property : listProperties) {
                property.setCandidateValues(mapListCandidateValues.get(property.getTag()));
                if (property.getFamily().equals("VRACore")) {
                    listPropertiesVRACore.add(property);
                    setVRACoreProperties.add(property.getTag());
                }
                if (property.getFamily().equals("NESSHI")) {
                    listPropertiesNESSHI.add(property);
                    setNESSHIProperties.add(property.getTag());

                }

            }
            listListPropertiesVRACore.add(listPropertiesVRACore);
            listListPropertiesNESSHI.add(listPropertiesNESSHI);
        }
        resource = listResources.get(countResources);
        listPropertiesVRACore = listListPropertiesVRACore.get(countResources);
        listPropertiesNESSHI = listListPropertiesNESSHI.get(countResources);

        System.out.println(
                "galleriaDataHubBean initiated!");
    }

    public List<Resource> getListResources() {
        return listResources;
    }

    public void setListResources(List<Resource> listResources) {
        this.listResources = listResources;
    }

    public Resource getResource() {
        return listResources.get(countResources);
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<Property> getListProperties() {
        return listProperties;
    }

    public void setListProperties(List<Property> listProperties) {
        this.listProperties = listProperties;
    }

    public List<Property> getListPropertiesNESSHI() {
        return listPropertiesNESSHI;
    }

    public void setListPropertiesNESSHI(List<Property> listPropertiesNESSHI) {
        this.listPropertiesNESSHI = listPropertiesNESSHI;
    }

    public List<Property> getListPropertiesVRACore() {
        return listPropertiesVRACore;
    }

    public void setListPropertiesVRACore(List<Property> listPropertiesVRACore) {
        this.listPropertiesVRACore = listPropertiesVRACore;
    }

    public void addNESSHIproperty() {
        Property property = new Property("", "", "NESSHI");
        listPropertiesNESSHI.add(property);
    }

    public String getLinkOnCKAN() {
        String link = "http://datahub.io/en/dataset/" + controllerBean.getDataset() + "/resource/" + resource.getId();
        return link;
    }

    public void setLinkOnCKAN() {
    }

    public void saveProperties() throws IOException {
        String description = ResourcePropertiesIO.listPropertiesToMarkDown(listPropertiesVRACore, listPropertiesNESSHI);
        resource.setDescription(description);
        listListPropertiesVRACore.set(countResources, listPropertiesVRACore);
        listListPropertiesNESSHI.set(countResources, listPropertiesNESSHI);
    }

    public String next() throws CKANException, IOException {

        //saves the updated resource to datahub.io
        saveProperties();

        //removes the resource from the current display if it was set to image.visible -> false
        if (resource.getDescription().contains("image.visible -> false")) {
            listResources.remove(countResources);
            listListPropertiesVRACore.remove(countResources);
            listListPropertiesNESSHI.remove(countResources);
        }

        client.updateResource(resource);
        countResources++;
        if (countResources == listResources.size()) {
            countResources = 0;
        }
        resource = listResources.get(countResources);
        listPropertiesVRACore = listListPropertiesVRACore.get(countResources);
        listPropertiesNESSHI = listListPropertiesNESSHI.get(countResources);

        //this reset countImage to zero (beginning of the list) if we hit the end of the list of pictures
        return null;
    }

    public String delete() throws CKANException {

        Client client = controllerBean.getCaller().getClient();
        client.deleteResource(resource);
        listResources.remove(resource);
        if (countResources >= listResources.size() || countResources < 0) {
            countResources = 0;
        }
        resource = listResources.get(countResources);
        listProperties = listListProperties.get(countResources);

        //this reset countImage to zero (beginning of the list) if we hit the end of the list of pictures
        return null;
    }

    public String previousAndForget() throws CKANException {

        countResources--;
        //this reset countImage to the last image of the collection if we hit the beginning of the list of pictures
        if (countResources < 0) {
            countResources = listResources.size() - 1;
        }
        resource = listResources.get(countResources);
        listPropertiesVRACore = listListPropertiesVRACore.get(countResources);
        listPropertiesNESSHI = listListPropertiesNESSHI.get(countResources);

        //this reset countImage to zero (beginning of the list) if we hit the end of the list of pictures
        return null;
    }

    public String nextAndForget() throws CKANException {

        countResources++;
        //this reset countImage to zero (beginning of the list) if we hit the end of the list of pictures
        if (countResources >= listResources.size()) {
            countResources = 0;
        }
        resource = listResources.get(countResources);
        listPropertiesVRACore = listListPropertiesVRACore.get(countResources);
        listPropertiesNESSHI = listListPropertiesNESSHI.get(countResources);

        return null;
    }

    public String relatesToPrevious() throws CKANException, IOException {

        //if the counter is already at zero there is no previous pic to merge with
        if (countResources == 0) {
            return null;
        }

        //set the description of the curr pic to non visible
        saveProperties();
        String description = resource.getDescription().replace("image.visible -> true", "image.visible -> false");
        resource.setDescription(description);

        //update the curr resource on CKAN
        client.updateResource(resource);




        countResources--;
        previousResource = listResources.get(countResources);
        previousPropertiesVRACore = listListPropertiesVRACore.get(countResources);
        previousPropertiesNESSHI = listListPropertiesNESSHI.get(countResources);

        String refCurrResource = "[" + resource.getId() + "] (" + resource.getUrl() + ")";
        for (Property property : listPropertiesVRACore) {
            if (property.getTag().equals("image.relatedTo") && !property.getValueWithoutHtml().equals("empty field")) {
                refCurrResource = refCurrResource + " ### " + property.getValueWithoutHtml();
            }
        }


        int indexProperty = -1;
        for (Property property : previousPropertiesVRACore) {
            if (property.getTag().equals("image.relatedTo")) {
                indexProperty = previousPropertiesVRACore.indexOf(property);
                property = ResourcePropertiesIO.addValueToMultipleValuesProperty(property, refCurrResource);
                previousPropertiesVRACore.set(indexProperty, property);
            }
        }
        //if indexProperty is still at -1 it means that no "relatedTo" property has been found. We create one.
        if (indexProperty == -1) {
            property = new Property("image.relatedTo", "", "VRACore");
            property = ResourcePropertiesIO.addValueToMultipleValuesProperty(property, refCurrResource);
            previousPropertiesVRACore.add(property);
        }

        //update the previous resource on CKAN
        previousResource.setDescription(ResourcePropertiesIO.listPropertiesToMarkDown(previousPropertiesVRACore, previousPropertiesNESSHI));
        client.updateResource(previousResource);

        listListPropertiesVRACore.set(countResources, previousPropertiesVRACore);
        listListPropertiesVRACore.remove(countResources + 1);
        listListPropertiesNESSHI.set(countResources, previousPropertiesNESSHI);
        listListPropertiesNESSHI.remove(countResources + 1);
        listResources.remove(countResources + 1);
        countResources++;
        return null;
    }

    public String relatesToNext() throws CKANException, IOException {

        //if the counter is already at the end of the list there is no next pic to merge with
        if (countResources == listResources.size() - 1) {
            return null;
        }

        //set the description of the curr pic to non visible
        saveProperties();
        String description = resource.getDescription().replace("image.visible -> true", "image.visible -> false");
        resource.setDescription(description);


        //update the curr resource on CKAN
        client.updateResource(resource);


        countResources++;
        nextResource = listResources.get(countResources);
        nextPropertiesVRACore = listListPropertiesVRACore.get(countResources);
        nextPropertiesNESSHI = listListPropertiesNESSHI.get(countResources);

        String refCurrResource = "[" + resource.getId() + "] (" + resource.getUrl() + ")";
        for (Property property : listPropertiesVRACore) {
            if (property.getTag().equals("image.relatedTo") && !property.getValueWithoutHtml().equals("empty field")) {
                refCurrResource = refCurrResource + " ### " + property.getValueWithoutHtml();
            }
        }

        int indexProperty = -1;
        for (Property property : nextPropertiesVRACore) {
            if (property.getTag().equals("image.relatedTo")) {
                indexProperty = nextPropertiesVRACore.indexOf(property);
//                System.out.println("curr property receiving new value: ");
//                System.out.println(property.toString());
//                System.out.println("value to be added: " + refCurrResource);
                property = ResourcePropertiesIO.addValueToMultipleValuesProperty(property, refCurrResource);
                nextPropertiesVRACore.set(indexProperty, property);
            }
        }
        //if indexProperty is still at -1 it means that no "relatedTo" property has been found. We create one.
        if (indexProperty == -1) {
            property = new Property("image.relatedTo", "", "VRACore");
            property = ResourcePropertiesIO.addValueToMultipleValuesProperty(property, refCurrResource);
            nextPropertiesVRACore.add(property);
        }

        //update the next resource on CKAN
        nextResource.setDescription(ResourcePropertiesIO.listPropertiesToMarkDown(nextPropertiesVRACore, nextPropertiesNESSHI));
        client.updateResource(nextResource);

        listListPropertiesVRACore.set(countResources, nextPropertiesVRACore);
        listListPropertiesVRACore.remove(countResources - 1);
        listListPropertiesNESSHI.set(countResources, nextPropertiesNESSHI);
        listListPropertiesNESSHI.remove(countResources - 1);
        listResources.remove(countResources - 1);
        return null;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public String getRelatedPics() throws IOException {
        Property propertyRelatedRefs = null;
        for (Property property : listPropertiesVRACore) {
            if (property.getTag().equals("image.relatedTo")) {
                propertyRelatedRefs = property;
                break;
            }
        }
        if (propertyRelatedRefs == null) {
            return "no related pic";
        }
        System.out.println("propertyRelatedRefs.getValueWithoutHtml()" + propertyRelatedRefs.getValueWithoutHtml());
        String[] urlPics = propertyRelatedRefs.getValueWithoutHtml().split(" ");
        StringBuilder htmlPics = new StringBuilder();
        String urlPic;
        for (String string : urlPics) {
            if (string.startsWith("(http")) {
                string = string.replace("(", "");
                string = string.replace(")", "");
                urlPic = "<a href=\"" + string + "\" target=\"_blank\"><img src=\"" + string + "\"" + " width=\"70\"/></a>  ";
                htmlPics.append(urlPic);
            }
        }
        return htmlPics.toString();
    }

    public void setRelatedPics(String relatedPics) {
        this.relatedPics = relatedPics;
    }

    public boolean isAllPicsVisible() {
        return allPicsVisible;
    }

    public void setAllPicsVisible(boolean allPicsVisible) throws IOException {
        if (this.allPicsVisible != allPicsVisible) {
            this.allPicsVisible = allPicsVisible;
            init();
        }
    }

    public void addToMap(String key, String value) {
        List<String> valuesInMap = new ArrayList();
        if (mapListCandidateValues.containsKey(key)) {
            valuesInMap = mapListCandidateValues.get(key);
            if (!valuesInMap.contains(value)) {
                valuesInMap.add(value);
                mapListCandidateValues.put(key, valuesInMap);
            }
        } else {
            valuesInMap.add(value);
            mapListCandidateValues.put(key, valuesInMap);
        }
    }

    public Map<String, List<String>> getMapListCandidateValues() {
        return mapListCandidateValues;
    }

    public void setMapListCandidateValues(Map<String, List<String>> mapListCandidateValues) {
        this.mapListCandidateValues = mapListCandidateValues;
    }

    public String getNbVisiblePics() {
        return String.valueOf(listResources.size());
    }

    public void setNbVisiblePics(String nbVisiblePics) {
        this.nbVisiblePics = nbVisiblePics;
    }

    public String getJumpName() {
        return jumpName;
    }

    public void setJumpName(String jumpName) {
        this.jumpName = jumpName;
    }

    public void jump() {
        Iterator<Resource> listResourcesIterator = listResources.listIterator(countResources);
        while (listResourcesIterator.hasNext()) {
            countResources++;
            Resource resource1 = listResourcesIterator.next();
            if (resource1.getName().equals(jumpName)) {
                return;
            }
        }
        countResources = 0;
        listResourcesIterator = listResources.listIterator(countResources);
        while (listResourcesIterator.hasNext()) {
            countResources++;
            Resource resource1 = listResourcesIterator.next();
            if (resource1.getName().equals(jumpName)) {
                return;
            }
        }
        countResources = 0;
    }
}
