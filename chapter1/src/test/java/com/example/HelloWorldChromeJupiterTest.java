package com.example;

import org.testng.annotations.Test;

//import ch.qos.logback.classic.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.logging.log4j.LogManager.getLogger;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;


public class HelloWorldChromeJupiterTest {
//	static final Logger log = getLogger(lookup());
	private static final Logger log = LoggerFactory.getLogger(HelloWorldChromeJupiterTest.class);
	
	private WebDriver driver;
	static final String driverPath = Paths.get("src/test/resources/drivers/chromedriver.exe").toAbsolutePath().toString();
	
	@BeforeAll
	void setupClass() {
		WebDriverManager.chromedriver().setup();
		log.info("ChromeDriver path set to {}", driverPath);
	}
	
	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
		log.info("ChromeDriver path set to {}", driverPath);
	}
	
	@Test
	void test() {
		String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java";
		driver.get(sutUrl);
		String title = driver.getTitle();
//		log.d	ebug("The title of {} is {}", sutUrl, title);
		log.info("ChromeDriver path set to {}", driverPath);
		assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
	}
	
	@AfterEach
	void teardown() {
		driver.quit();
	}
}
