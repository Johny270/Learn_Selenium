package com.example;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.locators.RelativeLocator.RelativeBy;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;

class LocationStrategiesTest {
	
	WebDriver driver;
	@BeforeEach
	void setUp() {
		driver = WebDriverManager.chromedriver().create();
	}

	@AfterEach
	void tearDown() {
		driver.quit();
	}

	@Test
	void testByTagName() {	
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		WebElement textarea = driver.findElement(By.tagName("textarea"));
		assertThat(textarea.getDomAttribute("rows")).isEqualTo("3");
	}
	
	@Test
	void testByHtmlAttributes() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		// By Name
		WebElement textByName = driver.findElement(By.name("my-text"));
		assertThat(textByName.isEnabled()).isTrue();
		
		// By id
		WebElement textById = driver.findElement(By.id("my-text-id"));
		assertThat(textById.getAttribute("type")).isEqualTo("text");
		assertThat(textById.getDomAttribute("type")).isEqualTo("text");
		assertThat(textById.getDomProperty("type")).isEqualTo("text");
		
		// By class name
		List<WebElement> byClassName = driver.findElements(By.className("form-control"));
		assertThat(byClassName.size()).isPositive();
		assertThat(byClassName.get(0).getAttribute("name")).isEqualTo("my-text");
		
	}
	
	@Test
	void testByLinkText() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement linkByText = driver.findElement(By.linkText("Return to index"));
		assertThat(linkByText.getTagName()).isEqualTo("a");
		assertThat(linkByText.getCssValue("cursor")).isEqualTo("pointer");
		
		WebElement linkByPartialText = driver.findElement(By.partialLinkText("index"));
		assertThat(linkByPartialText.getLocation()).isEqualTo(linkByText.getLocation());
		assertThat(linkByPartialText.getRect()).isEqualTo(linkByText.getRect());
	}
	
	@Test
	void testByCssSelectorBasic() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement hidden = driver.findElement(By.cssSelector("input[type=hidden]"));
		assertThat(hidden.isDisplayed()).isFalse();
	}
	
	@Test
	void testByCssSelectorAdvanced() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement checkbox1 = driver.findElement(By.cssSelector("[type=checkbox]:checked"));
		assertThat(checkbox1.getAttribute("id")).isEqualTo("my-check-1");
		assertThat(checkbox1.isSelected()).isTrue();
		
		WebElement checkbox2 = driver.findElement(By.cssSelector("[type=checkbox]:not(:checked)"));
		assertThat(checkbox2.getAttribute("id")).isEqualTo("my-check-2");
		assertThat(checkbox2.isSelected()).isFalse();
		
	}
	
	@Test
	void testByXPathBasic() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement hidden = driver.findElement(By.xpath("//input[@type='hidden']"));
		assertThat(hidden.isDisplayed()).isFalse();
	}
	
	@Test
	void testByXPathAdvanced() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement radio1 = driver.findElement(By.xpath("//*[@type='radio' and @checked]"));
		assertThat(radio1.getAttribute("id")).isEqualTo("my-radio-1");
		assertThat(radio1.isSelected()).isTrue();
		
		WebElement radio2 = driver.findElement(By.xpath("//*[@type='radio' and not(@checked)]"));
		assertThat(radio2.getAttribute("id")).isEqualTo("my-radio-2");
		assertThat(radio2.isSelected()).isFalse();
	}
	
	@Test
	void testByIdOrName() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement fileElement = driver.findElement(new ByIdOrName("my-file"));
		assertThat(fileElement.getAttribute("id")).isBlank();
		assertThat(fileElement.getAttribute("name")).isNotBlank();
	}
	
	@Test
	void testByChained() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		List<WebElement> rowsInForm = driver.findElements(new ByChained(By.tagName("form"), By.className("row")));
		assertThat(rowsInForm.size()).isEqualTo(1);
	}
	
	@Test
	void testByAll() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		List<WebElement> rowsInForm = driver.findElements(new ByAll(By.tagName("form"), By.className("row")));
		assertThat(rowsInForm.size()).isEqualTo(5);
	}
	
	@Test
	void testRelativeLocators() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement link = driver.findElement(By.linkText("Return to index"));
		RelativeBy relativeBy = RelativeLocator.with(By.tagName("input"));
		WebElement readOnly = driver.findElement(relativeBy.above(link));
		assertThat(readOnly.getAttribute("name")).isEqualTo("my-readonly");
	}

}
