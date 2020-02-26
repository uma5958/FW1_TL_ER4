/**
 * @author UmaMaheswararao
 */

package com.base;

import static com.util.ActionUtil.clearBrowserCookies;
import static com.util.ActionUtil.getCurrentDateAndTime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(com.Listeners.TestListeners.class)
public class BasePage {

	public static Properties prop;
	
	public static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> tlExtentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> tlExtentTestNode = new ThreadLocal<ExtentTest>();
	public static String testDataFilePath = "./src/main/java/com/testdata/NewiQaptureData.xlsx";
	
	@BeforeSuite
	public void beforeSuite() throws Exception {
		initialize_properties();
		initilizeExtentReport();
	}
	
	@AfterMethod
	public void tearDown() {
		quit();
	}
	
	@AfterSuite
	public static void afterSuite() throws Exception {
	}
	
	public static Properties initialize_properties() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static void launchBowser() throws Exception{
		String browserName = prop.getProperty("browser");

		if(browserName.equalsIgnoreCase("chrome")){
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");	
			setDriver(new ChromeDriver());
			getDriver().manage().window().maximize();
		}
		else if(browserName.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");	
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"C:\\temp\\logs.txt");
			setDriver(new FirefoxDriver());
		} 
		else if(browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			setDriver(new EdgeDriver());
		}
		else if(browserName.equalsIgnoreCase("headlessChrome")) {
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");	
			ChromeOptions options = new ChromeOptions();  
			options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");  
			setDriver(new ChromeDriver(options));
		}

		clearBrowserCookies();
		getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		getDriver().get(prop.getProperty("url"));
	}	
	
	public static void initilizeExtentReport() throws Exception {
		String timeStamp = getCurrentDateAndTime();
		extent = new ExtentReports();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "\\ExtentReports\\Reports\\Automation_Report-" + timeStamp + ".html");
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "\\ExtentReports\\ConfigFile\\html-config.xml");
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS", prop.getProperty("os"));
		extent.setSystemInfo("Environment", prop.getProperty("envirionment"));
		extent.setSystemInfo("Browser", prop.getProperty("browser"));
	}


	public static WebDriver getDriver() {
		return tldriver.get();
	}

	public static void setDriver(WebDriver driver) {
		tldriver.set(driver);
	}
	
	public static void start()  {
		if(getDriver()==null)
			try {
			new BasePage();
			}
		catch(Exception e) {
			
		}
	}

	public static void quit() {
		if(getDriver()!=null) {
			getDriver().quit();
		}
	}
	
	
	
	



}
