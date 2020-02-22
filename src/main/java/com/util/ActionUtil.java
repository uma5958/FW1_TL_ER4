/**
 * @author UmaMaheswararao
 */

package com.util;

import static com.base.BasePage.getDriver;
import static com.base.BasePage.tlExtentTest;
import static com.base.BasePage.tlExtentTestNode;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.YEAR;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.github.javafaker.Faker;


public class ActionUtil {
	
	public static final String calendarTimeFormat = "dd/MM/yyyy, EEE";

	//================ Action Methods Set 1 =================

	public static void createNode(String text) throws Exception {
		tlExtentTestNode.remove();
		ExtentTest node = tlExtentTest.get().createNode(text);
		tlExtentTestNode.set(node);
	}
	
	public static void log(String text) {
		tlExtentTestNode.get().log(Status.PASS, text);
		Reporter.log(text, true);
	}
	
	public static void clearBrowserCookies() throws Exception {
		getDriver().manage().deleteAllCookies();
	}

	public static void pauseTheExecution(int seconds) throws Exception {
		long milliSeconds = seconds * 1000;
		Thread.sleep(milliSeconds);
	}

	public static WebElement getWebElement(String elementName, By element) {
		Reporter.log("Trying to find Element: "+elementName, true);
		WebElement ele = getDriver().findElement(element);
		Reporter.log("Found Element: "+elementName, true);
		return ele;
	}

	public static List<WebElement> getWebElements(By element) {
		return getDriver().findElements(element);
	}

	public static void click(String elementName, By element) throws Exception {
		log("Clicking on: "+elementName);
		WebElement ele = getWebElement(elementName, element);
		ele.click();
	}

	public static void jsClick(String elementName, By element) throws Exception {
		log("Clicking using JavaScript on: "+elementName);
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", getWebElement(elementName, element));
	}


	public static void sendKeys(String elementName, By element, String text) throws Exception {
		log("Sending text '"+text+"' to: "+elementName);
		getWebElement(elementName, element).sendKeys(text);
	}

	public static String getText(String elementName, By element) throws Exception {
		log("Getting text from: "+elementName);
		return getWebElement(elementName, element).getText();
	}

	public static void clearUsingSendKeys(String elementName, By element) throws Exception {
		log("Clearing the value in: "+elementName);
		getWebElement(elementName, element).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
	}

