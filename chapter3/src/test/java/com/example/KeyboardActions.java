package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;

class KeyboardActions {
	
	WebDriver driver;
	private static final Logger log = LoggerFactory.getLogger(WebDriverMethodsTest.class);
	
	@BeforeEach
	void setUp() {
		driver = WebDriverManager.chromedriver().create();
	}
	
	@AfterEach
	void tearDown() {
		driver.quit();
	}
	@Test
	void test() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement inputText = driver.findElement(By.name("my-text"));
		String textValue = "Hello World";
		inputText.sendKeys(textValue);
		assertThat(inputText.getAttribute("value")).isEqualTo(textValue);
		log.info("the inputText field value: {}", inputText.getAttribute("value").toString());
		
		inputText.clear();
		assertThat(inputText.getAttribute("value")).isEmpty();
	}
	
	@Test
	void testUploadFile() throws IOException {
		String initUrl = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
		driver.get(initUrl);
		
		WebElement inputFile = driver.findElement(By.name("my-file"));
		
		Path tempFile = Files.createTempFile("tempfiles", ".tmp");
		String filename = tempFile.toAbsolutePath().toString();
		log.info("Using temporal file {} in file uploading", filename);
		inputFile.sendKeys(filename);
		
		driver.findElement(By.tagName("form")).submit();
		assertThat(driver.getCurrentUrl()).isNotEqualTo(initUrl);
	}
	
	@Test
	void  testSlider() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		WebElement slider = driver.findElement(By.name("my-range"));
		String initValue = slider.getAttribute("value");
		log.info("The initial value of the slider is {}", initValue);
		
		for (int i = 0; i < 5; i++) {
			slider.sendKeys(Keys.ARROW_RIGHT);
		}
		String endValue = slider.getAttribute("value");
		log.info("The final value of the slider is {}", endValue);
		assertThat(initValue).isNotEqualTo(endValue);
	}

}
