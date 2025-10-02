package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;
import io.github.bonigarcia.wdm.WebDriverManager;

class WaittingStrategies {
	WebDriver driver;
	
	@BeforeEach()
	void setup() {
		driver = WebDriverManager.chromedriver().create();
	}
	
	@AfterEach()
	void tearDown() {
		driver.quit();
	}
	
	@Test
	void testImplicitWait() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement landscape = driver.findElement(By.id("landscape"));
		assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
	}
	
	@Test
	void testExplicitWait() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement landscape = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("landscape")));
		assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
	}
	
	@Test
	void testSlowCalculator() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator");
		
		driver.findElement(By.xpath("//span[text()='1']")).click();
		driver.findElement(By.xpath("//span[text()='+']")).click();
		driver.findElement(By.xpath("//span[text()='3']")).click();
		driver.findElement(By.xpath("//span[text()='=']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.textToBe(By.className("screen"), "4"));
	}
	
	@Test
	void testFluentWait() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images");
		Wait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofSeconds(1))
				.ignoring(NoSuchElementException.class);
		
		WebElement landscape = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.id("landscape")));
		assertThat(landscape.getAttribute("src")).containsIgnoringCase("landscape");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
