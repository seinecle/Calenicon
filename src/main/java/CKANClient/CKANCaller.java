/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CKANClient;

import NESSHI.NESSHIProperties;
import XMPEditor.VRAProperties;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author C. Levallois
 */
public class CKANCaller implements Serializable {

    private Map<String, String> picProperties = new TreeMap();
    private Client client;
    private Dataset dataset;
    private String datasetString;
    private String currYear = null;
    private boolean isSampleIssue = false;
    private String currJournal = null;
    private String currJournalFolder;
    private int counter = 0;
    private boolean uploadOperation = false;
    private int nbUpload = 2000;
    private String currentJournalUploading = "Telegraaf";
    private Set<String> setResourcesNames;
    private String apiKey;

    public static void main(String args[]) throws IOException, CKANException {
        VRAProperties.populate();
        NESSHIProperties.populate();
        new CKANCaller().callCkanApi("vizTeam");

    }

    public CKANCaller(String datasetName) {
        this.datasetString = datasetName;
        VRAProperties.populate();
        NESSHIProperties.populate();
    }

    public CKANCaller() {
    }

    public Client getClient() {
        return client;
    }

    public List<Resource> callCkanApi(String api) throws IOException {
        if (api.equals("vizTeam")) {
            apiKey = "24a733ea-a4b9-4a5f-890b-b8a6096e8efa";
        } else {
            apiKey = api;
        }
        client = new Client(new Connection("http://datahub.io"), apiKey);

        try {

            if (uploadOperation) {
                setResourcesNames = new HashSet();
                datasetString = "nesshi-pics-telegraaf";
                Dataset.SearchResults search_results = client.findDatasets(datasetString);
                dataset = search_results.results.get(0);

                for (Resource resource : dataset.getResources()) {
                    setResourcesNames.add(resource.getName());
                }

                File mainFolder = new File("D:\\Docs Pro Clement\\NESSHI\\Project ICONOGRAPHY\\Guardian pdfs\\Docs Dutch Team");
                FolderLooper(mainFolder);
                return null;

            } else {
                Dataset.SearchResults search_results = client.findDatasets(datasetString);
                dataset = search_results.results.get(0);
////            client.updateUserRights("seinecle", datasetString, "editor");
//
                return dataset.getResources();
            }




        } catch (CKANException e) {
            System.out.println(e);
            return null;
        }
    }

    private void FolderLooper(File mainFolder) throws IOException, CKANException {
        BasicFileAttributes attr;
        Path fileNIO;
        for (File file : mainFolder.listFiles()) {
            if (counter > nbUpload) {
                break;
            }

            if (file.isDirectory()) {
                if (file.getName().equals("NRC")) {
                    currJournal = "part of [NRC Handelsblad] (http://en.wikipedia.org/wiki/NRC_Handelsblad)";
                    currJournalFolder = "NRC";
                }

                if (file.getName().equals("Telegraaf")) {
                    currJournal = "part of [De Telegraaf] (http://en.wikipedia.org/wiki/De_Telegraaf)";
                    currJournalFolder = "Telegraaf";
                }

                if (file.getName().matches("\\d\\d\\d\\d")) {
                    currYear = file.getName();
                }

                if (file.getName().matches("full issue")) {
                    continue;
                }

                if (counter < nbUpload) {
                    FolderLooper(file);
                }

            } else {
                if (!currJournalFolder.equals(currentJournalUploading)) {
                    continue;
                }
                if (!setResourcesNames.contains(currJournalFolder + "_" + currYear + "_" + file.getName())) {
                    fileNIO = Paths.get(file.getAbsolutePath());
                    attr = Files.readAttributes(fileNIO, BasicFileAttributes.class);
                    picProperties.put("work.relation", currJournal);
                    picProperties.put("work.date", currYear);
                    picProperties.put("work.locationSite", "archived copy of " + currJournalFolder + " held by the [National Library of The Netherlands] (http://www.kb.nl/en/home)");
                    picProperties.put("image.date", attr.creationTime().toString());
                    picProperties.put("image.agent", "[Clement Levallois] (http://www.clementlevallois.net) for [NESSHI] (http://www.nesshi.eu)");
                    picProperties.put("image.title", currJournalFolder + "_" + currYear + "_" + file.getName());
                    picProperties.put("image.worktype", "digital imaging");
                    picProperties.put("name", currJournalFolder + "_" + currYear + "_" + file.getName());
                    String fileExt[] = file.getName().split("\\.");
                    picProperties.put("resource_type", "Image");
                    picProperties.put("image.visible", "true");
                    picProperties.put("format", fileExt[fileExt.length - 1]);
                    picProperties.put("url", "http://www.clementlevallois.net/nesshi/journals/" + currJournalFolder + "/" + currYear + "/" + file.getName());
                    for (String key : VRAProperties.get()) {
                        if (!picProperties.containsKey(key)) {
                            picProperties.put(key, "empty field");
                        }
                    }
                    for (String key : NESSHIProperties.get()) {
                        if (!picProperties.containsKey(key)) {
                            picProperties.put(key, "empty field");
                        }
                    }
                    client.createResource(datasetString, picProperties);
//                    client.uploadFile();
                    System.out.println(counter++);
                }



            }
        }

        isSampleIssue = false;

    }
}