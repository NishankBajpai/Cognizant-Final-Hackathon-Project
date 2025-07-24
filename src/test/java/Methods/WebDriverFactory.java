package Methods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WebDriverFactory {
    public static WebDriver getDriver(){
        String browser = System.getProperty("browser","chrome");
        switch (browser.toLowerCase()) {
            case "chrome":return new ChromeDriver();
            case "edge": return new EdgeDriver();
            default: throw new IllegalArgumentException("Unsupported browser: "+browser);
        }
    }
}
