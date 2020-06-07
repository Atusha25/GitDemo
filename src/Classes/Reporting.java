package Classes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporting {
	
	WebDriver driver;
	ExtentTest logger;
	ExtentReports extent;

	public Reporting(WebDriver d, ExtentTest l, ExtentReports e) {
		driver=d;
		logger=l;
		extent=e;
	}
	
	File scr=null;
	File ssName=null;
	Date date=new Date();
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	public void writeToReport(boolean status, String value) {
		scr=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+"\\Screenshots\\screenshot_"+format.format(date)+"_"+System.currentTimeMillis()+".png";
		ssName=new File(path);
		try {
			FileUtils.copyFile(scr, ssName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//logger.addScreenCapture(path);
		if(status==false)  
			logger.log(LogStatus.FAIL, value+logger.addScreenCapture(path));
		else  
			logger.log(LogStatus.PASS, value+logger.addScreenCapture(path));
	}
	
}
