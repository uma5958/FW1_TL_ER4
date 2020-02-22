/**
 * @author UmaMaheswararao
 */

package com.util;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import static com.base.BasePage.*;

// TODO: Auto-generated Javadoc
/**
 * The Class VerificationLibrary.
 */
public class VerificationLibrary {
	/**
	 * Verify element hex color.
	 *
	 * @param expectedColor - The expected color in hex format
	 * @param element - The Web Element
	 * 
	 * <pre>
	 * {@code
	 * Example:
	 * 		WebElement ele = getDriver().findElement(By.id(""));
	 * 		VerificationLibrary.verifyElementColor("#ffffff",ele);
	 * }
	 * </pre>
	 */
	public static void verifyElementColor(String expectedColor, By element){
		Assert.assertEquals(CssLibrary.getElementColorAsHex(element), expectedColor);
	}

	/**
	 * Verify element background color.
	 *
	 * @param expectedColor - The expected color in hex format
	 * @param element - The Web Element
	 */
	public static void verifyElementBackgroundColor(String expectedColor, By element){
		Assert.assertEquals(CssLibrary.getElementColorAsHex(element), expectedColor);
	}

	/**
	 * Verify element border color.
	 *
	 * @param expectedColor - The expected border color in hex format
	 * @param element - The Web Element
	 */
	public static void verifyElementBorderColor(String expectedColor, By element){
		Assert.assertEquals(CssLibrary.getElementBorderColorAsHex(element), expectedColor);
	}

	/**
	 * Verify element font size.
	 *
	 * @param expectedSize - The expected font size
	 * @param element - The Web Element
	 */
	public static void verifyElementFontSize(String expectedSize, By element){
		Assert.assertEquals(CssLibrary.getElementFontSize(element), expectedSize);
	}

	/**
	 * Verify element font family.
	 *
	 * @param expectedFontFamily - The expected font family
	 * @param element - The Web Element
	 */
	public static void verifyElementFontFamily(String expectedFontFamily, By element){
		Assert.assertEquals(CssLibrary.getElementFontFamily(element), expectedFontFamily);
	}


	/**
	 * Verify presence of text.
	 *
	 * @param expectedText - The expected text
	 * @param element - The Web Element
	 */
	public static void verifyTextPresent(String expectedText, WebElement element){
		Assert.assertEquals(element.getText(), expectedText);
	}


	/**
	 * Verify element present.
	 *
	 * @param getDriver() - WebgetDriver()
	 * @param locatorType - The element locator (id,name,linkText,className,xpath,cssSelector,tagName,partialLinkText)
	 * @param locatorValue - The locator value
	 */
	public static void verifyElementPresent(String locatorType, String locatorValue){
		List<WebElement> elements = null;
		if(locatorType.equals("id")){
			elements=getDriver().findElements(By.id(locatorValue));
		}
		else if(locatorType.equals("name")){
			elements=getDriver().findElements(By.name(locatorValue));
		}
		else if(locatorType.equals("linkText")){
			elements=getDriver().findElements(By.linkText(locatorValue));
		}
		else if(locatorType.equals("className")){
			elements=getDriver().findElements(By.className(locatorValue));
		}
		else if(locatorType.equals("xpath")){
			elements=getDriver().findElements(By.xpath(locatorValue));
		}
		else if(locatorType.equals("cssSelector")){
			elements=getDriver().findElements(By.cssSelector(locatorValue));
		}
		else if(locatorType.equals("tagName")){
			elements=getDriver().findElements(By.tagName(locatorValue));
		}
		else if(locatorType.equals("partialLinkText")){
			elements=getDriver().findElements(By.partialLinkText(locatorValue));
		}
		else{
			System.out.println("Wrong locator type");
		}

		Assert.assertEquals(elements.size(),1);
	}

	/**
	 * Verify checkbox is selected.
	 *
	 * @param element - The Web Element
	 */
	public static void verifyCheckboxSelected(WebElement element){
		Assert.assertEquals(element.isSelected(), true);
	}

	/**
	 * Verify radio button selected.
	 *
	 * @param element - The Web Element
	 */
	public static void verifyRadiobuttonSelected(WebElement element){
		Assert.assertEquals(element.isSelected(), true);
	}

	/**
	 * Verify element enabled.
	 *
	 * @param element - The Web Element
	 */
	public static void verifyElementEnabled(WebElement element){
		Assert.assertEquals(element.isEnabled(), true);
	}

	/**
	 * Verify element displayed.
	 *
	 * @param element - The Web Element
	 */
	public static void verifyElementDisplayed(WebElement element){
		Assert.assertEquals(element.isDisplayed(), true);
	}

	/**
	 * Verify image tooltip.
	 *
	 * @param image The image as WebElement
	 * @param expectedTitle - The expected title
	 */
	public static void verifyImageTooltip(WebElement image, String expectedTitle){		
		Assert.assertEquals(image.getAttribute("title"),expectedTitle);
	}

