package test.java.Tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import Classes.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class MainTest {
	
	WebDriver driver;
	ExtentReports extent;
	ExtentTest logger;
	int i=0;
	Date date=new Date();
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	@BeforeTest
	public void beforeTest() {
		System.out.println("Before Test");
		System.out.println("Repots path: "+System.getProperty("user.dir"));
		extent=new ExtentReports(System.getProperty("user.dir")+"\\Reports\\ExtentReport"+format.format(date)+Math.random()+".html",false);
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
	}
	
	@BeforeClass
	public void beforeClass() {
		System.out.println("Before Class");
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver.exe");
	}
	
	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Before Method");
		driver=new ChromeDriver();
		driver.get("http://google.com");
//		driver.manage().window().maximize();
		logger=extent.startTest("Starting test");
		logger.log(LogStatus.INFO, "URL: "+driver.getCurrentUrl());
	}
	
	
	@Test(invocationCount=1)
	public void testNGTest() throws InterruptedException{
		Reporting report=new Reporting(driver, logger, extent);
		
		long numberOfRows=0;
		System.out.println("Test1");
		Google google = new Google(driver, logger, extent);
		Assert.assertTrue(google.setSearchValue("virat kohli wiki"));
		Assert.assertTrue(google.clickSearch());
		Assert.assertTrue(google.validateSearchValueDisplayed("Virat Kohli - Wikipedia"));
		Assert.assertTrue(google.clickSearchValue("Virat Kohli - Wikipedia"));
		Assert.assertTrue(google.clickImage());
		Assert.assertTrue(google.verifyEnlargedImage());
		
		driver.findElement(By.xpath("//button[@class='mw-mmv-close']")).click();
		numberOfRows=Long.valueOf(google.getNumberOfRows());
		System.out.println("Number of rows: "+numberOfRows);
		for(long i=0; i<numberOfRows; i++) {
			report.writeToReport(true, google.getStatsCompetition()+" "+google.getStatsTest()+" "+google.getStatsODI()+" "+google.getStatsT20I()+" "+google.getStatsFC20I());
		}
	}
	
//	@Test
//	public void testNGTest2() {
//		System.out.println("Test2");
//		Google google = new Google(driver, logger, extent);
//		AssertJUnit.assertTrue(google.setSearchValue("abcd"));
//		//Assert.assertTrue(google.clickSearch());
//	
//	}
	
	@AfterMethod
	public void afterEachTest(ITestResult result) {
		System.out.print("After Method\n");
		switch(result.getStatus()) {
		case ITestResult.FAILURE: logger.log(LogStatus.FAIL, "Test failed");
		break;
		case ITestResult.SUCCESS: logger.log(LogStatus.PASS, "Test passed");
		break;
		case ITestResult.SKIP: logger.log(LogStatus.SKIP, "Test skipped");
		break;
		}
//		driver.close();
		logger.log(LogStatus.INFO, "Closed browser");
		extent.endTest(logger);
	}
	
	@AfterClass
	public void afterSuite() {
		System.out.println("After Class");
//		driver.quit();
		extent.flush();
		extent.close();
	}
	
}
