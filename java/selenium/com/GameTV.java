package selenium.com;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class GameTV{

	 public WebDriver driver ; 
	 public String baseUrl = "https://game.tv";
     @Test(testName = "ChromeTest")
     public void verifyHomepageTitle() throws InterruptedException {

	 //System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
	 driver = new ChromeDriver();
	 //launcing the url
     driver.get(baseUrl);
     driver.manage().window().maximize();
     Thread.sleep(2000);
     
     //scrolling down till the grid
     JavascriptExecutor js = (JavascriptExecutor) driver;
     js.executeScript("window.scrollBy(0,7800)");
     ArrayList<WebElement> text = (ArrayList<WebElement>) driver.findElements(By.xpath("//ul//li[@class=\"games-item\"]//figcaption"));
     int i=0;
     System.out.println("#   "+"Game Name"+"                     Page URL"+"                                                                     Page Status"+"                                     Tournament Count");
     System.out.println("");
     
     //iterating over all the grid games to get the details
     for(WebElement e :text) {
     i++;
     System.out.print(i);
     String text1 = e.getText();
     System.out.print("   "+e.getText());
     Thread.sleep(3000);
     
     //opening the game in new tab
     driver.findElement(By.linkText(text1)).sendKeys(Keys.chord(Keys.CONTROL,Keys.ENTER));
     Thread.sleep(4000);
     ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
     
     //switching to the opened tab
     driver.switchTo().window(tabs.get(1));
     String url= driver.getCurrentUrl();
     System.out.print("       "+url);
     Thread.sleep(4000);
     
     //making get call for the url and fetching status code
     RequestSpecBuilder builder = new RequestSpecBuilder();          
     RequestSpecification requestSpec = builder.build();     
     Response response = RestAssured.given().get(url);
     int status =  response.getStatusCode();
     System.out.print("                                     "+status);
     
     //fetching tournament count
     String tournamentcount = driver.findElement(By.xpath("(//section[@class=\"tournaments\"]//span)[1]")).getText();
     System.out.print("           "+tournamentcount);
     
     //closing the opened tab
     driver.close();
     
     //switching to the default home page
     driver.switchTo().window(tabs.get(0)); 
     System.out.println("");
     }
     }		
}
