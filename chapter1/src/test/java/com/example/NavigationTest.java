package com.example;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class NavigationTest {
	
	WebDriver driver;

  @BeforeMethod
  public void beforeMethod() {
	  System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
	  
	  driver = new ChromeDriver();
  }
  
  @Test
  public void navigateToAUrl() {
	  driver.get("http://demo-store.seleniumacademy.com/");
	  Assert.assertEquals(driver.getTitle(), "Madison Island");
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}
