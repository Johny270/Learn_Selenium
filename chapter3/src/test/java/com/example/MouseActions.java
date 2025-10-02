package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import io.github.bonigarcia.wdm.WebDriverManager;

class MouseActions {

	WebDriver driver;
	
	@BeforeEach
	void setUp() {
		driver = WebDriverManager.chromedriver().create();
	}
	
	@AfterEach()
	void tearDown() {
		driver.quit();
	}
	
	@Test
	void testNavigationSimpleClick() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java");
		
		driver.findElement(By.xpath("//a[text()='Navigation']")).click();
		driver.findElement(By.xpath("//a[text()='Next']")).click();
		driver.findElement(By.xpath("//a[text()='3']")).click();
		driver.findElement(By.xpath("//a[text()='2']")).click();
		driver.findElement(By.xpath("//a[text()='Previous']")).click();
		
		String bodyText = driver.findElement(By.tagName("body")).getText();
		assertThat(bodyText).contains("Lorem ipsum");
	}
	
	@Test
	void testNavigationCheckboxesAndRadioButtons() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		WebElement checkbox2 = driver.findElement(By.id("my-check-2"));
		checkbox2.click();
		assertThat(checkbox2.isSelected()).isTrue();
		
		WebElement radio2 = driver.findElement(By.id("my-radio-2"));
		radio2.click();
		assertThat(radio2.isSelected()).isTrue();
	}
	
	@Test
	void testContextAndDoubleClick() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
		Actions actions = new Actions(driver);
		
		WebElement dropdown2 = driver.findElement(By.id("my-dropdown-2"));
		actions.contextClick(dropdown2).build().perform();
		WebElement contextMenu2 = driver.findElement(By.id("context-menu-2"));
		assertThat(contextMenu2.isDisplayed()).isTrue();
		
		WebElement dropdown3 = driver.findElement(By.id("my-dropdown-3"));
		actions.doubleClick(dropdown3).build().perform();
		WebElement contextMenu3 = driver.findElement(By.id("context-menu-3"));
		assertThat(contextMenu3.isDisplayed()).isTrue();
	}
	
	@Test
	void testMouseOver() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
		Actions actions = new Actions(driver);
		
		List<String> imageList = Arrays.asList("compass", "calendar", "award", "landscape");
		
		for (String imageName : imageList) {
			String xpath = String.format("//img[@src='img/%s.png']", imageName);
			WebElement image = driver.findElement(By.xpath(xpath));
			actions.moveToElement(image).build().perform();
			
			WebElement caption = driver.findElement(RelativeLocator.with(By.tagName("div")).near(image));
			assertThat(caption.getText()).containsIgnoringCase(imageName);
		}
	}
	
	@Test
	void testDragAndDrop() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
		Actions actions = new Actions(driver);
		
		WebElement draggable = driver.findElement(By.id("draggable"));
		int offset = 100;
		Point initLocation = draggable.getLocation();
		actions.dragAndDropBy(draggable, offset, 0)
			.dragAndDropBy(draggable, 0, offset)
			.dragAndDropBy(draggable, -offset, 0)
			.dragAndDropBy(draggable, 0, -offset).build().perform();
		assertThat(initLocation).isEqualTo(draggable.getLocation());
		
		WebElement target = driver.findElement(By.id("target"));
		actions.dragAndDrop(draggable, target).build().perform();
		assertThat(target.getLocation()).isEqualTo(draggable.getLocation());
	}
	
	@Test
	void testClickAndHold() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/draw-in-canvas.html");
		Actions actions = new Actions(driver);
		
		WebElement canvas = driver.findElement(By.tagName("canvas"));
		actions.moveToElement(canvas).clickAndHold();
		
		int numPoints = 10;
		int radius = 30;
		for (int i = 0; i <= numPoints; i++) {
			double angle = Math.toRadians(360 * i / numPoints);
			double x = Math.sin(angle) * radius;
			double y = Math.cos(angle) * radius;
			actions.moveByOffset((int) x, (int) y);
		}
		actions.release(canvas).build().perform();
	}
	
	@Test
	 void testCopyAndPaste() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		Actions actions = new Actions(driver);
		
		WebElement inputText = driver.findElement(By.name("my-text"));
		WebElement textarea = driver.findElement(By.name("my-textarea"));
		
		Keys modifier = SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL;
		actions.sendKeys(inputText, "Hello world").keyDown(modifier)
			.sendKeys(inputText, "a").sendKeys(inputText, "c")
			.sendKeys(textarea, "v").build().perform();
		
		assertThat(inputText.getAttribute("value")).isEqualTo(textarea.getAttribute("value"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
