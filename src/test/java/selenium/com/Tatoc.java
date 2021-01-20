package selenium.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Tatoc {
	WebDriver driver;
	public String url = "http://10.0.1.86/tatoc";
	
	@Test(priority=1)
	public void lunchUrl() {
		System.setProperty("webdriver.chrome.driver","C://Users//manishpatel//Downloads//chromeDriver//chromedriver.exe");
		if(driver==null)
		driver = new ChromeDriver();
		
		driver.get(url);
		System.out.println("Url launched. "+driver.getPageSource());
		
		driver.manage().window().maximize();	
	}
	
	@Test(priority=2)
	public void selectCourse() {
		driver.findElement(By.xpath("//a[text()='Basic Course']")).click();
		System.out.println("Clicked on 'Basic course' button");
	}
	
	@Test(priority=3)
	public void gridGate() throws InterruptedException {
		Thread.sleep(4000);
		driver.findElement(By.xpath("//div[@class='greenbox']")).click();
		System.out.println("Clicked on 'Green' box");
	}
	
	@Test(priority=4)
	public void frameDungeon() throws InterruptedException {
		Thread.sleep(4000);
		WebElement mainFrame = driver.findElement(By.xpath("//iframe[@id='main']"));
		driver.switchTo().frame(mainFrame);
		System.out.println("Switched to main frame");
		Thread.sleep(1000);
		String box1Color = driver.findElement(By.xpath("//div[text()='Box 1']")).getAttribute("class");
		System.out.println("Box 1 color: "+box1Color);

		WebElement childFrame = driver.findElement(By.xpath("//iframe[@id='child']"));
		driver.switchTo().frame(childFrame);
		System.out.println("Switched to child frame");
		Thread.sleep(1000);
		String box2Color = driver.findElement(By.xpath("//div[text()='Box 2']")).getAttribute("class");
		System.out.println("Box 1 color: "+box2Color);
		
		while(!box1Color.equals(box2Color)) {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(mainFrame);
			driver.findElement(By.xpath("//a[text()='Repaint Box 2']")).click();
			System.out.println("Clicked on 'Repaint' box");
			driver.switchTo().frame(childFrame);
			box2Color = driver.findElement(By.xpath("//div[text()='Box 2']")).getAttribute("class");
			System.out.println("Box 1 color: "+box1Color+" Box 2 color: "+box2Color);
			if(box1Color.equals(box2Color)) {
				break;
			}
		}
		driver.switchTo().defaultContent();
		driver.switchTo().frame(mainFrame);
		driver.findElement(By.xpath("//a[text()='Proceed']")).click();
		System.out.println("Clicked on 'Proceed' button");
	}
	
	@Test(priority=5)
	public void dragAround() throws InterruptedException {
		Thread.sleep(2000);
		WebElement target = driver.findElement(By.xpath("//div[text()='DROPBOX']"));
		WebElement src = driver.findElement(By.xpath("//div[text()='DRAG ME']"));
		Actions act = new Actions(driver);
		act.dragAndDrop(src, target).build().perform();
		driver.findElement(By.xpath("//a[text()='Proceed']")).click();
		System.out.println("Clicked on 'Proceed' button");
	}
	
	@Test(priority=6)
	public void popupWindows() throws InterruptedException {
		Thread.sleep(2000);
		WebElement target = driver.findElement(By.xpath("//div[text()='DROPBOX']"));
		WebElement src = driver.findElement(By.xpath("//div[text()='DRAG ME']"));
		Actions act = new Actions(driver);
		act.dragAndDrop(src, target).build().perform();
		driver.findElement(By.xpath("//a[text()='Proceed']")).click();
		System.out.println("Clicked on 'Proceed' button");
	}
}
