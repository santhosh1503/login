package motor;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Utilities.MailConfig;
import Utilities.MonitoringMail;
import Utilities.ReadXLSdata;
import Utilities.ScreenRecorderUtil;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.motor_Base;

public class login extends motor_Base{
	
	ExtentSparkReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	String repName;
	static String messageBody;
		
	@BeforeTest
	public void setReport() {
		
//		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//		
//		repName = "Test-Report-"+timestamp+".html";
// 
//		htmlReporter = new ExtentSparkReporter("./reports/Test-Report-"+timestamp+".html");

	    htmlReporter = new ExtentSparkReporter("./reports/Report.html");
	    extent = new ExtentReports();
		
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Motor 4W Automation Reports");
		htmlReporter.config().setReportName("Motor 4W Automation Test Results");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Envrionment","SIT");
		extent.setSystemInfo("Automation Tester", "Santhosh Siluveru");
		extent.setSystemInfo("Orgainzation", "Serole Technologies");	
	}

	@Test()
	public void loginandlogout()throws Exception {
		
		ScreenRecorderUtil.startRecord("login");	
		
		String QuoteNumber = "123";
		test = extent.createTest("login");
				
		output("driver-"+driver);
		Thread.sleep(5000);
		output("login sussfully");
		test.pass("login into TIPS");
		test.log(Status.INFO,"Quote Number is:"+QuoteNumber);
		test.log(Status.INFO,"login into TIPS");
		driver.findElement(By.xpath("/html/body/dx-dijta-home-root/dx-vertical-layout/dx-layout/content/dx-dijta-home-root/dx-compact-content/dx-layout-content/div/dx-dijta-home-dashboard/div/dx-group-wrapper/div/dx-launchpad/div/div/div/div/dx-gridster-wrapper/gridster/gridster-item/div/app-webcomponent-loader/div")).click();
		test.pass("QMS Tile was Clicked");
		test.log(Status.INFO,ReadXLSdata.capture());
		test.log(Status.PASS,"QMS Tile Clicked");
		test.fail("QMS Tile not Clicked");
		test.log(Status.FAIL,"QMS Tile not Clicked");
		
		ScreenRecorderUtil.stopRecord();	
	}
	
	   
	@AfterMethod
	public void tearDown(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {

			String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
			test.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
					+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
					+ " \n");

			try {

				ReadXLSdata.captureScreenshot();
				test.fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
						MediaEntityBuilder.createScreenCaptureFromPath(ReadXLSdata.screenshotName).build());
			} catch (IOException e) {

			}
			String failureLogg = "TEST CASE FAILED";
			Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
			test.log(Status.FAIL, m);

		} else if (result.getStatus() == ITestResult.SKIP) {

			String methodName = result.getMethod().getMethodName();

			String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  SKIPPED" + "</b>";

			Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
			test.skip(m);

		} else if (result.getStatus() == ITestResult.SUCCESS) {

			String methodName = result.getMethod().getMethodName();

			String logText = "<b>" + "TEST CASE: - " + methodName.toUpperCase() + "  PASSED" + "</b>";

			Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
			test.pass(m);
			try {

				ReadXLSdata.captureScreenshot();
				test.pass("<b>" + "<font color=" + "GREEN>" + "Screenshot of Pass" + "</font>" + "</b>",
						MediaEntityBuilder.createScreenCaptureFromPath(ReadXLSdata.screenshotName).build());
			} catch (IOException e) {

			}

		}
		extent.flush(); 
	}

	
	@AfterTest
	public void mail() throws InterruptedException {
		
		MonitoringMail mail = new MonitoringMail();
		
		messageBody = "Automation Report done by Santhosh Siluveru(Mail: santhosh.siluveru@serole.com)";

//		try {
//			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
//					+ ":8080/job/Motor%20TIPS/Extent_20Reports/";
//			
//			messageBody = "Automation Report done by Santhosh Siluveru(Mail: santhosh.siluveru@serole.com)";
//			
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		try {
			mail.sendMail(MailConfig.server, MailConfig.from, MailConfig.to, MailConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	
		Thread.sleep(5000);
	}
	}
	

