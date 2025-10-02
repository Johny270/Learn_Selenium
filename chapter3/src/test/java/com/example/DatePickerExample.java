package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import io.github.bonigarcia.wdm.WebDriverManager;

class DatePickerExample {
	
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
	void testDatePicker() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		// Get the current date from the system clock
		LocalDate today = LocalDate.now();
		int currentYear = today.getYear();
		int currentDay = today.getDayOfMonth();
		
		// Click on the date picker to open the calendar
		WebElement datePicker = driver.findElement(By.name("my-date"));
		datePicker.click();
		
		// Click on the current month by searching by text
		WebElement monthElement = driver.findElement(By.xpath(String.format("//th[contains(text(), '%d')]", currentYear)));
		monthElement.click();
		
		// Click on the left arrow using relative locators
		WebElement arrowLeft = driver.findElement(RelativeLocator.with(By.tagName("th")).toRightOf(monthElement));
		arrowLeft.click();
		
		// Click on the current month of that year
		WebElement monthPastYear = driver.findElement(RelativeLocator.with(By.cssSelector("span[class$-focused]")).below(arrowLeft));
		monthPastYear.click();
		
		// Click on the present day of that month
		WebElement dayElement = driver.findElement(By.xpath(String.format("//td[@class='day' and contains(text(), '%d')]", currentDay)));
		dayElement.click();
		
		// Get the final date on the input text
		String oneYearBack = datePicker.getAttribute("value");
		log.info("Final date in date picker: {}", oneYearBack);
		
		// Assert that the expected date is equal to the one selected in the date picker
		LocalDate previousYear = today.minusYears(1);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String expectedDate = previousYear.format(dateFormat);
		
		log.info("Expected date: {}", expectedDate);
		assertThat(oneYearBack).isEqualTo(expectedDate);
	}

}