	/**
	 * Verify image loaded.
	 *
	 * @param getDriver() -The getDriver()
	 * @param image -The image
	 */
	public static void verifyImageLoaded(WebElement image){
		Assert.assertTrue(JavascriptLibrary.getImageLoadStatus(image));
	}

	/**
	 * Verify alert present.
	 *
	 * @param getDriver() -The getDriver()
	 */
	public static void verifyAlertPresent(){
		try {
			getDriver().switchTo().alert();
			Assert.assertTrue(true,"Alert is present");
		} catch (Exception e) {
			Assert.fail("Alert not present");
		}
	}

	/**
	 * Verify cookie by name.
	 *
	 * @param getDriver() -The getDriver()
	 * @param cookieName the cookie name
	 */
	public static void verifyCookieByName(String cookieName){		
		Cookie cookie = CookieLibrary.getCookieByName(cookieName);
		if(cookie!=null){
			Assert.assertTrue(true,"Cookie:"+cookieName+" is present");
		}
		else{
			Assert.fail("Cookie:"+cookieName+" not present");
		}
	}

	/**
	 * Verify cookie deleted by cookie name.
	 *
	 * @param getDriver() -The getDriver()
	 * @param cookieName the cookie name
	 */
	public static void verifyCookieDeletedByName(String cookieName){
		Cookie cookie = CookieLibrary.getCookieByName(cookieName);		
		if(cookie==null){
			Assert.assertTrue(true,"Cookie:"+cookieName+" is Deleted");
		}
		else{
			Assert.fail("Cookie:"+cookieName+" failed to delete");
		}
	}

	/**
	 * Verify element height.
	 *
	 * @param element - The Web Element
	 * @param expectedHeight -The expected height as integer
	 */
	public static void verifyElementHeight(By element, int expectedHeight){
		Assert.assertEquals(CssLibrary.getElementHeight(element),expectedHeight);		
	}

	/**
	 * Verify element width.
	 *
	 * @param element - The Web Element
	 * @param expectedWidth -The expected width as integer
	 */
	public static void verifyElementWidth(By element, int expectedWidth){
		Assert.assertEquals(CssLibrary.getElementWidth(element),expectedWidth);		
	}

	/**
	 * Verify element position. This depends on the resolution of the system
	 *
	 * @param element - The Web Element
	 * @param xpos the Expected x position of the element
	 * @param ypos the Expected y position of the element.
	 */
	public static void verifyElementPosition(By element, int xpos, int ypos){
		int x = CssLibrary.getElementLeftPosition(element);
		int y = CssLibrary.getElementTopPosition(element);		
		if(x==xpos && y==ypos){
			Assert.assertTrue(true, "Element Position matches");
		}
		else{
			Assert.fail("Position does not match. Expected xpos:"+xpos+" and expected ypos:"+ypos+" does not match Actual xpos:"+x+" and Actual ypos:"+y);
		}		
	}

	/**
	 * Verify page URL.
	 *
	 * @param getDriver() -The getDriver()
	 * @param expectedUrl the expected URL of the page
	 */
	public static void verifyPageUrl(String expectedUrl){
		Assert.assertEquals(getDriver().getCurrentUrl(), expectedUrl);
	}


