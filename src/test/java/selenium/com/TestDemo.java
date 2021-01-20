package selenium.com;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TestDemo {
	WebDriver driver;
	String searchResult, searchResultXpath;
	String driverPath = "C://Users//manishpatel//Downloads//chromedriver//chromedriver.exe";
	String url = "https://www.google.com/";
	String searchText = "testng";
	String xpath_searchBox = "//input[@class='gLFyf gsfi']";
	String xpath_searchResultLink = "((//div[@class='g'])[index]//cite)[1]";
	List webLinkList = new ArrayList<String>();

	public void logMessage(String msg) {
		System.out.println(msg);
	}

	public void hardWait(int sec) throws InterruptedException {
		Thread.sleep(sec * 1000);
		logMessage("Waiting for " + sec + " seconds...");
	}

	@Test(priority = 1)
	public void launchUrl() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		
		driver.get(url);
		logMessage("Url launched: "+url);
		
		driver.manage().window().maximize();
		logMessage("Browser Maximized");
	}

	@Test(priority = 2)
	public void searchResultOnGoogle() {
		driver.findElement(By.xpath(xpath_searchBox)).click();
		logMessage("Clicked on search text box");
		
		WebElement searchBox = driver.findElement(By.xpath(xpath_searchBox));
		
		searchBox.sendKeys(searchText);
		logMessage("User entered search text i.e: "+searchText);
		
		searchBox.sendKeys(Keys.ENTER);
		logMessage("User performed 'Enter' stroke");
	}

	@Test(priority = 2)
	public void getSearchResultUrl() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		hardWait(5);
		for (int i = 1; i <= 5; i++) {
			searchResultXpath = xpath_searchResultLink.replace("index", String.valueOf(i));
			WebElement availablElement = driver.findElement(By.xpath(searchResultXpath));
			js.executeScript("arguments[0].scrollIntoView();", availablElement);
			searchResult = driver.findElement(By.xpath(searchResultXpath)).getText();
			hardWait(1000);
			webLinkList.add(searchResult);
		}
		System.out.println(webLinkList);
	}
}
