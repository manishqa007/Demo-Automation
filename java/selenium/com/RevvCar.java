package selenium.com;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class RevvCar {
	WebDriver driver;
		
//	    1. launch https://www.revv.co.in/open
//		2. click on drop down icon in front of select city
//		3. Select 1st city from the list 
//		4. click on Explore cars
//		5. scroll to bottom of the next page
//		6. repeat above steps after selecting next city till top 5 cities.

	@Test(priority=1)
	public void launchUrl() {
		System.setProperty("webdriver.chrome.driver", "C://Users//manishpatel//Downloads//chromedriver//chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get("https://www.revv.co.in/open");
		driver.manage().window().maximize();
		System.out.println("Launched url");
	}
	
	@Test(priority=2)
	public void dropDownInFrontOfSelectCity() {
		driver.findElement(By.xpath("(//div[@class='citySelection gtm_open_city_section tracking_open_selectCy']//input)[2]")).click();
		System.out.println("Clicked on select city drop down");	
	}
	
	@Test(priority=3)
	public void select1stcity() throws InterruptedException {
		int xpathCount = driver.findElements(By.xpath("//span[@class=\"cityListItem\"]")).size();
		System.out.println(">>>>>>>>>"+xpathCount);
		boolean flag = false;
		for (int i = 1; i <=xpathCount; i++) {
			if(flag==true) {
				Thread.sleep(1000);
				driver.findElement(By.xpath("(//div[@class='citySelection gtm_open_city_section tracking_open_selectCy']//input)[2]")).click();
			}
			String cityXpath = "(//span[@class='cityListItem']/div/div/div)[" + i +"]";
			String cityName = driver.findElement(By.xpath(cityXpath)).getText();
			
			System.out.println("Selected the "+i+" city"+cityName);
			driver.findElement(By.xpath(cityXpath)).click();
			driver.findElement(By.xpath("(//div[@class='label1 tracking_open_brandN']//h2)[1]")).click();
			
			boolean card = false;
//			while(!card) {
//				Thread.sleep(3000);
//				card = driver.findElement(By.xpath("(//img[@class='plp-value-prop-card'])[1]")).isDisplayed();
//			}
			Thread.sleep(3000);
			System.out.println("Card is displayed");
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,15000)");
			
			Thread.sleep(1000);
			boolean textSocial = driver.findElement(By.xpath("//h4[text()='Social']")).isDisplayed();
			System.out.println("textSocial: "+textSocial);
			
			driver.navigate().back();
			flag = true;
			
		}		
	}

}
