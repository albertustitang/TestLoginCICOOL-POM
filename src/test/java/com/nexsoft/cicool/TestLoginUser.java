package com.nexsoft.cicool;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestLoginUser {

	protected WebDriver driver;
	private JavascriptExecutor jse;
	
	public void delay(int inInt) {
		try {
			Thread.sleep(inInt);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String screenShot() {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String waktu = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String namaFile = "D:\\TestScreenshoot\\" + waktu + ".png";
		File screenshot = new File(namaFile);
		try {
			FileUtils.copyFile(srcFile, screenshot);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return namaFile;
	}

	@BeforeTest
	public void init() {
		System.setProperty("url", "http://localhost/cicool");
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		driver = new ChromeDriver();
		jse = (JavascriptExecutor) driver;
		driver.get(System.getProperty("url"));
		driver.manage().window().maximize();
	}

	@BeforeMethod
	public void cekSession() {
		driver.get(System.getProperty("url"));

		// cek session user telah login atau belum

		HomePage home = PageFactory.initElements(driver, HomePage.class);

//		home.clickSignIn().loginValidUser("albertustitang@gmail.com", "@Yogyakarta99");

//		driver.get(System.getProperty("url"));

//		home.logout();

	}


	@Test(priority = 1)
	public void testLoginWithValidUserAndPassword() {

		HomePage home = PageFactory.initElements(driver, HomePage.class);

		Dashboard dash = home.clickSignIn().loginValidUser("albertustitang@gmail.com", "@Yogyakarta99");
		delay(500);
		// take screenshot
		String file = "<img src='file://" + screenShot() + "'height=\"450\" width=\"1017\"/>";
		Reporter.log(file);

		Assert.assertEquals(dash.getUsername(), "Albertustitang");

	}

	@Test(priority = 2)
	public void testLoginWithValidUserAndWrongPassword() {

		HomePage home = PageFactory.initElements(driver, HomePage.class);
		home.logout();
		SignInPage signin = home.clickSignIn();

		Dashboard dashboard = signin.loginValidUser("albertustitang@gmail.com", "123");
		
		delay(500);
		jse.executeScript("window.scrollBy(0, 100)", "");
		
		String file = "<img src='file://" + screenShot() + "'height=\"450\" width=\"1017\"/>";
		Reporter.log(file);

		Assert.assertEquals(signin.getErrorPassword(), "E-mail Address and Password do not match.");

		try {
			dashboard.getUsername();
		} catch (Exception e) {
			assertTrue(true, "Text Tidak ditemukan " + e.getMessage());
		}

	}
	
	@Test(priority = 3)
	public void testLogin_usernameNotValid_passwordNotValid() {
		HomePage home = PageFactory.initElements(driver, HomePage.class);
		SignInPage signin = home.clickSignIn();
		Dashboard dashboard = signin.loginValidUser("titan@gmail.com", "titan");
		
		delay(500);
		jse.executeScript("window.scrollBy(0, 100)", "");
		
		String file = "<img src='file://" + screenShot() + "'height=\"450\" width=\"1017\"/>";
		Reporter.log(file);

		// verify login failed user doesn't exist
		Assert.assertEquals(signin.getErrorUsernamePassword(), "User does not exist");
		try {
			dashboard.getUsername();
		} catch (Exception e) {
			assertTrue(true, "Text not found " + e.getMessage());
		}
	}
	
	@Test(priority = 4)
	public void testLogin_usernameEmpty_passwordEmpty() {
		HomePage home = PageFactory.initElements(driver, HomePage.class);
		
		SignInPage signin = home.clickSignIn();
		Dashboard dashboard = signin.loginValidUser("", "");
		
		delay(500);
		jse.executeScript("window.scrollBy(0, 100)", "");
		
		String file = "<img src='file://" + screenShot() + "'height=\"450\" width=\"1017\"/>";
		Reporter.log(file);

		// verify login failed user and pass empty
		Assert.assertEquals(signin.getErrorEmptyUsernamePassword(), "uname and pass catch");
		try {
			dashboard.getUsername();
		} catch (Exception e) {
			assertTrue(true, "Text not found " + e.getMessage());
		}
	}
	
	@Test(priority = 5)
	public void testforgotPassword() {
		HomePage home = PageFactory.initElements(driver, HomePage.class);
		SignInPage signin = home.clickSignIn();
		ForgotPasswordPage forgot = signin.clickForgotPassword();
		
		delay(500);
		jse.executeScript("window.scrollBy(0, 100)", "");
		
		String file = "<img src='file://" + screenShot() + "'height=\"450\" width=\"1017\"/>";
		Reporter.log(file);

		// verify login failed user and pass empty
		Assert.assertEquals(forgot.getForgotPasswordValue(), "Send a link to reset the password");
		try {
			forgot.getForgotPasswordValue();
		} catch (Exception e) {
			assertTrue(true, "Text not found " + e.getMessage());
		}
	}
	
	

}