	/**
	 * Verify broken link. Verify if the given link url is broken
	 *
	 * @param url -The url as String
	 */
	public static void verifyBrokenLink(String url){
		URL u = null;
		String response = ""; 
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) u.openConnection();
			connection.connect();
			response = connection.getResponseMessage();	        
			connection.disconnect();
			Assert.assertEquals(response, "OK", "Verifying link validity:");
		} catch (IOException e) {

		}				
	}	

	public static void verifyWebTableDatesOrder(List<WebElement> eleList, String order, String format) throws Exception {
		SoftAssert a = new SoftAssert();
		if (order.equalsIgnoreCase("Ascending")) {
			List<String> stringDates = new ArrayList<>();
			for (WebElement ele : eleList) {
				stringDates.add(ele.getText());
			}
			ArrayList<Date> datesExpected = new ArrayList<Date>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for (String date : stringDates) {
				try {
					if (date != ""){
						if (sdf.parse(date) == null) {
							Reporter.log("Empty Cell", true);
						} else {
							datesExpected.add(sdf.parse(date));
						}
					}
				} catch (Exception ex) {
					Reporter.log("Empty Cell", true);
				}
			}
			ArrayList<Date> datesActual = new ArrayList<Date>();
			datesActual.addAll(datesExpected);
			Collections.sort(datesExpected); // Ascending order
			Reporter.log("***** Expected dates *****", true);
			for (Date sl : datesExpected) {
				Reporter.log("Expected: " + sl, true);
			}
			Reporter.log("***** Actual dates *****", true);
			for (Date sl : datesActual) {
				Reporter.log("Actual: " + sl, true);
			}
			a.assertEquals(datesActual, datesExpected, "Dates are not in ascending order");
			Reporter.log("Dates are in ascending order", true);
		}else if (order.equalsIgnoreCase("Descending")) {
			List<String> stringDates = new ArrayList<>();
			for (WebElement ele : eleList) {
				stringDates.add(ele.getText());
			}
			ArrayList<Date> datesExpected = new ArrayList<Date>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for (String date : stringDates) {
				try {
					if (date != ""){
						if (sdf.parse(date) == null) {
							Reporter.log("Empty Cell", true);
						} else {
							datesExpected.add(sdf.parse(date));
						}
					}
				} catch (Exception ex) {
					Reporter.log("Empty Cell", true);
				}
			}
			ArrayList<Date> datesActual = new ArrayList<Date>();
			datesActual.addAll(datesExpected);
			Collections.sort(datesExpected, Collections.reverseOrder()); // Descending order
			Reporter.log("***** Expected dates *****", true);
			for (Date sl : datesExpected) {
				Reporter.log("Expected: " + sl, true);
			}
			Reporter.log("***** Actual dates *****", true);
			for (Date sl : datesActual) {
				Reporter.log("Actual: " + sl, true);
			}
			a.assertEquals(datesActual, datesExpected, "Dates are not in descending order");
			Reporter.log("Dates are in descending order", true);
		}
		a.assertAll();
	} 

	public static void verifyWebTableTextDataOrderWithoutCaseSensitive(List<WebElement> eleList, String order) {
		SoftAssert a = new SoftAssert();
		if (order.equalsIgnoreCase("Ascending")) {
			List<String> expectedList = new ArrayList<>();
			for (WebElement ele : eleList) {
				expectedList.add(ele.getText());
			}
			List<String> actualList = new ArrayList<>();
			actualList.addAll(expectedList);
			//Collections.sort(expectedList); // Ascending order
			Reporter.log("***** Expected *****", true);
			for (String s : expectedList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (String s : actualList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(expectedList, actualList, "Ascending order is NOT functional");
			Reporter.log("Ascending order is functional", true);
		}else if (order.equalsIgnoreCase("Descending")) {
			List<String> expectedList = new ArrayList<>();
			for (WebElement ele : eleList) {
				expectedList.add(ele.getText());
			}
			List<String> actualList = new ArrayList<>();
			actualList.addAll(expectedList);
			//Collections.sort(expectedList, Collections.reverseOrder()); // Descending order
			Collections.sort(expectedList, String.CASE_INSENSITIVE_ORDER);
			Collections.reverse(expectedList);
			Reporter.log("***** Expected *****", true);
			for (String s : expectedList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (String s : actualList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(expectedList, actualList, "Descending order is NOT functional");
			Reporter.log("Descending order is functional", true);
		}
		a.assertAll();
	}

	public static void verifyWebTableTextDataOrderWithCaseSensitive(List<WebElement> eleList, String order) {
		SoftAssert a = new SoftAssert();
		if (order.equalsIgnoreCase("Ascending")) {
			List<String> expectedList = new ArrayList<>();
			for (WebElement ele : eleList) {
				expectedList.add(ele.getText());
			}
			List<String> actualList = new ArrayList<>();
			actualList.addAll(expectedList);
			Collections.sort(expectedList); // Ascending order
			Reporter.log("***** Expected *****", true);
			for (String s : expectedList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (String s : actualList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(expectedList, actualList, "Ascending order is NOT functional");
			Reporter.log("Ascending order is functional", true);
		}else if (order.equalsIgnoreCase("Descending")) {
			List<String> expectedList = new ArrayList<>();
			for (WebElement ele : eleList) {
				expectedList.add(ele.getText());
			}
			List<String> actualList = new ArrayList<>();
			actualList.addAll(expectedList);
			Collections.sort(expectedList, Collections.reverseOrder()); // Descending order
			Reporter.log("***** Expected *****", true);
			for (String s : expectedList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (String s : actualList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(expectedList, actualList, "Descending order is NOT functional");
			Reporter.log("Descending order is functional", true);
		}
		a.assertAll();
	}

	public static void verifyWebTableNumberDataOrder(List<WebElement> eleList, String order) {
		SoftAssert a = new SoftAssert();
		if (order.equalsIgnoreCase("Ascending")) {
			List<Integer> expNumList = new ArrayList<Integer>();
			for (WebElement ele : eleList) {
				expNumList.add(NumberUtils.toInt(ele.getText()));
			}
			List<Integer> actNumList = new ArrayList<Integer>();
			actNumList.addAll(expNumList);
			Collections.sort(expNumList); // Ascending order
			Reporter.log("***** Expected *****", true);
			for (Integer s : expNumList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (Integer s : actNumList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(actNumList, expNumList, "Ascending order is NOT functional");
			Reporter.log("Ascending order is functional", true);
		}else if (order.equalsIgnoreCase("Descending")) {
			List<Integer> expNumList = new ArrayList<Integer>();
			for (WebElement ele : eleList) {
				expNumList.add(NumberUtils.toInt(ele.getText()));
			}
			List<Integer> actNumList = new ArrayList<Integer>();
			actNumList.addAll(expNumList);
			Collections.sort(expNumList, Collections.reverseOrder()); // Descending order
			Reporter.log("***** Expected *****", true);
			for (Integer s : expNumList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (Integer s : actNumList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(actNumList, expNumList, "Descending order is NOT functional");
			Reporter.log("Descending order is functional", true);
		}
		a.assertAll();
	}

	public static void verifyWebTableCurrencyDataOrder(List<WebElement> eleList, String order) {
		SoftAssert a = new SoftAssert();
		if (order.equalsIgnoreCase("Ascending")) {
			List<Long> expList = new ArrayList<Long>();
			for (WebElement ele : eleList) {
				String txt = ele.getText();
				String txtNew = txt.replaceAll("[, £ $ ₹ zł € $ лв R$ $ CHF ￥ Kč kr $ kn Ft Rp ₪ ¥ ₩ $ RM kr $ ₱ lei руб kr $ ฿ ₺ R د.إ]", "");
				expList.add(NumberUtils.toLong(txtNew));
			}
			List<Long> actList = new ArrayList<Long>();
			actList.addAll(expList);
			Collections.sort(expList); // Ascending order
			Reporter.log("***** Expected *****", true);
			for (Long s : expList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (Long s : actList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(expList, actList, "Ascending order is NOT functional");
			Reporter.log("Ascending order is functional", true);
		}else if (order.equalsIgnoreCase("Descending")) {
			List<Long> expList = new ArrayList<Long>();
			for (WebElement ele : eleList) {
				String txt = ele.getText();
				String txtNew = txt.replaceAll("[, £ $ ₹ zł € $ лв R$ $ CHF ￥ Kč kr $ kn Ft Rp ₪ ¥ ₩ $ RM kr $ ₱ lei руб kr $ ฿ ₺ R د.إ]", "");
				expList.add(NumberUtils.toLong(txtNew));
			}
			List<Long> actList = new ArrayList<Long>();
			actList.addAll(expList);
			Collections.sort(expList, Collections.reverseOrder()); // Descending order
			Reporter.log("***** Expected *****", true);
			for (Long s : expList) {
				Reporter.log("Expected: "+s, true);
			}
			Reporter.log("***** Actual *****", true);
			for (Long s : actList) {
				Reporter.log("Actual: "+s, true);
			}
			a.assertEquals(expList, actList, "Descending order is NOT functional");
			Reporter.log("Descending order is functional", true);
		}
		a.assertAll();
	}

	public static boolean isFileDownloaded(String downloadPath, String fileName) {
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(fileName)) {
				// File has been found, it can now be deleted:
				dirContents[i].delete();
				return true;
			}
		}
		return false;
	}

	public static boolean isOptionPresentInTheDropdown(WebElement ele, String expOption) {
		boolean found = false;
		Select s = new Select(ele);
		List<WebElement> optionsList = s.getOptions();
		for (WebElement opt : optionsList) {
			if (opt.getText().equals(expOption)) {
				Reporter.log("Given option: "+opt.getText(), true);
				found = true;
				break;
			}
		}
		return found;
	}

	public static void isOptionsMatchedInThe_DropDown(WebElement dropDown, String[] exp) {
		Select s = new Select(dropDown);
		List<WebElement> ddList = s.getOptions();
		for (WebElement ele : ddList) {
			Reporter.log("Options from title drop down: " +ele.getText(), true);
			boolean match = false;
			for (int i = 0; i < exp.length; i++){
				if (ele.getText().equals(exp[i])){
					match = true;
				}
			}
			Assert.assertTrue(match, "Given option not found");
		}
	}

	public static void isDropdownOptionsAreInSortedOrder(WebElement dropDown) {
		List<String> tempList = new ArrayList<String>(); // Storing DropDown values in temporary list
		Select sel=new Select(dropDown);
		List<WebElement> optionsList= sel.getOptions();
		for( WebElement opt : optionsList){
			tempList.add(opt.getText());
		}
		List<String> sortedlist= new ArrayList<String>(); // Copying list 1 into list 2
		sortedlist.addAll(tempList);
		Collections.sort(sortedlist); // Sorting in Ascending order
		Assert.assertEquals(tempList, sortedlist, "Options are not in sorted order");
	}

















}









