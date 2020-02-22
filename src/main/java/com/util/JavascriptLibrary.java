/**
 * @author UmaMaheswararao
 */

package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import static com.base.BasePage.*;

// TODO: Auto-generated Javadoc
/**
 * The Class JavascriptLibrary.
 */
public class JavascriptLibrary {
	
	/**
	 * Gets the image load status.
	 *
	 * @param getDriver() The getDriver()
	 * @param element - The element
	 * @return the image load status
	 */
	public static boolean getImageLoadStatus(WebElement element){
		Boolean ImagePresent = (Boolean) ((JavascriptExecutor)getDriver()).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
		return ImagePresent;
		
	}
	
	/**
	 * JavaScript click element.
	 *
	 * @param getDriver() - The getDriver()
	 * @param element - The element to click
	 */
	public static void javascriptClickElement(WebElement element){
		((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", element);
	}
	
	
	/**
	 * JavaScript type into textbox.
	 *
	 * @param getDriver() the getDriver()
	 * @param element1 the element1
	 * @param value - The value to type
	 */
	public static void javascriptType(WebElement element1, String value){
		((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",element1);
	}
	
	/**
	 * JavaScript scroll window.
	 *
	 * @param getDriver() - The getDriver()
	 * @param xScroll the horizontal scroll
	 * @param yScroll the vertical scroll
	 */
	public static void javascriptScrollWindow(int xScroll, int yScroll){
		((JavascriptExecutor)getDriver()).executeScript("window.scrollTo("+xScroll+","+yScroll+")");
	}
	
		
	/**
	 * Gets the depth.
	 *
	 * @param getDriver() - The getDriver()
	 * @param ind - Independent WebElement
	 * @param dep - Dependent WebElement
	 * @return the depth
	 */
	public static Long getDepth(WebElement ind, WebElement dep){
		String function1 = "function parents(node){"+
							"var nodes = [node];"+
							"for (;node;node = node.parentNode) {"+
								"nodes.unshift(node);"+
							"}"+	
							"return nodes;"+
							"}";
		String function2 = "function commonAncestor(node1,node2) {"+
								"var parents1 = parents(node1);"+
								"var parents2 = parents(node2);"+
								"for (var i = 0; i < parents1.length; i++) {"+
									"if (parents1[i] != parents2[i]){return i;}"+
								"}"+
							"} return commonAncestor(arguments[0],arguments[1]);";
		
		String finalFunction = function1+function2;		
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		Long depth = (Long) js.executeScript(finalFunction,ind,dep);
		return depth;
	}
	
	
	/**
	 * Gets the common parent.
	 *
	 * @param getDriver() the getDriver()
	 * @param independentElementXpath - Independent element xpath
	 * @param dependentElementXpath - Dependent element xpath
	 * @return the immediate common parent
	 */
	public static WebElement getCommonParent(String independentElementXpath, String dependentElementXpath){
		WebElement ind = getDriver().findElement(By.xpath(independentElementXpath));
		
		String function1 = "function parents(node){"+
							"var nodes = [node];"+
							"for (;node;node = node.parentNode) {"+
								"nodes.unshift(node);"+
							"}"+	
							"return nodes;"+
							"}";
		String function2 = "function commonAncestor(node1,node2) {"+
								"var parents1 = parents(node1);"+
								"var parents2 = parents(node2);"+
								"for (var i = 0; i < parents1.length; i++) {"+
									"if (parents1[i] != parents2[i]){return parents1[i-1];}"+
								"}"+
							"} return commonAncestor(arguments[0],arguments[1]);";		
		
		String finalFunction = function1+function2;
				
		Map<Long,WebElement> myMap = new HashMap<Long,WebElement>();		
		
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		List<WebElement> dependents = getDriver().findElements(By.xpath(dependentElementXpath));
		for(int i=0;i<dependents.size();i++){
			WebElement dep = dependents.get(i);			
			WebElement parent = (WebElement) js.executeScript(finalFunction,ind,dep);
			Long depth = getDepth(ind, dep);
			myMap.put(depth, parent);		
			System.out.println(i);
		}
		
		TreeMap<Long,WebElement> treeMap = new TreeMap<Long,WebElement>(myMap);
		System.out.println(treeMap.lastEntry().getValue());
		return treeMap.lastEntry().getValue();
		
	}
	
	/**
	 * Gets the common parent textbox jquery.
	 *
	 * @param getDriver() the getDriver()
	 * @param independentElementXpath the independent element xpath
	 * @return the common parent textbox jquery
	 */
	public static WebElement getCommonParentTextboxJquery(String independentElementXpath){	
		WebElement ind = getDriver().findElement(By.xpath(independentElementXpath));
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		WebElement parent = (WebElement) js.executeScript("for(var i=0;i<15;i++){var parent = jQuery(arguments[0]).parents().eq(i);if(parent.has('input[type=text]').length){return parent.get(0);}}",ind);
		return parent;	
	}
	
	/**
	 * Gets the common parent checkbox jquery.
	 *
	 * @param getDriver() the getDriver()
	 * @param independentElementXpath the independent element xpath
	 * @return the common parent checkbox jquery
	 */
	public static WebElement getCommonParentCheckboxJquery(String independentElementXpath){	
		WebElement ind = getDriver().findElement(By.xpath(independentElementXpath));
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		WebElement parent = (WebElement) js.executeScript("for(var i=0;i<15;i++){var parent = jQuery(arguments[0]).parents().eq(i);if(parent.has('input[type=checkbox]').length){return parent.get(0);}}",ind);
		return parent;	
	}
	
	/**
	 * Gets the common parent radio jquery.
	 *
	 * @param getDriver() the getDriver()
	 * @param independentElementXpath the independent element xpath
	 * @return the common parent radio jquery
	 */
	public static WebElement getCommonParentRadioJquery(String independentElementXpath){	
		WebElement ind = getDriver().findElement(By.xpath(independentElementXpath));
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		WebElement parent = (WebElement) js.executeScript("for(var i=0;i<15;i++){var parent = jQuery(arguments[0]).parents().eq(i);if(parent.has('input[type=radio]').length){return parent.get(0);}}",ind);
		return parent;	
	}
	
	
	/**
	 * Gets the jQuery load status.
	 *
	 * @param getDriver() the getDriver()
	 * @return the jQuery load status
	 */
	public static boolean getJqueryLoadStatus(){
		boolean status=false;
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		status = Boolean.valueOf(js.executeScript("if (typeof jQuery != 'undefined') {return true;}else{return false;}").toString());		
		return status;
	}
	
	/**
	 * Adjusts the zoom level of the browser based on the given 'percentage of zoom' you want
	 * 
	 */
	public static void adjustBrowserZoom(int zoom) {
		JavascriptExecutor jse = (JavascriptExecutor)getDriver();
		jse.executeScript("document.body.style.zoom = '"+zoom+"%';");
	}
	
}










