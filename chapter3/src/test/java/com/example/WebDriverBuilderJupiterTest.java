package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.assertj.core.api.Assertions.assertThat;

class WebDriverBuilderJupiterTest {

	WebDriver driver;
	
	@BeforeAll
	static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	void setup() {
		driver = RemoteWebDriver.builder().oneOf(new ChromeOptions()).build();
	}
	
	@AfterEach
	void teardown() {
		driver.quit();
	}
	
	@Test
	void test() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java");
		assertThat(driver.getTitle()).isEqualTo("Hands-On Selenium WebDriver with Java");
	}

}
