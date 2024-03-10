import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class GetData {
    WebDriver driver = new ChromeDriver();

    public static List<String> usunDuplikaty(List<String> lista) {
        HashSet<String> set = new HashSet<>(lista);
        return new ArrayList<>(set);
    }
    public static String generujNazwePliku() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        return dateFormat.format(data) + ".yaml";
    }
    public static Map<String, String> convertToMap(List<String> lista){
        Map<String, String> mapOfLinks = new HashMap<>();
        for (String link : lista) {
            mapOfLinks.put(link, "link");
        }
        return mapOfLinks;
    }
    public static void saveFromMapToYaml(Map<String, String> linksMap, String nazwaPliku) {

        Yaml yaml = new Yaml();

        try (FileWriter writer = new FileWriter(nazwaPliku)) {
            yaml.dump(linksMap, writer);
            System.out.println("Lista została zapisana do pliku: " + nazwaPliku);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static List<String> getLinkURLs (WebDriver driver, String xpath){
            // Znalezienie wszystkich linków o podanym XPath
            List<WebElement> links = driver.findElements(By.xpath(xpath));
            List<String> linkURLs = new ArrayList<>();

            // Pobranie adresów URL linków i dodanie ich do listy
            for (WebElement link : links) {
                String linkURL = link.getAttribute("href");
                linkURLs.add(linkURL);
            }
            // Zwrócenie listy adresów URL
            return linkURLs;
        }
        @BeforeEach
        public void driverSetup () {
            driver.get("https://www.otodom.pl/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-accept-btn-handler")));
            driver.findElement(By.id("onetrust-accept-btn-handler")).click();
            driver.findElement(By.id("location")).click();
            driver.findElement(By.id("location-picker-input")).sendKeys("Lębork");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//div[@id='__next']/main/section/div/div/form/div/div/div[3]/div/div/div/div[2]/ul/li/label")).click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.id("search-form-submit")).click();
            wait.until(ExpectedConditions.titleContains("Mieszkania na sprzedaż: Lębork, lęborski | Otodom.pl"));
        }
        @Test
        public void LoginTest () {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            String howManyFlatsString = driver.findElement(By.xpath("//div[@id=\"__next\"]/div[2]/main/div/div[2]/div[3]/div[1]/div[1]/div/div/div[1]/div/div")).getText();
            String[] splited = howManyFlatsString.split(" ");
            String lastElement = splited[splited.length - 1];
            String firstElemet = splited[0];
            String lastTwoChars = firstElemet.substring(firstElemet.length() - 2);
            int howManyFlatsInt = Integer.parseInt(lastElement);
            String element = driver.findElement(By.cssSelector("#__next > div.css-1i02l4.egbyzpx4 > main > div > div.css-1d0gimt.egbyzpx2 > div.css-feokcq.egbyzpx5 > div.e1bc0sac0.css-1cbo1bu > div.css-1406so7.e1bc0sac4 > div.css-18budxx.e18zekwa0 > ul > li:nth-child(7)")).getText();
            int siteCounter = Integer.parseInt(element);
            String currentURL = driver.getCurrentUrl();
            List<String> allLinkURLs = new ArrayList<>();
            List<String> linkURLs = getLinkURLs(driver, "//article//section/div[1]/div/div[2]/div/div/div[1]/div/div[1]/a");
            allLinkURLs.addAll(linkURLs);
            int counter = 2;
            while (counter <= siteCounter) {
                driver.get(currentURL + "&page=" + counter);
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//article//section/div[1]/div/div[2]/div/div/div[1]/div/div[1]/a")));
                List<String> linkURLs1 = getLinkURLs(driver, "//article/section/div[1]/div/div[2]/div/div/div[1]/div/div[1]/a");
                allLinkURLs.addAll(linkURLs1);
                counter++;
            }
            usunDuplikaty(allLinkURLs);
            String data =generujNazwePliku();
            Map<String, String> linksMap = new HashMap<>(convertToMap(allLinkURLs));
            saveFromMapToYaml(linksMap, data);
            for (Map.Entry<String, String> entry : linksMap.entrySet()) {
                System.out.println("Klucz: " + entry.getKey() + ", Wartość: " + entry.getValue());
            }
            driver.quit();
        }
}


