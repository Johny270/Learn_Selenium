package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;

class WebDriverMethodsTest {
	
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
	void testBasicMethods() {
		String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
		driver.get(sutUrl);
		
		assertThat(driver.getTitle()).isEqualTo("Hands-On Selenium WebDriver with Java");
		assertThat(driver.getCurrentUrl()).isEqualTo(sutUrl);
		assertThat(driver.getPageSource()).containsIgnoringCase("</html>");
	}
	
	@Test
	void testSessionId() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
		
		SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
		assertThat(sessionId).isNotNull();
		log.info("The sessionId is {}", sessionId.toString());
	}

}
