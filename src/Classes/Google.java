package Classes;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Google {
	
	WebDriver driver;
	ExtentTest logger;
	ExtentReports extent;
	Reporting report;
	
	public Google(WebDriver d, ExtentTest l, ExtentReports e) {
		driver=d;
		logger=l;
		extent=e;
		report=new Reporting(driver, logger, extent);
	};
	
	private String googleSearch="//input[@name='q']";
	private String btnSearch="(//input[@name='btnK'])[2]";
	private String btnSearch2="//button[@id='mKlEF']";
	private String lnkSearchValue="//h3[@class='LC20lb' and contains(text(),'<SEARCH_VALUE>')]";
	private String imgGoogle = "//img[@alt='Google']";
	private String imgVirat = "//img[@alt='Virat Kohli portrait.jpg']";
	private String imgEnlarged = "//img[@crossorigin='anonymous']";
	private String statsRow="//th[text()='Career statistics']//..//..//table//tr";
	private String statsCompetition = statsRow+"/th";
	private String statsTest = statsRow+"/td";
	private String statsODI = statsRow+"/td[2]";
	private String statsT20I= statsRow+"/td[3]";
	private String statsFC20 = statsRow+"/td[4]";
	
	public boolean setSearchValue(String value) {
		
		try{driver.findElement(By.xpath(googleSearch)).clear();
		driver.findElement(By.xpath(googleSearch)).sendKeys(value);
		report.writeToReport(true, "Entered value");
		}catch(Exception e) {
			e.printStackTrace();
			report.writeToReport(false, "Error while trying to set search value "+value+". Error: "+e.getLocalizedMessage());
			return false;
		}
		
		return true;
	}
	
	public boolean clickSearch() throws InterruptedException {
		driver.findElement(By.xpath(imgGoogle)).click();
		Thread.sleep(2000);
		JavascriptExecutor js=  (JavascriptExecutor)driver;
		try { 
			driver.findElement(By.xpath(btnSearch)).click();
//			js.executeScript("arguments.click();", driver.findElement(By.xpath(btnSearch)));
		report.writeToReport(true, "Clicked on search");
		}catch(Exception e) {
			e.printStackTrace();
			try { driver.findElement(By.xpath(btnSearch2)).click();				
			}catch(Exception ex) {
				ex.printStackTrace();
				report.writeToReport(false, "Error while clicking on search button. Error: "+e.getLocalizedMessage());
				return false;
			}
		}	
		
		return true;
	}
	
	public boolean clickSearchValue(String value) {
		try {driver.findElement(By.xpath(lnkSearchValue.replace("<SEARCH_VALUE>", value))).click();
		report.writeToReport(true, "Clicked on search value: "+value);
		}catch(Exception e) {
			e.printStackTrace();
			try { driver.findElement(By.xpath(lnkSearchValue.replace("<SEARCH_VALUE>", value))).click();				
			}catch(Exception ex) {
				ex.printStackTrace();
				report.writeToReport(false, "Error while clicking on search button. Error: "+e.getLocalizedMessage());
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validateSearchValueDisplayed(String value) {
		int retryCount=0;
		boolean linkFound=false;
		
		do {
			if(driver.findElement(By.xpath(lnkSearchValue.replace("<SEARCH_VALUE>", value))).isDisplayed()==false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				retryCount++;
			}else {
				linkFound=true;
				break;
			}
				
		}while(retryCount<30);
		
		if(linkFound=false)
			return false;
		
		return true;
	}
	
	public boolean clickImage() {
		try {driver.findElement(By.xpath(imgVirat)).click();
		report.writeToReport(true, "Clicked on image");
		}catch(Exception e) {
			e.printStackTrace();
			try { driver.findElement(By.xpath(imgVirat)).click();				
			}catch(Exception ex) {
				ex.printStackTrace();
				report.writeToReport(false, "Error while clicking on search button. Error: "+e.getLocalizedMessage());
				return false;
			}
		}
		
		return true;
	}
	
	public boolean verifyEnlargedImage() {
		int retryCount=0;
		boolean imageFound=false;
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		do {
			if(driver.findElement(By.xpath(imgEnlarged)).isDisplayed()==false) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				retryCount++;
			}else {
				imageFound=true;
				break;
			}
			
		}while(retryCount<30);
		
		if(imageFound==false)
			return false;
		
		return true;
	}
	
	public String getNumberOfRows() {
		List<WebElement> elements = driver.findElements(By.xpath(statsRow));
		
		return String.valueOf(elements.size());
	}
	
	public String getStatsCompetition() {
		return driver.findElement(By.xpath(statsCompetition)).getText();
	}
	
	public String getStatsTest() {
		return driver.findElement(By.xpath(statsTest)).getText();
	}
	
	public String getStatsODI() {
		return driver.findElement(By.xpath(statsODI)).getText();
	}
	
	public String getStatsT20I() {
		return driver.findElement(By.xpath(statsT20I)).getText();
	}
	
	public String getStatsFC20I() {
		return driver.findElement(By.xpath(statsFC20)).getText();
	}
	
}
