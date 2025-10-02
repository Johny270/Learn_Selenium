package Garcimorez;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import static org.assertj.core.api.Assertions.assertThat;

class ExecutingJavaScript {
	
	WebDriver driver;
	private static final Logger log = LoggerFactory.getLogger(ExecutingJavaScript.class);
	
	@BeforeEach()
	void setup() {
		driver = WebDriverManager.chromedriver().create();
	}
	
	@AfterEach()
	void tearDown() {
		driver.quit();
	}
	
	@Test
	void testScrollIntoView() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		// Locate the last paragraph in the Web page using css Selector
		WebElement lastElement = driver.findElement(By.cssSelector("p:last-child"));
		String script = "arguments[0].scrollIntoView();";
		// Executing the js script passing in the located WebElement as an argument
		js.executeScript(script, lastElement);
	}
	
	@Test
	void testInfiniteScroll() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/infinite-scroll.html");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		// To pause the test until the new content is loaded
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		By pLocator = By.tagName("p");
		List<WebElement> paragraphs = wait.until(
				ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, 0));
		int initParagraphsNumber = paragraphs.size();
		WebElement lastParagraph = driver.findElement(
				By.xpath(String.format("//p[%d]", initParagraphsNumber)));
		String script = "arguments[0].scrollIntoView()";
		
		// Scroll into the last paragraph on the page
		js.executeScript(script, lastParagraph);
		// Wait until the new content is loaded
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(pLocator, initParagraphsNumber));
	}
	
	@Test
	void testColorPicker() {
		driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		// Locate the colorPicker by name
		WebElement colorPicker = driver.findElement(By.name("my-colors"));
		// Read the value of the color picker
		String initColor = colorPicker.getAttribute("value");
		log.info("The initial color is {}", initColor);
		
		Color red = new Color(255, 0, 0, 1);
		String script = String.format("arguments[0].setAttribute('value', '%s');", red.asHex());
		js.executeScript(script, colorPicker);
		
		String finalColor = colorPicker.getAttribute("value");
		log.info("The final color is {}", finalColor);
		assertThat(finalColor).isNotEqualTo(initColor);
		assertThat(Color.fromString(finalColor)).isEqualTo(red);
		
	}
	
	@Test
	void testPinnedScripts() {
		/*
		 * Pinned Scripts: Enables attaching JavaScript fragments to a WebDriver session
		 * assigning a unique key to each snippet, and executing these snippets on demand
		*/
		String initPage = "https://bonigarcia.dev/selenium-webdriver-java/";
		driver.get(initPage);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		ScriptKey linkKey = js
				.pin("return document.getElementsByTagName('a')[2];");
		ScriptKey firstArgKey = js.pin("return arguments[0];");
		
		Set<ScriptKey> pinnedScripts = js.getPinnedScripts();
		assertThat(pinnedScripts).hasSize(2);
		
		WebElement formLink = (WebElement)js.executeScript(linkKey);
		formLink.click();
		assertThat(driver.getCurrentUrl()).isNotEqualTo(initPage);
		
		String message = "Hello World!";
		String executeScript = (String)js.executeScript(firstArgKey, message);
		assertThat(executeScript).isEqualTo(message);
		
		js.unpin(linkKey);
		assertThat(js.getPinnedScripts()).hasSize(1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