	public static void waitForElementToBeDisplayed(String elementName, By element, long timeOutInSeconds) throws Exception {
		log("Waiting for: "+elementName+" to be displayed for maximum "+timeOutInSeconds+" Seconds");
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Exception e) {
		}
	}

	public static void waitForEitherOfElementToBeDisplayed(String element1Name, By element1, String element2Name, By element2, long timeOutInSeconds) {
		log("Waiting for either of the elements: "+element1Name
					+" (or) "+element2Name+" to be displayed for maximum "+timeOutInSeconds+" Seconds");
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(element1),
					ExpectedConditions.presenceOfElementLocated(element2)));
		} catch (Exception e) {
		}
	}

	public static void waitForElementToBeInvisible(String elementName, By element, long timeOutInSeconds) throws Exception {
		log("Waiting for "+elementName+" to be invisible for maximum "+timeOutInSeconds+" Seconds");
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
		} catch (Exception e) {

		}
	}

	public static boolean verifyElementPresent(String elementName, By element) throws Exception {
		log("Verifying the presence of: "+elementName);
		try {
			getWebElement(elementName, element).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean verifyEitherOfElementPresent(String element1Name, By element1, String element2Name, By element2) throws Exception {
		log("Verifying the presence of either of: "+element1Name+" (or) "+element2Name);
		if (verifyElementPresent(element1Name, element1))
			return true;
		else if (verifyElementPresent(element2Name, element2))
			return true;
		return false;
	}

	public static String searchAndSelectRequiredValueFromDropDown(String clickElementName, By clickElement, String inputElementName, By inputElement,
			String requiredValue, String listElementsName,  By listElements) throws Exception {
		String dropDownElementText = "";
		click(clickElementName, clickElement);
		sendKeys(inputElementName, inputElement, requiredValue);
		waitForElementToBeDisplayed(listElementsName, listElements, 10);
		pauseTheExecution(1);
		List<WebElement> list = getWebElements(listElements);
		for (WebElement ele : list) {
			dropDownElementText = ele.getText();
			if (dropDownElementText.equals(requiredValue)) {
				ele.click();
				break;
			}
		}
		return requiredValue;
	}

	public static void refreshTheBrowserPage() throws Exception {
		getDriver().navigate().refresh();
	}

	public static void getWebElementByJavaScriptExecutor() throws Exception {
		String script = "return window.getComputedStyle(document.querySelector(\"label[for='homeAddress.addressLine2']\"),':before').getPropertyValue('content')";
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		String content = (String) js.executeScript(script);
		System.out.println(content);
	}

	public static void verifyCurrentUrlWithExpectedUrl(String expectedURL) throws Exception {
		String currentURL = getDriver().getCurrentUrl();
		log("Verifying the current URL(" + currentURL + ") with expected URL(" + expectedURL + ")");
		if (!currentURL.equals(expectedURL))
			assertTrue(false, "Current Url is not matching with the expected URL, Expected URL = " + expectedURL
					+ " but Current Url = " + currentURL + "");
	}

	public static String getCurrentUrl() throws Exception {
		return getDriver().getCurrentUrl();
	}

	public static void navigateBack() throws Exception {
		log("Navigating back(Like, Clicking on back arrow in back button)");
		getDriver().navigate().back();
	}

	public static boolean verifyElementNotPresent(String elementName, By element) throws Exception {
		log("Verifying the non presence of: "+elementName);
		try {
			getWebElement(elementName, element).isDisplayed();
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	public static void loadTheUrl(String url) throws Exception {
		// if (test != null)
		// tlExtentTestNode.get().log(Status.PASS, "Loading the URL: " + url);
		getDriver().get(url);
	}

	//================ Action Methods Set 2 ================

	// ScreenShot code
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

	// Wait for element present
	public static void waitForElementPresent(String elementName, By locator) {
		log("Waiting for for the presence of: "+elementName);
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			Reporter.log("Some exception/error occured while waiting for element: "+locator.toString(), true);
			e.printStackTrace();
		}
	}

	// Returns element
	public static WebElement getElement(String elementName, By locator) {
		WebElement element = null;
		try {
			waitForElementPresent(elementName, locator);
			element = getDriver().findElement(locator);
		} catch (Exception e) {
			Reporter.log("Some exception occured while creating element: "+locator.toString(), true);
			e.printStackTrace();
		}
		return element;
	}

	// Returns elements
	public static List<WebElement> getElements(String elementName, By locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		List<WebElement> list = null;
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			waitForElementPresent(elementName, locator);
			list = getDriver().findElements(locator);
		} catch (Exception e) {
			Reporter.log("Some exception occured while creating element: "+locator.toString(), true);
			e.printStackTrace();
		}
		return list;
	}

	// Returns the title of the page
	public static String getPageTitle() {
		return getDriver().getTitle();
	}

	// Click Method by using JAVA Generics (You can use both By or Webelement)
	public static <T> void click(String elementName, T elementAttr) {
		log("Clicking on: "+elementName);
		if (elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By)elementAttr);
			getDriver().findElement((By) elementAttr).click();
		} else {
			((WebElement) elementAttr).click();
		}
	}

	public static <T> void sendKeys(String elementName, T elementAttr, String text) {
		log("Sending '"+text+"' to "+elementName);
		if (elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By)elementAttr);
			getDriver().findElement((By) elementAttr).sendKeys(text);
		} else {
			((WebElement) elementAttr).sendKeys(text);
		}
	}

	public static <T> String readText(String elementName, T elementAttr) {
		if (elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			return getDriver().findElement((By) elementAttr).getText();
		} else {
			return ((WebElement) elementAttr).getText();
		}
	}

	// Close popup if exists
	public static void handlePopup(By by) throws Exception {
		List<WebElement> popups = getDriver().findElements(by);
		if (!popups.isEmpty()) {
			popups.get(0).click();
			Thread.sleep(500);
		}
	}

	// Wait for element to be clickable then click
	public static <T> void waitForClickableThenClick(String elementName, T elementAttr) {
		log("Waiting for: "+elementName+" then clicking on: "+elementName);
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		if(elementAttr.getClass().getName().contains("By")) {
			WebElement ele = getDriver().findElement((By)elementAttr);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
		} else {
			WebElement ele = ((WebElement)elementAttr);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
		}
	}

	// Wait for element to be visible then click
	public static <T> void waitForVisibleThenClick(String elementName, T elementAttr) {
		log("Waiting for visibility of: "+elementName+" then clicking on: "+elementName);
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		if(elementAttr.getClass().getName().contains("By")) {
			WebElement ele = getDriver().findElement((By)elementAttr);
			wait.until(ExpectedConditions.visibilityOf(ele));
			ele.click();
		} else {
			WebElement ele = ((WebElement)elementAttr);
			wait.until(ExpectedConditions.visibilityOf(ele));
			ele.click();
		}
	}

	// Wait for element to be visible then type
	public static <T> void waitForVisibleThenType(String elementName, T elementAttr, String text) {
		log("Waiting for visibility of: "+elementName+" then sending: "+text);
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		if(elementAttr.getClass().getName().contains("By")) {
			WebElement ele = getDriver().findElement((By)elementAttr);
			wait.until(ExpectedConditions.visibilityOf(ele));
			ele.sendKeys(text);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			wait.until(ExpectedConditions.visibilityOf(ele));
			ele.sendKeys(text);
		}
	}

	// JavaScript click
	public static <T> void jsClick(String elementName, T elementAttr){
		log("Clicking on: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
		}
	}

	// JavaScript type
	public static <T> void jsType(String elementName, T elementAttr, String value){
		log("Sending '"+value+"' to: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'", ele);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'", ele);
		}
	}

	// Clear & type
	public static <T> void clearAndType(String elementName, T elementAttr, String value) {
		log("Clearing text in: "+elementName+" and sending '"+value+"'");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			ele.clear();
			ele.sendKeys(value);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			ele.clear();
			ele.sendKeys(value);
		}
	}

	// Click, clear & type - same textbox
	public static <T> void clickClearAndType(String elementName, T elementAttr, String value) throws Exception {
		log("Click, clearing: "+elementName+" and sending text: '"+value+"'");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			ele.click();
			Thread.sleep(500);
			ele.clear();
			ele.sendKeys(value);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			ele.click();
			Thread.sleep(500);
			ele.clear();
			ele.sendKeys(value);
		}
	}

	// Click, clear & type - Hidden textbox
	public static <T> void clickClearAndType(String element1Name, T elementAttr1, String element2Name, T elementAttr2, String value) throws Exception {
		log("Clicking on: "+element1Name+", clearing: "+element2Name+" and sending '"+value+"'");
		if(elementAttr1.getClass().getName().contains("By") && elementAttr2.getClass().getName().contains("By")) {
			waitForElementPresent(element1Name, (By) elementAttr1);
			WebElement ele1 = getDriver().findElement((By)elementAttr1);
			WebElement ele2 = getDriver().findElement((By)elementAttr2);
			ele1.click();
			Thread.sleep(500);
			ele2.clear();
			ele2.sendKeys(value);
		} else {
			WebElement ele1 = ((WebElement)elementAttr1);
			WebElement ele2 = ((WebElement)elementAttr2);
			ele1.click();
			Thread.sleep(500);
			ele2.clear();
			ele2.sendKeys(value);
		}
	}

	// JavaScript - click, clear & type
	public static <T> void jsClickClearAndType(String elementName, T elementAttr, String value) throws Exception {
		log("Clicking on: "+elementName+", clearing: "+elementName+" and sending '"+value+"'");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Thread.sleep(500);
			ele.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Thread.sleep(500);
			ele.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele);
		}
	}

	// JavaScript - click, clear & type - Hidden textbox 
	public static <T> void jsClickClearAndType(String element1Name, T elementAttr1, String element2Name, T elementAttr2, String value) throws Exception {
		log("Clicking on: "+element1Name+", clearing: "+element2Name+" and sending '"+value+"'");
		if(elementAttr1.getClass().getName().contains("By") && elementAttr2.getClass().getName().contains("By")) {
			waitForElementPresent(element1Name, (By) elementAttr1);
			WebElement ele1 = getDriver().findElement((By)elementAttr1);
			WebElement ele2 = getDriver().findElement((By)elementAttr2);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele1);
			Thread.sleep(500);
			ele2.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele2);
		} else {
			WebElement ele1 = ((WebElement)elementAttr1);
			WebElement ele2 = ((WebElement)elementAttr2);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele1);
			Thread.sleep(500);
			ele2.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele2);
		}
	}

	// Actions click
	public static <T> void actionClick(String elementName, T elementAttr) {
		log("Clicking on: "+elementName);
		Actions act = new Actions(getDriver());
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			act.moveToElement(ele).click().build().perform();
		} else {
			WebElement ele = ((WebElement)elementAttr);
			act.moveToElement(ele).click().build().perform();
		}
	}

	// Actions click - click at part of element wrt xy coordinates
	public static <T> void actionClickAtPartOfElementWrtXY(String elementName, T elementAttr, int x, int y) {
		log("Clicking on: "+elementName);
		Actions action= new Actions(getDriver());
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			action.moveToElement(ele).moveByOffset(x, y).click().build().perform();
		} else {
			WebElement ele = ((WebElement)elementAttr);
			action.moveToElement(ele).moveByOffset(x, y).click().build().perform();
		}
	}

	// Stale element click
	public static <T> void staleElementClick(String elementName, T elementAttr) {
		for(int i=0; i<=3;i++){
			try{
				if(elementAttr.getClass().getName().contains("By")) {
					waitForElementPresent(elementName, (By) elementAttr);
					WebElement ele = getDriver().findElement((By)elementAttr);
					ele.click();
				} else {
					WebElement ele = ((WebElement)elementAttr);
					ele.click();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}

	// Click checkbox 
	public static <T> void checkbox_Checking(String elementName, T elementAttr) {
		log("Checking: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("Checkbox is already checked", true);
			} else {
				ele.click();
				Reporter.log("Checked the checkbox", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("Checkbox is already checked", true);
			} else {
				ele.click();
				Reporter.log("Checked the checkbox", true);
			}
		}
	}

	// Select radio button
	public static <T> void radiobutton_Select(String elementName, T elementAttr) {
		log("Checking: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("RadioButton is already checked", true);
			} else {
				ele.click();
				Reporter.log("Selected the Radiobutton", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("RadioButton is already checked", true);
			} else {
				ele.click();
				Reporter.log("Selected the Radiobutton", true);
			}
		}
	}

	// Uncheck radio button
	public static <T> void checkbox_Unchecking(String elementName, T elementAttr) {
		log("Unchecking: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Checkbox is unchecked", true);
			} else {
				Reporter.log("Checkbox is already unchecked", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Checkbox is unchecked", true);
			} else {
				Reporter.log("Checkbox is already unchecked", true);
			}
		}
	}

	// Deselect radio button
	public static <T> void radioButton_Deselect(String elementName, T elementAttr) {
		log("Deselecting: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Radio Button is deselected", true);
			} else {
				Reporter.log("Radio Button was already Deselected", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Radio Button is deselected", true);
			} else {
				Reporter.log("Radio Button was already Deselected", true);
			}
		}
	}

	// Drag & Drop - 1
	public static <T> void dragAndDrop(String fromElementName, T fromWebElementAttr, String toElementName,  T toWebElementAttr) {
		log("Dragging: "+fromElementName+" and dropping on: "+toElementName);
		Actions builder = new Actions(getDriver());
		if(fromWebElementAttr.getClass().getName().contains("By") && toWebElementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(fromElementName, (By) fromWebElementAttr);
			WebElement ele1 = getDriver().findElement((By)fromWebElementAttr);
			WebElement ele2 = getDriver().findElement((By)toWebElementAttr);
			builder.dragAndDrop(ele1, ele2).perform();
		} else {
			WebElement ele1 = ((WebElement)fromWebElementAttr);
			WebElement ele2 = ((WebElement)toWebElementAttr);
			builder.dragAndDrop(ele1, ele2).perform();
		}
	}

	// Drag & Drop 2
	public static <T> void dragAndDrop_Method2(String fromElementName, T fromWebElementAttr, String toElementName,  T toWebElementAttr) {
		log("Dragging: "+fromElementName+" and dropping on: "+toElementName);
		Actions builder = new Actions(getDriver());
		if(fromWebElementAttr.getClass().getName().contains("By") && toWebElementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(fromElementName, (By) fromWebElementAttr);
			WebElement ele1 = getDriver().findElement((By)fromWebElementAttr);
			WebElement ele2 = getDriver().findElement((By)toWebElementAttr);
			Action dragAndDrop = builder.clickAndHold(ele1).moveToElement(ele2).release(ele2).build();
			dragAndDrop.perform();
		} else {
			WebElement ele1 = ((WebElement)fromWebElementAttr);
			WebElement ele2 = ((WebElement)toWebElementAttr);
			Action dragAndDrop = builder.clickAndHold(ele1).moveToElement(ele2).release(ele2).build();
			dragAndDrop.perform();
		}
	}

	// Drag & drop 3
	public static <T> void dragAndDrop_Method3(String fromElementName, T fromWebElementAttr, String toElementName,  T toWebElementAttr) throws InterruptedException {
		log("Dragging: "+fromElementName+" and dropping on: "+toElementName);
		Actions builder = new Actions(getDriver());
		if(fromWebElementAttr.getClass().getName().contains("By") && toWebElementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(fromElementName, (By) fromWebElementAttr);
			WebElement ele1 = getDriver().findElement((By)fromWebElementAttr);
			WebElement ele2 = getDriver().findElement((By)toWebElementAttr);
			builder.clickAndHold(ele1).moveToElement(ele2).perform();
			Thread.sleep(2000);
			builder.release(ele2).build().perform();
		} else {
			WebElement ele1 = ((WebElement)fromWebElementAttr);
			WebElement ele2 = ((WebElement)toWebElementAttr);
			builder.clickAndHold(ele1).moveToElement(ele2).perform();
			Thread.sleep(2000);
			builder.release(ele2).build().perform();
		}
	}

	// Double click
	public static <T> void doubleClickWebelement(String elementName, T elementAttr)throws InterruptedException {
		log("Double clicking on: "+elementName);
		Actions builder = new Actions(getDriver());
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			builder.doubleClick(ele).perform();
			Thread.sleep(2000);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			builder.doubleClick(ele).perform();
			Thread.sleep(2000);
		}
	}

	// Select by visible text
	public static <T> void selectElementByVisibleText(String elementName, T elementAttr, String Name) {
		log("Selecting '"+Name+"' option from: "+elementName+" dropdown");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByVisibleText(Name);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByVisibleText(Name);
		}
	}

	// Select by value
	public static <T> void selectElementByValue(String elementName, T elementAttr, String value) {
		log("Selecting '"+value+"' option from: "+elementName+" dropdown");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByValue(value);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByValue(value);
		}
	}

	// Select by index
	public static <T> void selectElementByIndex(String elementName, T elementAttr, int index) {
		log("Selecting '"+index+"' option from: "+elementName+" dropdown");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByIndex(index);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByIndex(index);
		}
	}

	// Upload file
	public static <T> void uploadFile(String elementName, T browseButton, String filePath) throws Exception {
		log("Uploading file using: "+elementName);
		if(browseButton.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) browseButton);
			WebElement ele = getDriver().findElement((By)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			ele.click();
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		} else {
			WebElement ele = ((WebElement)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			ele.click();
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		}
	}

	// Upload file 2
	public static <T> void uploadFile2(String elementName, T browseButton, String filePath) throws Exception {
		log("Uploading file using: "+elementName);
		if(browseButton.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) browseButton);
			WebElement ele = getDriver().findElement((By)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		} else {
			WebElement ele = ((WebElement)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		}
	}

	// Scroll into view
	public static <T> void scrollIntoView(String elementName, T elementAttr) {
		log("Scrolling to: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			JavascriptExecutor js=(JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", ele);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			JavascriptExecutor js=(JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", ele);
		}
	}

	// Scroll page wrt xy coordinates
	public static void scrollPageWrtXY(int x, int y) {
		((JavascriptExecutor)getDriver()).executeScript("scroll(" +x+ "," +y+ ")");
	}

	// Check checkbox
	public static <T> void check(String elementName, T checkBox) {
		log("Checking: "+elementName);
		if(checkBox.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) checkBox);
			WebElement ele = getDriver().findElement((By)checkBox);
			if (!ele.isSelected())
				ele.click();
		} else {
			WebElement ele = ((WebElement)checkBox);
			if (!ele.isSelected())
				ele.click();
		}
	}	

	// Switch to new window
	public static void switchToNewWindow() {
		Set s = getDriver().getWindowHandles();
		Iterator itr = s.iterator();
		String w1 = (String) itr.next();
		String w2 = (String) itr.next();
		getDriver().switchTo().window(w2);
	}

	// Switch to new window 2
	public static void switchToNewWindow_new() {
		List<String> browserTabs = new ArrayList<String> (getDriver().getWindowHandles());
		getDriver().switchTo().window(browserTabs .get(1));
	}

	// Switch to old window
	public static void switchToOldWindow() {
		Set s = getDriver().getWindowHandles();
		Iterator itr = s.iterator();
		String w1 = (String) itr.next();
		String w2 = (String) itr.next();
		getDriver().switchTo().window(w1);
	}

	// Switch to parent window 
	public static void switchToParentWindow() {
		getDriver().switchTo().defaultContent();
	}

	// Wait for element to be clickable
	public static <T> void waitForElementToBeClickable(String elementName, T elementAttr) {
		log("Waiting for element to be clickable: "+elementName);
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		} else {
			WebElement ele = ((WebElement)elementAttr);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		}
	}

	// Wait for element to be visible
	public static <T> void waitForElementVisibility(String elementName, T elementAttr) {
		log("Waiting for element to be visibility: "+elementName);
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			wait.until(ExpectedConditions.visibilityOf(ele));
		} else {
			WebElement ele = ((WebElement)elementAttr);
			wait.until(ExpectedConditions.visibilityOf(ele));
		}
	}

	// Wait for alert present
	public static void waitForAlertPresent() {
		log("Waiting for alert present");
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	// Wait for list of elements visibility
	public static void waitForListOfElementsVisibility(List<WebElement> list) {
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOfAllElements(list));
	}

	// Set window size
	public static void setWindowSize(int Dimension1, int dimension2) {
		getDriver().manage().window().setSize(new Dimension(Dimension1, dimension2));
	}

	// Switch to tab
	public static <T> void moveToTab(String elementName, T elementAttr) {
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			ele.sendKeys(Keys.chord(Keys.ALT, Keys.TAB));
			ele.sendKeys(Keys.chord(Keys.CONTROL+"a", Keys.BACK_SPACE, Keys.ENTER));
		} else {
			WebElement ele = ((WebElement)elementAttr);
			ele.sendKeys(Keys.chord(Keys.ALT, Keys.TAB));
		}
	}

	// Keyboard events
	public static <T> void keyboardEvents(String elementName, T elementAttr, Keys key, String alphabet) {
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			ele.sendKeys(Keys.chord(key, alphabet));
		} else {
			WebElement ele = ((WebElement)elementAttr);
			ele.sendKeys(Keys.chord(key, alphabet));
		}
	}

	// Click multiple elements
	public static <T> void clickMultipleElements(String element1Name, T elementAttr1, String element2Name, T elementAttr2) {
		log("Clicking on: "+element1Name+" and "+element2Name);
		Actions act = new Actions(getDriver());
		if(elementAttr1.getClass().getName().contains("By") && elementAttr2.getClass().getName().contains("By")) {
			waitForElementPresent(element1Name, (By) elementAttr1);
			WebElement ele1 = getDriver().findElement((By)elementAttr1);
			WebElement ele2 = getDriver().findElement((By)elementAttr2);
			act.keyDown(Keys.CONTROL).click(ele1).click(ele2).keyUp(Keys.CONTROL).build().perform();
		} else {
			WebElement ele1 = ((WebElement)elementAttr1);
			WebElement ele2 = ((WebElement)elementAttr2);
			act.keyDown(Keys.CONTROL).click(ele1).click(ele2).keyUp(Keys.CONTROL).build().perform();
		}
	}

	// Hover to element
	public static <T> void hoverToElement(String elementName, T elementAttr) throws Exception {
		log("Hovering to: "+elementName);
		Actions act = new Actions(getDriver());
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			act.moveToElement(ele).build().perform();
			Thread.sleep(2000);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			act.moveToElement(ele).build().perform();
			Thread.sleep(2000);
		}
	}

	// Slider pointer drag & drop
	public static <T> void sliderDragAndDrap(T sliderBar, T slider, String dragChoicePercentage) {
		log("Setting slider to: "+dragChoicePercentage);
		Actions act = new Actions(getDriver());
		if(sliderBar.getClass().getName().contains("By") && slider.getClass().getName().contains("By")) {
			WebElement bar = getDriver().findElement((By)sliderBar);
			WebElement pointer = getDriver().findElement((By)slider);
			int size = bar.getSize().getWidth();
			Reporter.log("Size of slider bar in pixels is: "+size, true);
			// Using drag and drop
			int userOption = Integer.parseInt(dragChoicePercentage);
			act.dragAndDropBy(pointer, (userOption*size)/100, 0).release().build().perform();
			// Using click and Hold then drop
			//act.moveToElement(slider).clickAndHold().moveByOffset((dragChoice*size)/100, 0).release().build().perform();
		} else {
			WebElement bar = ((WebElement)sliderBar);
			WebElement pointer = ((WebElement)slider);
			int size = bar.getSize().getWidth();
			Reporter.log("Size of slider bar in pixels is: "+size, true);
			// Using drag and drop
			int userOption = Integer.parseInt(dragChoicePercentage);
			act.dragAndDropBy(pointer, (userOption*size)/100, 0).release().build().perform();
			// Using click and Hold then drop
			//act.moveToElement(slider).clickAndHold().moveByOffset((dragChoice*size)/100, 0).release().build().perform();
		}
	}

	// Returns tool tip of the element
	public static <T> String getToolTip(String elementName, T elementAttr)	{
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			String tooltip = ele.getAttribute("title");
			log("Tool tip of: "+elementName+" is: "+tooltip);
			return tooltip;
		} else {
			WebElement ele = ((WebElement)elementAttr);
			String tooltip = ele.getAttribute("title");
			log("Tool tip of: "+elementName+" is: "+tooltip);
			return tooltip;
		}
	}

	// Returns tool tip of the element
	public static <T> String getToolTip2(String elementName, T elementAttr, String attribute) {
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			String tooltip = ele.getAttribute(attribute);
			log("Tool tip of: "+elementName+" is: "+tooltip);
			return tooltip;
		} else {
			WebElement ele = ((WebElement)elementAttr);
			String tooltip = ele.getAttribute(attribute);
			log("Tool tip of: "+elementName+" is: "+tooltip);
			return tooltip;
		}
	}

	// Returns attribute value
	public static <T> String getAttributeValue(T elementAttr, String attribute) {
		if(elementAttr.getClass().getName().contains("By")) {
			WebElement ele = getDriver().findElement((By)elementAttr);
			String value = ele.getAttribute(attribute);
			return value;
		} else {
			WebElement ele = ((WebElement)elementAttr);
			String value = ele.getAttribute(attribute);
			return value;
		}
	}

	// Highlights the element before action
	public static <T> void highlightElement(T elementAttr) throws Exception {
		if(elementAttr.getClass().getName().contains("By")) {
			WebElement ele = getDriver().findElement((By)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].style.border='3px solid yellow'", ele); // highlight
			Thread.sleep(300); // delay between highlight and unhighlight 
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].style.border=''", ele); // unhighlight
		} else {
			WebElement ele = ((WebElement)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].style.border='3px solid yellow'", ele); // highlight
			Thread.sleep(300); // delay between highlight and unhighlight 
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].style.border=''", ele); // unhighlight
		}
	}

	// Returns first selected option
	public static <T> String getFirstSelectedOption(String elementName, T elementAttr) {
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			Select select = new Select(ele);
			WebElement option = select.getFirstSelectedOption();
			return option.getAttribute("text");
		} else {
			WebElement ele = ((WebElement)elementAttr);
			Select select = new Select(ele);
			WebElement option = select.getFirstSelectedOption();
			return option.getAttribute("text");
		}
	}

	// Click one check box from list
	public static void clickCheckboxFromList(String xpathOfElement, String valueToSelect) {
		List<WebElement> lst = getDriver().findElements(By.xpath(xpathOfElement));
		for (int i = 0; i < lst.size(); i++) {
			List<WebElement> dr = lst.get(i).findElements(By.tagName("label"));
			for (WebElement f : dr) {
				Reporter.log("value in the list : " + f.getText(),true);
				if (valueToSelect.equals(f.getText())) {
					f.click();
					break;
				}
			}
		}
	}

	// Download file
	public static <T> void downoadFile(String elementName, T downloadButton) throws Exception {
		log("Downloading file using: "+elementName);
		WebElement ele = null;
		if(downloadButton.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) downloadButton);
			ele = getDriver().findElement((By)downloadButton);
		} else {
			ele = ((WebElement)downloadButton);
		}
		ele.click();   
		Thread.sleep(2000);   
		Robot robot = new Robot();   
		//For clicking on the Ok button on the dialog box   
		robot.keyPress(KeyEvent.VK_ENTER);    
		robot.keyRelease(KeyEvent.VK_ENTER);    
		Thread.sleep(2000);   
		//For clicking on Ok button on the dialog box which appears(not necessary)   
		//while saving file in a specific location.   
		robot.keyPress(KeyEvent.VK_ENTER);    
		robot.keyRelease(KeyEvent.VK_ENTER);    
		Thread.sleep(2000);   
		//For navigating to Yes button,if a prompt says that the file already   
		//exists in the location   
		robot.keyPress(KeyEvent.VK_LEFT);    
		robot.keyRelease(KeyEvent.VK_LEFT);    
		Thread.sleep(2000);   
		//For clicking on Ok button   
		robot.keyPress(KeyEvent.VK_ENTER);    
		robot.keyRelease(KeyEvent.VK_ENTER);    
	}

	// Download file 2
	public static void downloadFile(String href, String fileName)	throws Exception {
		URL url = null;
		URLConnection con = null;
		int i;
		url = new URL(href);
		con = url.openConnection();
		File file = new File(".//OutputData//" + fileName);
		BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(file));
		while ((i = bis.read()) != -1) {
			bos.write(i);
		}
		bos.flush();
		bis.close();
	}

	public static void navigateToEveryLinkInPage() throws InterruptedException {
		List<WebElement> linksize = getDriver().findElements(By.tagName("a"));
		int linksCount = linksize.size();
		Reporter.log("Total no of links Available: " + linksCount, true);
		String[] links = new String[linksCount];
		Reporter.log("List of links Available: ", true);
		// print all the links from WebPage
		for (int i = 0; i < linksCount; i++) {
			links[i] = linksize.get(i).getAttribute("href");
			Reporter.log(linksize.get(i).getAttribute("href"), true);
		}
		// navigate to each Link on the WebPage
		for (int i = 0; i < linksCount; i++) {
			getDriver().navigate().to(links[i]);
			Thread.sleep(3000);
			Reporter.log(getDriver().getTitle(), true);
		}
	}

	// AjaxCall wait
	public static void waitForAjax(WebDriver driver) {
		new WebDriverWait(driver, 180).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				return (Boolean) js.executeScript("return jQuery.active == 0");
			}
		});
	}

	// Fluent wait
	@SuppressWarnings("deprecation")
	public static WebElement fluentWait(String elementname, By element, String waitType, long waitInSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
				.withTimeout(waitInSeconds, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		try{
			switch(waitType) 
			{
			case "elementToBeClickable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;

			case "visibilityOf":
				wait.until(ExpectedConditions.visibilityOf((WebElement) element));
				break;

			case "elementToBeSelected":
				wait.until(ExpectedConditions.elementToBeSelected(element));
				break;

			default:
				wait.until(ExpectedConditions.visibilityOf((WebElement) element));
				break;
			}
		}
		catch(TimeoutException e) {
			e.printStackTrace();
		}
		return (WebElement) element;	
	}

	@SuppressWarnings({ "deprecation"})
	public static WebElement fluentWait(By locator, int timeoutSeconds) {
		//FluentWait Decleration
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
				.withTimeout(timeoutSeconds, TimeUnit.SECONDS) //Set timeout
				.pollingEvery(100, TimeUnit.MILLISECONDS) //Set query/check/control interval
				.withMessage("Timeout occured!") //Set timeout message
				.ignoring(NoSuchElementException.class); //Ignore NoSuchElementException

		//Wait until timeout period and when an element is found, then return it.
		return wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return getDriver().findElement(locator);
			}
		});
		//This is lambda expression of below code block. It is only for JAVA 8
		//return wait.until((WebgetDriver() webgetDriver()) -> getDriver().findElement(locator));
	}

	// Returns all options from dropdown
	public static <T> List<WebElement> getAllOptionsFromTheDropdown(String elementName, T elementAttr) {
		log("Getting all options from dropdown: "+elementName);
		WebElement ele = null;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			ele = getDriver().findElement((By)elementAttr);
		} else {
			ele = ((WebElement)elementAttr);
		}
		Select s = new Select(ele);
		List<WebElement> options = s.getOptions();
		return options;
	}

	// Returns current time
	public static long getTime() {
		return System.currentTimeMillis();
	}

	// Returns popup windows 
	public static String getWindowHandle() {
		return getDriver().getWindowHandle();
	}

	// Returns set of windows
	public static Set<String> getWindowHandles() {
		return getDriver().getWindowHandles();
	}

	// Scroll to bottom of the page
	public static void scrollBottom() {
		JavascriptExecutor js = ((JavascriptExecutor) getDriver());
		js.executeScript("window.scrollTo(0,document.body.scrollHeight); return true");
		try { Thread.sleep(1000); }catch(Exception e){}
	}

	// Scroll down the page as per the given number
	public static void scrollBottomByChoice(int noOfTimes) throws Exception {
		for (int i = 1; i <= noOfTimes; i++) {
			scrollBottom();
			Thread.sleep(1000);
		}
	}

	// Scroll to top of page
	public static void scrollTop() {
		JavascriptExecutor js = ((JavascriptExecutor) getDriver());
		js.executeScript("window.scrollTo(document.body.scrollHeight,0); return true");
		try {Thread.sleep(1000); }catch(Exception e){}
	}

	// Scroll to center of the page
	public static void scrollCenter() {
		JavascriptExecutor js = ((JavascriptExecutor) getDriver());
		js.executeScript("window.scrollTo(0,document.body.scrollHeight/2); return true");
		try { Thread.sleep(1000); }catch(Exception e){}
	}

	// Scroll page wrt xy coordinates
	public static void scrollPage(int xValue, int yValue) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("window.scrollBy(" + xValue + "," + yValue + ")", "");
		try { Thread.sleep(1000); }catch(Exception e){}
	}

	// Select from given list of option
	public static void selectFromListBox(List<WebElement> elementList, String selection) {
		try {
			for (WebElement element : elementList) {
				if (element.getText().trim().equalsIgnoreCase(selection)) {
					element.click();
					break;
				}
			}
		} catch (Exception e) {
			Reporter.log("Error : [" + selection + "] is not present in the list box or list box contains no value : "
					+ e.getLocalizedMessage(), true);
		}
	}

	// Adjust Browser Zoom
	public static void adjustBrowserZoom(int zoom) {
		JavascriptExecutor jse = (JavascriptExecutor)getDriver();
		jse.executeScript("document.body.style.zoom = '"+zoom+"%';");
	}

	// Ajax Utils================================================================================
	public static <T> void ajaxJsClick(String elementName, T elementAttr){
		log("Clicking on: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxJsType(String elementName, T elementAttr, String value){
		log("Sending '"+value+"' to: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'", ele);
			ele.sendKeys(Keys.TAB);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'", ele);
			ele.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxSendKeys(String elementName, T elementAttr, int timeout, String value) {
		log("Sending '"+value+"' to: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOf(ele));
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOf(ele));
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxType(String elementName, T elementAttr, String value) {
		log("Sending '"+value+"' to: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxClearAndType(String elementName, T elementAttr, String value) throws Exception {
		log("Clear & Sending '"+value+"' to: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			ele.clear();
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			ele.clear();
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxClickClearAndType(String element1Name, T elementAttr1, String element2Name, T elementAttr2, String value) throws Exception {
		log("Clicking on: "+element1Name+", clearing: "+element2Name+" and sending '"+value+"'");
		if(elementAttr1.getClass().getName().contains("By") && elementAttr2.getClass().getName().contains("By")) {
			waitForElementPresent(element1Name, (By) elementAttr1);
			WebElement ele1 = getDriver().findElement((By)elementAttr1);
			WebElement ele2 = getDriver().findElement((By)elementAttr2);
			ele1.click();
			Thread.sleep(500);
			ele2.clear();
			ele2.sendKeys(value);
			ele2.sendKeys(Keys.TAB);
		} else {
			WebElement ele1 = ((WebElement)elementAttr1);
			WebElement ele2 = ((WebElement)elementAttr2);
			ele1.click();
			Thread.sleep(500);
			ele2.clear();
			ele2.sendKeys(value);
			ele2.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	// Click, select inner text & type - Hidden textbox
	public static <T> void ajaxClickSelectAndType(String element1Name, T eleAttr1, String element2Name, T eleAttr2, String value) throws Exception {
		log("Clicking on: "+element1Name+", clearing: "+element2Name+" and sending '"+value+"'");
		if(eleAttr1.getClass().getName().contains("By") && eleAttr2.getClass().getName().contains("By")) {
			WebElement ele1 = getDriver().findElement((By)eleAttr1);
			WebElement ele2 = getDriver().findElement((By)eleAttr2);
			ele1.click();
			ele2.sendKeys(Keys.CONTROL+"a");
			ele2.sendKeys(value);
			ele2.sendKeys(Keys.TAB);
		} else {
			WebElement ele1 = ((WebElement)eleAttr1);
			WebElement ele2 = ((WebElement)eleAttr2);
			ele1.click();
			ele2.sendKeys(Keys.CONTROL+"a");
			ele2.sendKeys(value);
			ele2.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxClickClearAndType(String elementName, T elementAttr, String value) throws Exception {
		log("Click, clearing: "+elementName+" and sending text: '"+value+"'");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			ele.click();
			Thread.sleep(500);
			ele.clear();
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			ele.click();
			Thread.sleep(500);
			ele.clear();
			ele.sendKeys(value);
			ele.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxJsClickClearAndType(String element1Name, T elementAttr1, String element2Name, T elementAttr2, String value) throws Exception {
		log("Clicking on: "+element1Name+", clearing: "+element2Name+" and sending '"+value+"'");
		if(elementAttr1.getClass().getName().contains("By") && elementAttr2.getClass().getName().contains("By")) {
			waitForElementPresent(element1Name, (By) elementAttr1);
			WebElement ele1 = getDriver().findElement((By)elementAttr1);
			WebElement ele2 = getDriver().findElement((By)elementAttr2);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele1);
			Thread.sleep(500);
			ele2.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele2);
			ele2.sendKeys(Keys.TAB);
		} else {
			WebElement ele1 = ((WebElement)elementAttr1);
			WebElement ele2 = ((WebElement)elementAttr2);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele1);
			Thread.sleep(500);
			ele2.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele2);
			ele2.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	// JavaScript - click, select inner text & type - Hidden textbox 
	public static <T> void ajaxJsClickSelectAndType(T eleAttr1, T eleAttr2, String value) throws Exception {
		if(eleAttr1.getClass().getName().contains("By") && eleAttr2.getClass().getName().contains("By")) {
			WebElement ele1 = getDriver().findElement((By)eleAttr1);
			WebElement ele2 = getDriver().findElement((By)eleAttr2);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele1);
			ele2.sendKeys(Keys.CONTROL+"a");
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele2);
			ele2.sendKeys(Keys.TAB);
		} else {
			WebElement ele1 = ((WebElement)eleAttr1);
			WebElement ele2 = ((WebElement)eleAttr2);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele1);
			ele2.sendKeys(Keys.CONTROL+"a");
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele2);
			ele2.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxJsClickClearAndType(String elementName, T elementAttr, String value) throws Exception {
		log("Clicking & clearing: "+elementName+" and sending '"+value+"'");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Thread.sleep(500);
			ele.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele);
			ele.sendKeys(Keys.TAB);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Thread.sleep(500);
			ele.clear();
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].value='"+value+"'",ele);
			ele.sendKeys(Keys.TAB);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxClick(String elementName, T elementAttr, int timeout) {
		if (elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			new WebDriverWait(getDriver(), timeout).
			until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
		} else {
			WebElement ele = ((WebElement)elementAttr);
			new WebDriverWait(getDriver(), timeout).
			until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxActionClick(String elementName, T elementAttr) {
		log("Clicking on: "+elementName);
		Actions act = new Actions(getDriver());
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			act.moveToElement(ele).click().build().perform();
		} else {
			WebElement ele = ((WebElement)elementAttr);
			act.moveToElement(ele).click().build().perform();
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxActionClickAtPartOfElementWrtXY(String elementName, T elementAttr, int x, int y) {
		log("Clicking on: "+elementName);
		Actions action= new Actions(getDriver());
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			action.moveToElement(ele).moveByOffset(x, y).click().build().perform();
		} else {
			WebElement ele = ((WebElement)elementAttr);
			action.moveToElement(ele).moveByOffset(x, y).click().build().perform();
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxStaleElementClick(String elementName, T elementAttr) {
		log("Clicking on: "+elementName);
		for(int i=0; i<=3;i++){
			try{
				if(elementAttr.getClass().getName().contains("By")) {
					waitForElementPresent(elementName, (By) elementAttr);
					WebElement ele = getDriver().findElement((By)elementAttr);
					ele.click();
				} else {
					WebElement ele = ((WebElement)elementAttr);
					ele.click();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxClickMultipleElements(String element1Name, T someElement, String elemet2Name, T someOtherElement) {
		log("Clicking on: "+element1Name+" and "+elemet2Name);
		Actions builder = new Actions(getDriver());
		if(someElement.getClass().getName().contains("By") && someOtherElement.getClass().getName().contains("By")) {
			waitForElementPresent(element1Name, (By) someElement);
			WebElement ele1 = getDriver().findElement((By)someElement);
			WebElement ele2 = getDriver().findElement((By)someOtherElement);
			builder.keyDown(Keys.CONTROL).click(ele1)
			.click(ele2).keyUp(Keys.CONTROL).build().perform();
		} else {
			WebElement ele1 = ((WebElement)someElement);
			WebElement ele2 = ((WebElement)someOtherElement);
			builder.keyDown(Keys.CONTROL).click(ele1)
			.click(ele2).keyUp(Keys.CONTROL).build().perform();
		}
		waitForAjax(getDriver());
	}

	public static boolean ajaxCheckAlert_Accept() {
		try {
			Alert a = getDriver().switchTo().alert();
			String str = a.getText();
			Reporter.log("Alert message is: "+str, true);
			a.accept();
			waitForAjax(getDriver());
			return true;
		} catch (Exception e) {
			Reporter.log("No alert present", true);
			return false;
		}
	}

	public static boolean ajaxCheckAlert_Dismiss() {
		try {
			Alert a = getDriver().switchTo().alert();
			String str = a.getText();
			Reporter.log("Alert message is: "+str, true);
			a.dismiss();
			waitForAjax(getDriver());
			return true;
		} catch (Exception e) {
			Reporter.log("No alert present", true);
			return false;
		}
	}	

	public static <T> void ajaxCheckbox_Checking(String elementName, T elementAttr) {
		log("Checking: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("Checkbox is already checked", true);
			} else {
				ele.click();
				Reporter.log("Checked the checkbox", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("Checkbox is already checked", true);
			} else {
				ele.click();
				Reporter.log("Checked the checkbox", true);
			}
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxRadiobutton_Select(String elementName, T elementAttr) {
		log("Checking: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("RadioButton is already checked", true);
			} else {
				ele.click();
				Reporter.log("Selected the Radiobutton", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				Reporter.log("RadioButton is already checked", true);
			} else {
				ele.click();
				Reporter.log("Selected the Radiobutton", true);
			}
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxCheckbox_Unchecking(String elementName, T elementAttr) {
		log("Unchecking: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Checkbox is unchecked", true);
			} else {
				Reporter.log("Checkbox is already unchecked", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Checkbox is unchecked", true);
			} else {
				Reporter.log("Checkbox is already unchecked", true);
			}
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxRadioButton_Deselect(String elementName, T elementAttr) {
		log("Deselecting: "+elementName);
		boolean checkstatus;
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Radio Button is deselected", true);
			} else {
				Reporter.log("Radio Button was already Deselected", true);
			}
		} else {
			WebElement ele = ((WebElement)elementAttr);
			checkstatus = ele.isSelected();
			if (checkstatus == true) {
				ele.click();
				Reporter.log("Radio Button is deselected", true);
			} else {
				Reporter.log("Radio Button was already Deselected", true);
			}
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxDragAndDrop(String fromElementName, T fromWebElementAttr, String toElementName, T toWebElementAttr) {
		log("Dragging: "+fromElementName+" and dropping on: "+toElementName);
		Actions builder = new Actions(getDriver());
		if(fromWebElementAttr.getClass().getName().contains("By") && toWebElementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(fromElementName, (By) fromWebElementAttr);
			WebElement ele1 = getDriver().findElement((By)fromWebElementAttr);
			WebElement ele2 = getDriver().findElement((By)toWebElementAttr);
			builder.dragAndDrop(ele1, ele2).perform();
		} else {
			WebElement ele1 = ((WebElement)fromWebElementAttr);
			WebElement ele2 = ((WebElement)toWebElementAttr);
			builder.dragAndDrop(ele1, ele2).perform();
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxDragAndDrop_Method2(String fromElementName, T fromWebElementAttr, String toElementName, T toWebElementAttr) {
		log("Dragging: "+fromElementName+" and dropping on: "+toElementName);
		Actions builder = new Actions(getDriver());
		if(fromWebElementAttr.getClass().getName().contains("By") && toWebElementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(fromElementName, (By) fromWebElementAttr);
			WebElement ele1 = getDriver().findElement((By)fromWebElementAttr);
			WebElement ele2 = getDriver().findElement((By)toWebElementAttr);
			Action dragAndDrop = builder.clickAndHold(ele1).moveToElement(ele2).release(ele2).build();
			dragAndDrop.perform();
		} else {
			WebElement ele1 = ((WebElement)fromWebElementAttr);
			WebElement ele2 = ((WebElement)toWebElementAttr);
			Action dragAndDrop = builder.clickAndHold(ele1).moveToElement(ele2).release(ele2).build();
			dragAndDrop.perform();
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxDragAndDrop_Method3(String fromElementName, T fromWebElementAttr, String toElementName, T toWebElementAttr) throws InterruptedException {
		log("Dragging: "+fromElementName+" and dropping on: "+toElementName);
		Actions builder = new Actions(getDriver());
		if(fromWebElementAttr.getClass().getName().contains("By") && toWebElementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(fromElementName, (By) fromWebElementAttr);
			WebElement ele1 = getDriver().findElement((By)fromWebElementAttr);
			WebElement ele2 = getDriver().findElement((By)toWebElementAttr);
			builder.clickAndHold(ele1).moveToElement(ele2).perform();
			Thread.sleep(2000);
			builder.release(ele2).build().perform();
		} else {
			WebElement ele1 = ((WebElement)fromWebElementAttr);
			WebElement ele2 = ((WebElement)toWebElementAttr);
			builder.clickAndHold(ele1).moveToElement(ele2).perform();
			Thread.sleep(2000);
			builder.release(ele2).build().perform();
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxDoubleClickWebelement(String elementName, T elementAttr)throws InterruptedException {
		log("Double clicking on: "+elementName);
		Actions builder = new Actions(getDriver());
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			builder.doubleClick(ele).perform();
			Thread.sleep(2000);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			builder.doubleClick(ele).perform();
			Thread.sleep(2000);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxSelectElementByVisibleText(String elementName, T elementAttr, String Name) {
		log("Selecting '"+Name+"' option from: "+elementName+" dropdown");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByVisibleText(Name);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByVisibleText(Name);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxSelectElementByValue(String elementName, T elementAttr, String value) {
		log("Selecting '"+value+"' option from: "+elementName+" dropdown");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByValue(value);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByValue(value);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxSelectElementByIndex(String elementName, T elementAttr, int index) {
		log("Selecting '"+index+"' option from: "+elementName+" dropdown");
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByIndex(index);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			Select selectitem = new Select(ele);
			selectitem.selectByIndex(index);
		}
		waitForAjax(getDriver());
	}

	public static void ajaxClickCheckboxFromList(String xpathOfElement, String valueToSelect) {
		List<WebElement> lst = getDriver().findElements(By.xpath(xpathOfElement));
		for (int i = 0; i < lst.size(); i++) {
			List<WebElement> dr = lst.get(i).findElements(By.tagName("label"));
			for (WebElement f : dr) {
				Reporter.log("value in the list : " + f.getText(),true);
				if (valueToSelect.equals(f.getText())) {
					f.click();
					waitForAjax(getDriver());
					break;
				}
			}
		}
	}

	public static <T> void ajaxUploadFile(String elementName, T browseButton, String filePath) throws Exception {
		log("Uploading file using: "+elementName);
		if(browseButton.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) browseButton);
			WebElement ele = getDriver().findElement((By)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			ele.click();
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		} else {
			WebElement ele = ((WebElement)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			ele.click();
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxUploadFile2(String elementName, T browseButton, String filePath) throws Exception {
		log("Uploading file using: "+elementName);
		if(browseButton.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) browseButton);
			WebElement ele = getDriver().findElement((By)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		} else {
			WebElement ele = ((WebElement)browseButton);
			StringSelection sel2 = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel2,null);
			Reporter.log("Selection: "+sel2, true);
			Thread.sleep(1000);
			((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", ele);
			Reporter.log("Browse button clicked", true);
			Robot robot2 = new Robot();
			Thread.sleep(2000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			robot2.keyPress(KeyEvent.VK_CONTROL);
			robot2.keyPress(KeyEvent.VK_V);
			robot2.keyRelease(KeyEvent.VK_CONTROL);
			robot2.keyRelease(KeyEvent.VK_V);
			Thread.sleep(1000);
			robot2.keyPress(KeyEvent.VK_ENTER);
			robot2.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
		}
		waitForAjax(getDriver());
	}

	public static <T> void ajaxScrollIntoView(String elementName, T elementAttr) {
		log("Scrolling to: "+elementName);
		if(elementAttr.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) elementAttr);
			WebElement ele = getDriver().findElement((By)elementAttr);
			JavascriptExecutor js=(JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", ele);
		} else {
			WebElement ele = ((WebElement)elementAttr);
			JavascriptExecutor js=(JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", ele);
		}
		waitForAjax(getDriver());
	}

	public static void ajaxScrollPageWrtXY(int x, int y) {
		((JavascriptExecutor)getDriver()).executeScript("scroll(" +x+ "," +y+ ")");
		waitForAjax(getDriver());
	}

	public static <T> void ajaxCheck(String elementName, T checkBox) {
		log("Checking: "+elementName);
		if(checkBox.getClass().getName().contains("By")) {
			waitForElementPresent(elementName, (By) checkBox);
			WebElement ele = getDriver().findElement((By)checkBox);
			if (!ele.isSelected())
				ele.click();
		} else {
			WebElement ele = ((WebElement)checkBox);
			if (!ele.isSelected())
				ele.click();
		}
		waitForAjax(getDriver());
	}	

	//================ Wrapper Methods =================

	public static int getRandomIntegerValue(int min, int max) throws Exception {
		if (min > max) {
			min = min + max;
			max = min - max;
			min = min - max;
		}
		Random random = new Random();
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static int getDifferenceInYearsBetweenTwoDates(String fromDate, String pastDate) throws Exception {
		int differenceInYears = 0;
		String[] fromDateArray = fromDate.split("[-]");
		String[] toDateArray = pastDate.split("[-]");
		int fromYear = Integer.parseInt(fromDateArray[0]);
		int fromMonth = Integer.parseInt(fromDateArray[1]);
		int fromDay = Integer.parseInt(fromDateArray[2]);
		int pastYear = Integer.parseInt(toDateArray[0]);
		int pastMonth = Integer.parseInt(toDateArray[1]);
		int pastDay = Integer.parseInt(toDateArray[2]);
		if (fromMonth == pastMonth) {
			if (fromDay <= pastDay)
				differenceInYears--;
		} else if (fromMonth < pastMonth) {
			differenceInYears--;
		}
		differenceInYears = differenceInYears + fromYear - pastYear;
		return differenceInYears;
	}

	public static HashSet<Integer> getRandomNumbersInGivenRange(int randomNumbersSizeToSelect, int min, int max)
			throws Exception {
		HashSet<Integer> randomNumbers = new HashSet<Integer>();
		int i = 0;
		while (true) {
			int randomNumber = getRandomIntegerValue(min, max);
			randomNumbers.add(randomNumber);
			if (randomNumbers.size() == randomNumbersSizeToSelect)
				break;
			i++;
			if (i > 10000)
				break;
		}
		return randomNumbers;

	}

	public static String getDateInRequiredFormat(String dateParam, String actualFormat, String requiredFormat)
			throws Exception {
		Date date = new SimpleDateFormat(actualFormat).parse(dateParam);
		SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat);
		return formatter.format(date);
	}

	public static String getNextHourTime(String time) throws Exception {
		Date date = new SimpleDateFormat("h:mm a").parse(time);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, 1);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
		return formatter.format(date);
	}

	public static String getDifferenceBetweenTwoTimes(String fromTime, String tillTime) throws Exception {
		String timeDifference = "";
		Date d1 = new SimpleDateFormat("h:mm a").parse(fromTime);
		Date d2 = new SimpleDateFormat("h:mm a").parse(tillTime);
		long differenceInMilliSeconds = d2.getTime() - d1.getTime();
		long differenceInMinutes = differenceInMilliSeconds / (60 * 1000);
		long hours = differenceInMinutes / 60;
		long minutes = differenceInMinutes - hours * 60;
		if (hours == 0)
			timeDifference = String.valueOf(minutes) + " min";
		else if (minutes == 0)
			timeDifference = String.valueOf(hours) + " hour";
		else
			timeDifference = String.valueOf(hours) + " hour" + " " + String.valueOf(minutes) + " min";
		return timeDifference;
	}

	public static String getRandomTimeOfTheDay() throws Exception {
		String minute = "";
		String period = "";
		int randomHour = getRandomIntegerValue(1, 12);
		int randomMinute = getRandomIntegerValue(1, 59);
		int randomPeriod = getRandomIntegerValue(0, 1);
		if (String.valueOf(randomMinute).length() == 1)
			minute = "0" + randomMinute;
		else
			minute = String.valueOf(randomMinute);
		if (randomPeriod == 0)
			period = "AM";
		else
			period = "PM";
		return String.valueOf(randomHour) + ":" + minute + " " + period;
	}

	public static String getNextValidTimeOfTheDay(String time) throws Exception {
		String minutes = "";
		int currentHour = Integer.parseInt(time.split(":")[0]);
		int currentMinutes = Integer.parseInt(time.split(":")[1].split(" ")[0]);
		if (time.contains("PM")) {
			if (currentHour == 12)
				currentHour = 11;
			int randomNextHour = getRandomIntegerValue(currentHour, 11);
			int randomNextMinute = getRandomIntegerValue(currentMinutes + 1, 59);
			minutes = String.valueOf(randomNextMinute);
			if (String.valueOf(randomNextMinute).length() == 1)
				minutes = "0" + String.valueOf(randomNextMinute);
			time = randomNextHour + ":" + minutes + " PM";
		}
		if (time.contains("AM")) {
			int randomNextHour = getRandomIntegerValue(currentHour, 24);
			int randomNextMinute = getRandomIntegerValue(currentMinutes + 1, 59);
			minutes = String.valueOf(randomNextMinute);
			if (String.valueOf(randomNextMinute).length() == 1)
				minutes = "0" + String.valueOf(randomNextMinute);
			time = randomNextHour + ":" + minutes + " AM";
			if (randomNextHour > 12) {
				randomNextHour = randomNextHour - 12;
				time = randomNextHour + ":" + minutes + " PM";
			} else if (randomNextHour == 12) {
				time = randomNextHour + ":" + minutes + " PM";
			}
		}
		return time;
	}

	public static String getCookieValue(String cookieName) throws Exception {
		String cookie = getDriver().manage().getCookieNamed(cookieName).toString();
		return cookie;
	}

	public static String getTime(int hour, int minutes) throws Exception {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
		c.add(Calendar.MINUTE, minutes);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
		return formatter.format(date);
	}

	public static Date convertStringToDate(String dateString, String dateFormat) throws Exception {
		DateFormat format = new SimpleDateFormat(dateFormat);
		Date date = format.parse(dateString);
		return date;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDatesWithRepeatsEveryYearCondition(String startDate,
			String endDate, String repeatsEveryMonth) throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		listOfDates.add(startDate);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		while (true) {
			startDate = getNextDateFromGivenDateForAppointmentAndReminder(0, 0, Integer.parseInt(repeatsEveryMonth),
					startDate, calendarTimeFormat);
			Date date = convertStringToDate(startDate, calendarTimeFormat);
			if (date.after(end))
				break;
			listOfDates.add(startDate);
		}
		return listOfDates;
	}

	public static String getStartDateOfTheWeek(String givenDate) throws Exception {
		String startDate = "";
		if (givenDate.contains("Sun"))
			return givenDate;
		int i = -1;
		while (true) {
			String date = getNextDateFromGivenDateForAppointmentAndReminder(i, 0, 0, givenDate, calendarTimeFormat);
			if (date.contains("Sun")) {
				startDate = date;
				break;
			}
			i--;
		}

		return startDate;
	}

	public static ArrayList<String> getAllRequiredDaysInAWeek(String startDate, String weekDate, String endDate,
			String repeatingDays) throws Exception {
		ArrayList<String> listOfDatesInAWeek = new ArrayList<String>();
		String startDateOfWeek = getStartDateOfTheWeek(weekDate);
		Date startDATE = convertStringToDate(startDate, calendarTimeFormat);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		String currentDate = getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, calendarTimeFormat);
		Date todayDATE = convertStringToDate(currentDate, calendarTimeFormat);
		int i = 0;
		boolean haltWhile = false;
		while (true) {
			String nextDate = getNextDateFromGivenDateForAppointmentAndReminder(i, 0, 0, startDateOfWeek,
					calendarTimeFormat);
			for (String repeatingDay : repeatingDays.split(",")) {

				Date date = convertStringToDate(nextDate, calendarTimeFormat);
				if (date.after(end)) {
					haltWhile = true;
					break;
				}
				if (date.before(todayDATE))
					continue;

				if (date.before(startDATE))
					continue;

				if (nextDate.contains(repeatingDay.trim()))
					listOfDatesInAWeek.add(nextDate);

			}
			if (nextDate.contains("Sat")) {
				haltWhile = true;
				break;
			}
			i++;
			if (haltWhile)
				break;
		}

		return listOfDatesInAWeek;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDatesWithRepeatsEveryWeekCondition(String startDate,
			String endDate, String repeatsEveryWeek, String repeatingDays) throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		ArrayList<String> listOfDatesInAWeek = getAllRequiredDaysInAWeek(startDate, startDate, endDate, repeatingDays);
		listOfDates.addAll(listOfDatesInAWeek);
		int weekDays = 7 * Integer.parseInt(repeatsEveryWeek);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		String weekDate = startDate;
		while (true) {
			weekDate = getNextDateFromGivenDateForAppointmentAndReminder(weekDays, 0, 0, weekDate, calendarTimeFormat);
			listOfDatesInAWeek = getAllRequiredDaysInAWeek(startDate, weekDate, endDate, repeatingDays);
			listOfDates.addAll(listOfDatesInAWeek);
			Date date = convertStringToDate(weekDate, calendarTimeFormat);
			if (date.after(end))
				break;
		}
		return listOfDates;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDatesWithRepeatsEveryMonthCondition(String startDate,
			String endDate, String repeatsEveryMonth) throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		listOfDates.add(startDate);
		Date end = convertStringToDate(endDate, calendarTimeFormat);
		while (true) {
			startDate = getNextDateFromGivenDateForAppointmentAndReminder(0, Integer.parseInt(repeatsEveryMonth), 0,
					startDate, calendarTimeFormat);
			Date date = convertStringToDate(startDate, calendarTimeFormat);
			if (date.after(end))
				break;
			listOfDates.add(startDate);
		}
		return listOfDates;
	}

	public static ArrayList<String> getListOfDatesBetweenTheGivenDates(String startDate, String endDate)
			throws Exception {
		ArrayList<String> listOfDates = new ArrayList<String>();
		int i = 0;
		while (true) {
			String date = getNextDateFromGivenDateForAppointmentAndReminder(i, 0, 0, startDate, calendarTimeFormat);
			listOfDates.add(date);
			if (date.equals(endDate))
				break;
			i++;
		}
		return listOfDates;
	}

	public static String getNextDateFromGivenDateForAppointmentAndReminder(int plusDay, int plusMonth, int plusYear,
			String currentDate, String requiredFormat) throws Exception {
		Date givenDate = convertStringToDate(currentDate, calendarTimeFormat);
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		c.add(Calendar.DATE, plusDay);
		c.add(Calendar.MONTH, plusMonth);
		c.add(Calendar.YEAR, plusYear);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat);
		return formatter.format(date);
	}

	public static String getCurrentDateInRequiredFormatForAppointmentAndReminder(int plusDay, int plusMonth,
			int plusYear, String requiredFormat) throws Exception {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, plusDay);
		c.add(Calendar.MONTH, plusMonth);
		c.add(Calendar.YEAR, plusYear);
		date = c.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(requiredFormat);
		return formatter.format(date);
	}

	public static String getRandomDateBetweenGivenRange(int start, int end, boolean onlyPast) throws Exception {
		Faker faker = new Faker();
		SimpleDateFormat df = new SimpleDateFormat(calendarTimeFormat);
		Date date = faker.date().birthday(start, end);
		Date currentDate = convertStringToDate(
				getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, calendarTimeFormat),
				calendarTimeFormat);
		if (onlyPast) {
			while (date.after(currentDate)) {
				date = faker.date().birthday(start, end);
			}
		}
		return df.format(date);
	}

	public static String getRandomDateOfBirth() throws Exception {
		Faker faker = new Faker();
		SimpleDateFormat df = new SimpleDateFormat(calendarTimeFormat);
		Date date = faker.date().birthday(1, 10);
		return df.format(date);
	}

	public static String getMonthAndYear(int monthPeriod, int yearPeriod) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("MMM yyyy");
		int month = Calendar.getInstance().get(Calendar.MONTH); // month is 0 based on calendar
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.set(MONTH, month + monthPeriod);
		calendar.set(YEAR, year + yearPeriod);
		return df.format(calendar.getTime());
	}

	public static ArrayList<Integer> getCurrentCalendarDisabledDayDates(ArrayList<Integer> currentMonthDates)
			throws Exception {
		ArrayList<Integer> blockedDates = new ArrayList<Integer>();
		for (int date : currentMonthDates) {
			if (date != 1) {
				blockedDates.add(date);
			} else
				break;
		}
		int j = 0;
		List<Integer> sublist = null;
		for (int date : currentMonthDates) {
			if (j > 20) {
				if (date == 1) {
					sublist = currentMonthDates.subList(j, currentMonthDates.size());
				}
			}
			j++;
		}
		for (int i : sublist) {
			blockedDates.add(i);
		}
		return blockedDates;
	}

	public static ArrayList<Integer> getPreviousCalendarMonthPastDates() throws Exception {
		ArrayList<Integer> currentMonthPastDates = new ArrayList<Integer>();
		String currentDateString = getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, "d");
		int currentDate = Integer.parseInt(currentDateString);
		ArrayList<Integer> currentMonthDates = getCalendarMonthDayDates(0);
		int count = 0;
		for (int i : currentMonthDates) {
			if (i == currentDate && count > 20)
				break;
			else
				currentMonthPastDates.add(i);
			count++;
		}
		return currentMonthPastDates;
	}

	public static ArrayList<Integer> getCurrentCalendarMonthPastDates() throws Exception {
		ArrayList<Integer> currentMonthPastDates = new ArrayList<Integer>();
		String currentDateString = getCurrentDateInRequiredFormatForAppointmentAndReminder(0, 0, 0, "d");
		int currentDate = Integer.parseInt(currentDateString);
		if (currentDate != 1) {
			ArrayList<Integer> currentMonthDates = getCalendarMonthDayDates(1);
			for (int i : currentMonthDates) {
				if (i == currentDate)
					break;
				else
					currentMonthPastDates.add(i);
			}
		}
		return currentMonthPastDates;
	}

	public static ArrayList<Integer> getCalendarMonthDayDates(int requiredMonth) throws Exception {
		ArrayList<Integer> currentMonthDates = new ArrayList<Integer>();
		int count = 0;
		boolean condition = true;
		while (true) {
			currentMonthDates = new ArrayList<Integer>();
			SimpleDateFormat df = new SimpleDateFormat("d");
			int month = Calendar.getInstance().get(Calendar.MONTH) + requiredMonth; // month is 0 based on calendar
			int year = Calendar.getInstance().get(Calendar.YEAR);

			Calendar start = Calendar.getInstance();
			start.set(MONTH, month - 1); // month is 0 based on calendar
			start.set(YEAR, year);
			start.set(DAY_OF_MONTH, 1);
			start.getTime();
			start.set(DAY_OF_WEEK, SUNDAY);

			Calendar end = Calendar.getInstance();
			end.set(MONTH, month); // next month
			end.set(YEAR, year);
			end.set(DAY_OF_MONTH, 1);
			end.getTime();
			end.set(DATE, -1);
			end.set(DAY_OF_WEEK, SATURDAY);
			start.getTime();
			if (end.get(MONTH) != month)
				end.add(DATE, +7);
			if (!condition)
				end.add(DATE, +7);
			while (start.before(end)) {
				String date = df.format(start.getTime());
				currentMonthDates.add(Integer.parseInt(date));
				start.add(DATE, 1);
			}
			String date = df.format(start.getTime());
			currentMonthDates.add(Integer.parseInt(date));
			if (currentMonthDates.size() == 42) {
				break;
			} else {
				if (currentMonthDates.size() > 42) {
					condition = true;
				} else {
					condition = false;
				}
			}

			count++;

			if (count > 10)
				break;
		}
		return currentMonthDates;
	}

	public static String getcurrentDayDate(String dayFormat) throws Exception {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dayFormat);
		return formatter.format(date);
	}

	public static String[] getCurrentWeekDates() throws Exception {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String[] daysOfWeek = new String[7];
		for (int i = 0; i < 7; i++) {
			daysOfWeek[i] = sdf.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return daysOfWeek;
	}

	public static String getCurrentTimeOrDateInRequiredFormat(String requiredFormat) throws Exception {
		Date currentTime = Calendar.getInstance().getTime();
		String timeStamp = new SimpleDateFormat(requiredFormat).format(currentTime);
		return timeStamp;
	}

	public static String getCurrentDateAndTime() {
		Date currentTime = Calendar.getInstance().getTime();
		String timeStamp = new SimpleDateFormat("dd_MM_yyyy-hh_mm_ss_a").format(currentTime);
		return timeStamp;
	}













}
