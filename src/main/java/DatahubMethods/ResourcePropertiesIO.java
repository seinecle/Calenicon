/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 Copyright 2013 Clement Levallois
 Authors : Clement Levallois <clement.levallois@gephi.org>
 Website : http://www.clementlevallois.net


 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2013 Clement Levallois. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s):

 Portions Copyrighted 2011 Gephi Consortium.
 */
package DatahubMethods;

import Model.Property;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourcePropertiesIO {

    public static String listPropertiesToMarkDown(List<Property> listProperties, List<Property> listPropertiesNESSHI) throws IOException {

        StringBuilder description = new StringBuilder();
        for (Property property : listProperties) {
//            System.out.println("property: key: " + property.getTag() + ", valueEdited: " + property.getValue());
            description.append(property.getTag()).append(" -> ").append(property.getValueWithoutHtml()).append("<br/>");
        }
        for (Property property : listPropertiesNESSHI) {
//            System.out.println("property: key: " + property.getTag() + ", valueEdited: " + property.getValue());
            description.append(property.getTag()).append(" -> ").append(property.getValueWithoutHtml()).append("<br/>");
        }


        return description.toString();
    }

    public static List<Property> stringToListProperties(String string) {
        List<Property> listProperties = new ArrayList();
        String propertiesString[] = string.split("<br/>");
        for (String line : propertiesString) {
            String keyValue[] = line.split(" -> ");
            if (keyValue[0].contains("image.") ||keyValue[0].contains("work.")) {
                listProperties.add(new Property(keyValue[0], keyValue[1], "VRACore"));
            }
            if (keyValue[0].contains("nesshi")) {
                listProperties.add(new Property(keyValue[0], keyValue[1], "NESSHI"));
            }
        }


        return listProperties;
    }

    public static Property addValueToMultipleValuesProperty(Property property, String newValue) throws IOException {

        String values = property.getValueWithoutHtml();
        if (values.equals("empty field")){
            values = "";
        }
        if (!values.contains(newValue)) {
            values = values + " ### " + newValue;
        }
        return new Property(property.getTag(), values, property.getFamily());

    }

    public static List<Property> mergeOnlyPropertiesWithMultipleValues(List<Property> previousProperties, List<Property> currProperties) throws IOException {
        int indexPrevProperty;
        for (Property prevProperty : previousProperties) {
            indexPrevProperty = previousProperties.indexOf(prevProperty);
            for (Property currProperty : currProperties) {
                if (prevProperty.getTag().equals(currProperty.getTag())) {
                    if (prevProperty.getValueWithoutHtml().contains("###") || currProperty.getValueWithoutHtml().contains("###")) {
                        prevProperty = ResourcePropertiesIO.addValueToMultipleValuesProperty(prevProperty, currProperty.getValueWithoutHtml());
                        previousProperties.set(indexPrevProperty, prevProperty);
                    }
                }
            }
        }
        return previousProperties;


    }

    public static String mapPropertiesToMarkDown(Map<String, String> mapProperties, String datasetName) {

        StringBuilder mdBuilder = new StringBuilder();
        StringBuilder descriptionBuilder = new StringBuilder();
        mdBuilder.append("{\"package_id\":\"");
        mdBuilder.append(datasetName);
        mdBuilder.append("\", ");
        for (String key : mapProperties.keySet()) {
            if (key.contains(".")) {
                descriptionBuilder.append(key);
                descriptionBuilder.append(" -> ");
                descriptionBuilder.append(mapProperties.get(key));
//                descriptionBuilder.append(System.getProperty("line.separator"));
                descriptionBuilder.append("<br/>");
            } else {
                mdBuilder.append("\"");
                mdBuilder.append(key);
                mdBuilder.append("\":\"");
                mdBuilder.append(mapProperties.get(key));
                mdBuilder.append("\", ");
            }
        }
        mdBuilder.append("\"");
        mdBuilder.append("description");
        mdBuilder.append("\":\"");
        mdBuilder.append(descriptionBuilder.toString());
        mdBuilder.append("\"}");




        return mdBuilder.toString();
    }
}
