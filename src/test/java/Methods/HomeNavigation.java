package Methods;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import stepdefinitions.StepDefinition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeNavigation {
    //add codes respective to the method
	private static final Logger logger = LogManager.getLogger(HomeNavigation.class);
    public static void holidayHome (WebDriver driver,int days,String location) throws Exception{
    	try {
			Thread.sleep(3000);
			WebElement loginBtn = driver.findElement(By.xpath("//button[@class='de576f5064 b46cd7aad7 e26a59bb37 c295306d66 c7a901b0e7 daf5d4cb1c']"));
			if(loginBtn.isDisplayed()) {
				loginBtn.click();
			}
		}
		catch(Exception e) {
			logger.warn("The pop-up may not be closed, rerun to fix the failed case");
		}
		
		driver.findElement(By.xpath("//input[@class='b915b8dc0b']")).sendKeys(location);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(text(),'"+location+"')]")).click();
		
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300);");
        
		LocalDate checkinDate = LocalDate.now().plusDays(1);
		LocalDate checkoutDate = checkinDate.plusDays(days);
		driver.findElement(By.xpath("//span[@data-date='" + checkinDate + "']")).click();
		driver.findElement(By.xpath("//span[@data-date='" + checkoutDate + "']")).click();
		driver.findElement(By.xpath("//button[@class='de576f5064 dc15842869 e84058595b b3d1de1c28']")).click();
		driver.findElement(By.xpath("//button[@class='de576f5064 b46cd7aad7 e26a59bb37 c295306d66 c7a901b0e7 aaf9b6e287 dc8366caa6']")).click();
		driver.findElement(By.xpath("//button[@class='de576f5064 b46cd7aad7 e26a59bb37 c295306d66 c7a901b0e7 aaf9b6e287 dc8366caa6']")).click();
		driver.findElement(By.xpath("//button[@class='de576f5064 b46cd7aad7 ced67027e5 dda427e6b5 e4f9ca4b0c ca8e0b9533 cfd71fb584 a9d40b8d51']")).click();
		Thread.sleep(2000);
		logger.info("Enter the info and searched for the homes");
    }

    public static void homeFilters(WebDriver driver) throws Exception{
    	Thread.sleep(5000);
		
		try {
			Thread.sleep(2000);
			WebElement loginBtn = driver.findElement(By.xpath("//button[@class='de576f5064 b46cd7aad7 e26a59bb37 c295306d66 c7a901b0e7 daf5d4cb1c']"));
			if(loginBtn.isDisplayed()) {
				loginBtn.click();
			}
		}
		catch(Exception e) {logger.info("no loginBtn displayed");}
		
		driver.findElement(By.xpath("//span[@class='a9918d47bf']")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,300);");
		
		driver.findElement(By.xpath("//span[contains(text(), 'Property rating (high to low)')]")).click();
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,300);");
		driver.findElement(By.xpath("//span[@class='fc70cba028 c2a70a5db2 ed13e01125']")).click();
		js.executeScript("window.scrollBy(0,250);");
		
		driver.findElement(By.xpath("//div[contains(text(), 'Wheelchair accessible')]")).click();
		logger.info("All the filters are selected");
    }

    public static List<List<String>> getHomeList(WebDriver driver,int numOfHomes) throws Exception {
        // add return statement for this method
		List<List<String>> homeLists = new ArrayList<>();
		Thread.sleep(1000);
		List<WebElement> hotelNames = driver.findElements(By.cssSelector("div[data-testid='title']"));
		List<WebElement> hotelPrices = driver.findElements(By.cssSelector("span[data-testid='price-and-discounted-price']"));
		List<WebElement> hotelRatings = driver.findElements(By.cssSelector("div.f63b14ab7a.dff2e52086"));
		logger.info("Got all the list of the hotels");
		for(int i=0;i<numOfHomes;i++){
			List<String> homeList = new ArrayList<>();
			homeList.add(hotelNames.get(i).getText()); //name
			homeList.add(hotelRatings.get(i).getText()); //rating
			homeList.add("5"); //no of people
			homeList.add(hotelPrices.get(i).getText()); //total price
			homeLists.add(homeList);
		}
		return homeLists;
    }
}
