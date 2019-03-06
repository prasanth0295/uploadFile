package executionEngine;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import utils.ExcelReader;
import config.Constants;
import utils.Log;

public class DriverScript {
	WebDriver driver = null;

	@Test
	public void main() throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"F:\\Softwares\\Selenium Jars\\chromedriver_win32\\chromedriver.exe");
		DOMConfigurator.configure("G:\\EclipseWorld\\DDT\\log4j.xml");

		ExcelReader er = new ExcelReader();
		er.setSheet(1);
	

		for (int i = 1; i <= er.sheet.getLastRowNum(); i++) {

			try {
				driver = new ChromeDriver();
			} catch (Exception e) {
				Log.fatal("Driver can't be instantiated");
			}
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
			driver.get(Constants.URL);
			driver.manage().window().maximize();
			Log.startTestCase(er.getSheetName(1));
			WebElement email = null;
			try {
				email = driver.findElement(By.id("email"));
				Log.info("E-Mail element is found");
			} catch (NoSuchElementException e) {
				Log.error("Element not found");
			}
			WebElement password = driver.findElement(By.name("passwd"));
			Log.info("Password element is found");
			String emailTo = er.getEmail(i);
			String passwordTo = er.getPassword(i);

			try {
				email.sendKeys(emailTo);
				Log.info("E-Mail entered");
			} catch (ElementNotSelectableException e) {
				Log.fatal("E-mail field is not interacting");
			}
			Thread.sleep(3000);
			password.sendKeys(passwordTo);
			Log.info("Password entered");
			Thread.sleep(3000);
			System.out.println(i + "  th attempt");
			password.submit();
			String value = driver.findElement(By.xpath("/html/body/div[2]/div/div/h3")).getText();
			Log.info("Reached new page and found required element");
			String actual = "Successfully Logged in...";
			// String value = "Successfully Logged in...";
			if (value.contains(actual)) {
				er.setResult(i, "True");
				Log.endTestCase(er.getSheetName(1));
			} else {
				er.setResult(i, "False");
				Log.endTestCase(er.getSheetName(1));
			}
			Log.endTestCase("Closing Browser");
			driver.quit();
		}
	}
}
