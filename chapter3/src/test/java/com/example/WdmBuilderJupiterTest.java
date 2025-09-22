package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

class WdmBuilderJupiterTest {

	WebDriver driver;
	@BeforeEach
	void setup() {
		driver = WebDriverManager.chromedriver().create();
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}

	@Test
	void test() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java");
	}

}
