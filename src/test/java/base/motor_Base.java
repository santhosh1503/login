package base;


import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import Utilities.MailConfig;
import Utilities.MonitoringMail;
import io.github.bonigarcia.wdm.WebDriverManager;

public class motor_Base {
	public static WebDriver driver;
	public static Properties baseconf = new Properties();
	public static Properties baseloc = new Properties();
	public static  FileReader basedata;
	public static  FileReader loginlogout;
	
	static String messageBody;
	
	@BeforeSuite
	public void setup() throws IOException, InterruptedException {
		
		if(driver==null) {
			  basedata = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\ConfigFiles\\Z.Base.Config.Properties"); 
			  loginlogout = new FileReader(System.getProperty("user.dir")+ "\\src\\test\\resources\\ConfigFiles\\Z.Base.Locator.Properties");
			    
			  baseconf.load(basedata);
			  baseloc.load(loginlogout);
	
		  }
		  if(baseconf.getProperty("browser").equalsIgnoreCase("ch")) {
			  WebDriverManager.chromedriver().setup();
				WebDriver driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get(baseconf.getProperty("testurl"));
				System.out.println("URL opened successfully");
				Thread.sleep(5000);
				driver.findElement(By.id(baseloc.getProperty("unm"))).sendKeys(baseconf.getProperty("agentID"));
				Thread.sleep(1000);
				System.out.println("Username Entered successfully");
				Thread.sleep(1000);
				driver.findElement(By.id(baseloc.getProperty("psd"))).sendKeys(baseconf.getProperty("password"));
				System.out.println("Password Entered successfully");
				Thread.sleep(2000);
				driver.findElement(By.xpath(baseloc.getProperty("Login"))).click();
				System.out.println("Login Button clicked successfully");
				System.out.println("User log in successfully");
				Thread.sleep(6000);
				System.out.println("Home Page opend successfully");

		  }
		  else if(baseconf.getProperty("browser").equalsIgnoreCase("ff")) {
			  WebDriverManager.firefoxdriver().setup();
			  driver = new FirefoxDriver(); 
			  driver.manage().window().maximize();
			  driver.get(baseconf.getProperty("testurl"));
				System.out.println("URL opened successfully");
				Thread.sleep(5000);
				driver.findElement(By.id(baseloc.getProperty("unm"))).sendKeys(baseconf.getProperty("agentID"));
				Thread.sleep(1000);
				System.out.println("Username Entered successfully");
				Thread.sleep(1000);
				driver.findElement(By.id(baseloc.getProperty("Psd"))).sendKeys(baseconf.getProperty("password"));
				System.out.println("Password Entered successfully");
				Thread.sleep(2000);
				driver.findElement(By.xpath(baseloc.getProperty("Login"))).click();
				System.out.println("Login Button clicked successfully");
				System.out.println("User log in successfully");
				Thread.sleep(6000);
				System.out.println("Home Page opend successfully");
		  }
		  else if(baseconf.getProperty("browser").equalsIgnoreCase("ms")) {
			  WebDriverManager.edgedriver().setup();
			  driver = new EdgeDriver();
			  driver.manage().window().maximize();
			  driver.get(baseconf.getProperty("testurl"));
				System.out.println("URL opened successfully");
				Thread.sleep(5000);	
				driver.findElement(By.id(baseloc.getProperty("unm"))).sendKeys(baseconf.getProperty("agentID"));
				Thread.sleep(1000);
				System.out.println("Username Entered successfully");
				Thread.sleep(1000);
				driver.findElement(By.id(baseloc.getProperty("psd"))).sendKeys(baseconf.getProperty("password"));
				System.out.println("Password Entered successfully");
				Thread.sleep(2000);
				driver.findElement(By.xpath(baseloc.getProperty("Login"))).click();
				System.out.println("Login Button clicked successfully");
				System.out.println("User log in successfully");
				Thread.sleep(6000);
				System.out.println("Home Page opend successfully");
		  }
	  }
	
	  public void output(String value){
		  System.out.println(value);	
	     }
		@AfterSuite
	  public void tearDown() throws InterruptedException {
			
			Thread.sleep(5000);
			driver.findElement(By.xpath(baseloc.getProperty("profile"))).click();
			System.out.println("Profile Clicked successfully");
			Thread.sleep(5000);
			driver.findElement(By.xpath(baseloc.getProperty("Sobtn"))).click();
			System.out.println("Sign Out clicked successfully");
			System.out.println("User log Out successfully");
			Thread.sleep(3000);
			 driver.quit();
		    System.out.println("Browser Closed successfully");
		   
	  }
		
	}


