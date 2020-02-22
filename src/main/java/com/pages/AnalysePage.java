/**
 * @author UmaMaheswararao
 */

package com.pages;

import static com.base.BasePage.tldriver;
import static com.util.ActionUtil.getWebElement;
import static com.util.ActionUtil.waitForAjax;
import static com.util.ActionUtil.waitForElementVisibility;

import org.openqa.selenium.By;
import org.testng.Assert;

public class AnalysePage {
	// Objects Repository(OR):........................................................................
	// Page verification
	static By analysesSearchBox = By.xpath(".//*[@id='searchAnalyses']");
	
		

	
	// Actions:......................................................................................

	public static void verify_AnalysePage() throws Exception {
		waitForElementVisibility("analysesSearchBox", analysesSearchBox);
		Assert.assertTrue(getWebElement("analysesSearchBox", analysesSearchBox).isDisplayed(), "Unable to Navigate Analyse Page");
		tldriver.get().findElement(By.xpath("//a[contains(@title,'Return to parent folder')]")).click();
		waitForAjax(tldriver.get());
		Thread.sleep(2000);
		tldriver.get().findElement(By.xpath("//a[contains(@title,'Return to parent folder')]")).click();
		waitForAjax(tldriver.get());
	}


}
