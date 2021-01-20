package selenium.com;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BlueStack {
	WebDriver driver;
	
	String gameName, tournamentCount, pageUrl;
	int statusCode;
	
	@Test(priority = 1)
	public void launchUrl() {
		System.setProperty("webdriver.chrome.driver","C://Users//manishpatel//Downloads//chromedriver//chromedriver.exe");
		driver = new ChromeDriver();

		driver.get("https://www.game.tv/");
		driver.manage().window().maximize();
		System.out.println("Launched url");
	}

	@Test(priority = 2)
	public void fetchTounramentData() throws InterruptedException {
		int gameCount = driver.findElements(By.xpath("//li[@class='games-item']")).size();
		String addedWeeklyTextXpath = "//p[text()='More Added Weekly']";
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		for (int i = 1; i <= gameCount; i++) {
			String availableGameXpath = "(//li[@class='games-item']//a)[" + i + "]";

			WebElement availableGameElement = (i <= 25)? driver.findElement(By.xpath(addedWeeklyTextXpath)) : driver.findElement(By.xpath("(//li[@class='games-item']//a)[" + (i-6) + "]"));
			js.executeScript("arguments[0].scrollIntoView();", availableGameElement); // scroll
			
			Thread.sleep(500);
			boolean flagLoader = driver.findElement(By.xpath("(//ul[@class='games-list']//li//div[@class='img-loading']//*[name()='svg'])[1]")).isDisplayed();
			int counter = 1;
			while (flagLoader) {
				Thread.sleep(1000);
				flagLoader = driver.findElement(By.xpath("(//ul[@class='games-list']//li//div[@class='img-loading']//*[name()='svg'])[1]")).isDisplayed();
				counter++;
				if(counter >3) {
					break;
				}
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(availableGameXpath)));
			
			WebElement el = driver.findElement(By.xpath(availableGameXpath));
			Actions hoverOver = new Actions(driver);
			hoverOver.moveToElement(el).build().perform();
			
			driver.findElement(By.xpath(availableGameXpath)).click();
			// 1) Name of the game
			gameName = driver.findElement(By.xpath("//div//h1[@class='heading']")).getText();
			// 2) Game detail page url
			pageUrl = driver.getCurrentUrl();
			// 3) Status code for each age url
			Response res = RestAssured.get(pageUrl);
			statusCode = res.getStatusCode();
			// 4) Tournament count
			tournamentCount = driver.findElement(By.xpath("//div//span[@class='count-tournaments']")).getText();
			
			System.out.println(i+"  " + gameName +"  "+pageUrl+ "  "+statusCode+"  " + tournamentCount);
			driver.navigate().back();
		}
	}
}
