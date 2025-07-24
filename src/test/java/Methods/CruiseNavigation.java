package Methods;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CruiseNavigation {
    //add codes respective to the method
//	WebDriver driver;
//	public CruiseNavigation(WebDriver driver) {
//		this.driver=driver;
//	}
	private static final Logger logger = LogManager.getLogger(CruiseNavigation.class);
    public static void selectLine(WebDriver driver) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.info("wait is interrupted");
		}
		driver.findElement(By.xpath("//div[@id='onetrust-button-group']/button[2]")).click();
		driver.findElement(By.xpath("//*[@id='slide-1']/a/div")).click();
	}

    public static void selectShip(WebDriver driver) {
    	
//    	List<WebElement> cruisesElements = driver.findElements(By.xpath(""));
    	
    	
    	try {
    		List<WebElement> cruiseRatingsElements = driver.findElements(By.xpath("//div[@class='ui-block-d wth2-reviewsSectionWrapper']"));
        	List<WebElement> cruiseElNamElements = driver.findElements(By.xpath("//div[@class='ui-block-c']//h2[starts-with(@id,'brochureName')]"));
//    		System.out.println(cruiseRatingsElements.size());
//    		System.out.println(cruiseElNamElements.size());
        	
        	double maxrate = 0;
    		int maxIndex= 0;
    		for (int i=0;i<cruiseElNamElements.size();i++) {
    			try {
    				WebElement ratElement = cruiseRatingsElements.get(i).findElement(By.xpath(".//span[starts-with(@id,'reviewRating')]"));
    				double elRate = Double.parseDouble(ratElement.getText());
            		if (elRate>maxrate) {
            			maxrate=elRate;
            			maxIndex=i;
						logger.info("The best rated ship is rated {}",maxrate);
            		}
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
    		}
    		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
    		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", cruiseElNamElements.get(maxIndex));
    		cruiseElNamElements.get(maxIndex).click();

    		
		} catch (Exception e) {
			// TODO: handle exception
            System.out.println(e.getMessage());
		}
    	
    }

    public static List<String> getInfo(WebDriver driver) throws Exception {
        //add return statement for this method
        List<String> cruiseList = new ArrayList<>();
		cruiseList.add(driver.findElement(By.id("sc_shipname")).getText()); //cruise name
		cruiseList.add(driver.findElement(By.id("sc_brochure_name")).getText()); //brochure name
		cruiseList.add(driver.findElement(By.id("sc_departure_port")).getText()); //departure post
		List<WebElement> portsOfCall = driver.findElements(By.className("wth2-portsOfCallListLi"));
		//add a loop to concat all the portOfCall in a String
		StringBuilder portsCall = new StringBuilder();
		for(WebElement ports: portsOfCall){
			portsCall.append(ports.getText());
			portsCall.append(":");
		}
		String portCalls = portsCall.toString();
		cruiseList.add(portCalls); //port of Calls
		cruiseList.add(driver.findElement(By.id("scLowLeadPrice")).getText()); //price
		cruiseList.add(driver.findElement(By.id("reviewRating")).getText()); //rating
		//Take the screenshot of the itinerary
		driver.findElement(By.id("expandCollapse_itin")).click();
		WebElement itineraryEle = driver.findElement(By.id("expandCollapse_itin_Container"));
		Thread.sleep(2000);
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destination = new File("itinerary.png");
		FileUtils.copyFile(screenshot,destination);
        return  cruiseList;
    }
}
