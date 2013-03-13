/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import CKANClient.Resource;
import Controller.ControllerBean;
import Model.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@RequestScoped
public class GalleriaBean {

    @ManagedProperty("#{controllerBean}")
    private ControllerBean controllerBean;
    private int countImage;
    private Image image;
    private boolean editMode;

    public void setControllerBean(ControllerBean controllerBean) {
        this.controllerBean = controllerBean;
    }
    private List<Image> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<Image>();
        countImage = 0;
        for (Resource resource : controllerBean.getListResources()) {
            image = new Image();
            image.setUrl(resource.getUrl());
            image.setTitle("new image!");
            image.setVRAProperties(controllerBean.getMapPicsProperties().get(resource.getUrl()));
            image.setListVRAProperties(controllerBean.getListProperties().get(resource.getUrl()));
            images.add(image);
        }
    }

    public List<Image> getImages() {
        return images;
    }

    public String next() {

        countImage++;
        //this reset countImage to zero (beginning of the list) if we hit the end of the list of pictures
        if (countImage == images.size()) {
            countImage = 0;
        }
        return null;
    }


    public Image getImage() {
        return images.get(countImage);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
    
}
