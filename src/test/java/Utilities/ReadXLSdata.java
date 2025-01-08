package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.model.Media;

import java.util.Date;
import java.util.Hashtable;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import base.motor_Base;

public class ReadXLSdata extends motor_Base {
	
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException {
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\" + screenshotName));
		FileUtils.copyFile(scrFile,
				new File(".\\reports\\Screnshots\\" + screenshotName));
	}
	
	@DataProvider(name="TestDataXL")
	public String[][] getData(Method m) throws EncryptedDocumentException, IOException{
		String execelSheetName = m.getName();
		File f = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\LoginTestData.xlsx");
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheetName = wb.getSheet(execelSheetName);
		
		int totalRows = sheetName.getLastRowNum();
		System.out.println(totalRows);
		Row rowCells = sheetName.getRow(0);
		int totalCols = rowCells.getLastCellNum();
		
		
		DataFormatter format = new DataFormatter();
		String testData[][] = new String[totalRows][totalCols];
		for (int i=1; i<=totalRows; i++) {
			for(int j=0; j<totalCols; j++) {
				testData[i-1][j]=format.formatCellValue(sheetName.getRow(i).getCell(j));
				
			}
		}
		return testData;
		
	}

	public static Media capture() {
		// TODO Auto-generated method stub
		return null;
	}

	}

