/**
 * @author UmaMaheswararao
 */

package com.pages;

import static com.util.ActionUtil.getWebElement;

import org.openqa.selenium.By;

public class BuildPage {

	// Objects Repository(OR):........................................................................
	static By AddBtn = By.xpath(".//*[@id='btnNew']");
	



	

	// Actions:......................................................................................
	public static boolean verify_IQuapturePage() {
		return getWebElement("AddBtn", AddBtn).isDisplayed();
	}





}

