package stepdefinitions;

import Methods.AddToExcel;
import Methods.CruiseNavigation;
import Methods.HomeNavigation;
import Methods.WebDriverFactory;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StepDefinition{
    static WebDriver driver;
    static List<List<String>> homeList;
    static List<String> cruiseList;
    static JsonNode data;
    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readTree(new File("src/test/resources/testdata.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final Logger logger = LogManager.getLogger(StepDefinition.class);

    @Before
    public static void setup(){
        driver = WebDriverFactory.getDriver();
        logger.info("browser initiated");
    }

    //common for both test cases
    @Given("the cruise webpage is opened")
    public static void cruiseStartup(){
        //Change website based on preference
        String cruiseURL = data.path("cruise").path("url").asText();
        driver.get(cruiseURL);
        logger.info("navigated to cruises.booking.com");
    }

    @Given("the home webpage is opened")
    public static void homeStartup(){
        //Change website based on preference
        String homeURL = data.path("holiday").path("url").asText();
        driver.get(homeURL);
        logger.info("navigated to www.booking.com");
    }

    //holiday homes
    @When("the user searches for holiday home")
    public static void searchHome() throws Exception {
        int days = data.path("holiday").path("days").asInt();
        String location = data.path("holiday").path("location").asText();
        HomeNavigation.holidayHome(driver,days,location);
        logger.info("selected the hotels details in location {} for {} days ", location, days);
    }
    @And("the user selects the filters")
    public static void enableFilter() throws Exception {
        HomeNavigation.homeFilters(driver);
        logger.info("the filters are enabled");
    }
    @Then("the user collects the data of the homes")
    public static void getListOfHome() throws Exception{
        int noOfHomes = data.path("holiday").path("homes").asInt();
        homeList = HomeNavigation.getHomeList(driver,noOfHomes);
        logger.info("the list of homes are obtained");
    }


    //cruise ship
    @When("the user picks a cruise line")
    public static void selectCruiseLine(){
        CruiseNavigation.selectLine(driver);
        logger.info("the first cruise line is selected");
    }
    @And("the user picks a cruise ship")
    public static void selectCruiseShip(){
        CruiseNavigation.selectShip(driver);
        logger.info("the best rated cruiseship is selected");
    }
    @Then("the user collects the information of the ship")
    public static void getShipInfo() throws Exception {
        cruiseList = CruiseNavigation.getInfo(driver);
        logger.info("the infomation of the cruise ship is obtained");
    }
    
    // Create Excel with both sheets after scenario completes
    @After
    public static void createExcelWithBothSheets() {
        // If both data sets are available, create the Excel file
        if (homeList != null && cruiseList != null) {
            AddToExcel.addDataToExcel(homeList, cruiseList);
            logger.info("Excel file created with both Holiday Homes and Cruise Line sheets");
        }
        driver.quit();
        // If only one is available, wait until both are collected
        // before creating Excel file (no individual Excel files)
    }
}
