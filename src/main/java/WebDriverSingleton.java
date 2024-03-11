import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class WebDriverSingleton {
    private static WebDriver driver;


    private WebDriverSingleton() {
    }

    public static WebDriver getWebDriverInstance() {
        if (driver == null) {
            driver = new ChromeDriver();
        }
        return driver;
    }
}
