package com.afcs.web.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.afcs.web.constants.Constants;
import com.chatak.transit.afcs.server.pojo.web.TransitFeatureRequest;

public class GenerateRoleHtml {

	private static Logger logger = LoggerFactory.getLogger(GenerateRoleHtml.class);
	
	private GenerateRoleHtml() {
		//To Avoid instantiation.
	}
	
  /**
   * Method to replace String builder
   * 
   * @param sb
   * @param length
   * @param oldChar
   * @param newChar
   */
  public static void replaceStringBuilderData(StringBuilder sb, int length, String oldChar, String newChar) {
    try {
      int index = sb.indexOf(oldChar);
      int endIndex = index + 2 + length + 2;
      sb.replace(index, endIndex, newChar);
    }
    catch(Exception e) {
    	logger.error("Error :: GenerateRoleHtml :: replaceStringBuilderData ",e);
    }
  }
  
  /**
   * Generate Permission Html
   * 
   * @param featureMap
   * @return
   */
  public static String generateHtml(Map<Long, List<TransitFeatureRequest>> featureMap, List<Long> existingFeature) {
    if(featureMap != null) {
      StringBuilder sb = new StringBuilder();
      // Permission columnwise display logic
      List<String> endIndex = StringUtils.endIndex(featureMap.size(), Constants.FEATURE_LIST_INDEX_LIST);
      int count = 1;

      for(Map.Entry<Long, List<TransitFeatureRequest>> entry : featureMap.entrySet()) {
        List<TransitFeatureRequest> featureList = entry.getValue();

        sb.append("<fieldset class='col-sm-3'>");
        StringBuilder checkRole = new StringBuilder();
        for(TransitFeatureRequest feature : featureList) {
          if(feature.getFeatureLevel() == 0) {

            String jsValue = "@@" + entry.getKey() + "@@";
            sb.append("<div id='dynamicFeatureId" + feature.getFeatureId() + "' style='display:none' >" + jsValue
                      + "</div>");
            
            sb.append(validateAndSFeatureIdet(existingFeature, feature));
            sb.append("<label  class='checkCustomLabel checkbox-header-label'>");
            sb.append(feature.getName());
            sb.append("</label></div>");
          }
          else {
            sb.append("<div id='dynamicSubFeatue" + feature.getFeatureId() + "' style='display:none' >"
                      + entry.getKey() + "</div>");
            sb.append(setFeatureId(existingFeature, feature));
            sb.append("<label class='checkCustomLabel last-label-checkbox'>");
            sb.append(feature.getName());
            sb.append("</label></div>");
            checkRole.append(feature.getFeatureId() + "|");
          }
        }
        String oldChar = "@@" + entry.getKey() + "@@";
        if(checkRole.length() > 0) {
          replaceStringBuilderData(sb,
                                   entry.getKey().toString().length(),
                                   oldChar,
                                   checkRole.toString().substring(0, checkRole.toString().length() - 1));
        }
        sb.append("</fieldset>");
        sb.append(validateCount(featureMap, endIndex, count));

        count++;
      }

      return sb.toString();
    }

    return null;
  }

private static String setFeatureId(List<Long> existingFeature, TransitFeatureRequest feature) {
	return (existingFeature.contains(feature.getFeatureId())) ? "<div class='checkboxCustom  checkedCustom last-label-checkbox' id='" + feature.getFeatureId()
	          + "' onclick='checkPermission(this)'>":"<div class='checkboxCustom last-label-checkbox' id='" + feature.getFeatureId()
	          + "' onclick='checkPermission(this)'>";
}

private static String validateAndSFeatureIdet(List<Long> existingFeature, TransitFeatureRequest feature) {
	return (existingFeature.contains(feature.getFeatureId())) ? "<div class='checkboxCustom checkedCustom checkbox-header-label' id='" + feature.getFeatureId()
	          + "' onclick='checkRoleData(this)' >": "<div class='checkboxCustom last-label-checkbox' id='" + feature.getFeatureId()
	          + "' onclick='checkRoleData(this)' >";
}

	private static String validateCount(Map<Long, List<TransitFeatureRequest>> featureMap, List<String> endIndex,
			int count) {
		return (endIndex.contains(String.valueOf(count)) || count == featureMap.size()) ? "<fieldset class='col-sm-12'><label>&nbsp;</label></fieldset>"
		                                                                  : "";
	}

  /**
   * Method to Generate Edit features
   * 
   * @param featureMap
   * @param existingFeature
   * @return
   */

  public static String generateEditFeatueHtml(Map<Long, List<TransitFeatureRequest>> featureMap, List<Long> existingFeature) {
    return generateHtml(featureMap, existingFeature);
  }

}
