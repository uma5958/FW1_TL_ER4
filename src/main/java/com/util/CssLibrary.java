/**
 * @author UmaMaheswararao
 */

package com.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class CssLibrary.
 */
public class CssLibrary {

	/**
	 * Gets the element color as hex string.
	 *
	 * @param element - The Web Element
	 * @return the element color as hex string
	 */
	public static String getElementColorAsHex(By element){
		return getHexColor(((WebElement) element).getCssValue("color"));		
	}
	
	/**
	 * Gets the element background color as hex string.
	 *
	 * @param element - The Web Element
	 * @return the element background color as hex string
	 */
	public static String getElementBackgroundColorAsHex(By element){
		return getHexColor(((WebElement) element).getCssValue("background-color"));		
	}
	
	
	/**
	 * Gets the element border color as hex.
	 *
	 * @param element - The Web Element
	 * @return the element border color as hex
	 */
	public static String getElementBorderColorAsHex(By element){
		return getHexColor(((WebElement) element).getCssValue("border-bottom-color"));
	}
	
	
	/**
	 * Gets the element font size.
	 *
	 * @param element - The Web Element
	 * @return the element font size
	 */
	public static String getElementFontSize(By element){
		return ((WebElement) element).getCssValue("font-size");
	}
	
	/**
	 * Gets the element font-family.
	 *
	 * @param element - The Web Element
	 * @return the element font-family
	 */
	public static String getElementFontFamily(By element){
		return ((WebElement) element).getCssValue("font-family");		
	}
	
	/**
	 * Gets the hex color for the given rgba.
	 *
	 * @param rgba - The color in rgba
	 * @return the hex color
	 */
	public static String getHexColor(String rgba){
		return Color.fromString(rgba).asHex();
	}
		
	/**
	 * Gets the element height.
	 *
	 * @param element - The Web Element
	 * @return the element height
	 */
	public static int getElementHeight(By element){
		return ((WebElement) element).getSize().getHeight();
	}
	
	/**
	 * Gets the element width.
	 *
	 * @param element - The Web Element
	 * @return the element width
	 */
	public static int getElementWidth(By element){
		return ((WebElement) element).getSize().getWidth();
	}

	/**
	 * Gets the element left position.
	 *
	 * @param element - The Web Element
	 * @return the element left position
	 */
	public static int getElementLeftPosition(By element){
		return ((WebElement) element).getLocation().getX();
	}
	
	/**
	 * Gets the element top position.
	 *
	 * @param element - The Web Element
	 * @return the element top position
	 */
	public static int getElementTopPosition(By element){
		return ((WebElement) element).getLocation().getY();		
	}
	
	
	
}








