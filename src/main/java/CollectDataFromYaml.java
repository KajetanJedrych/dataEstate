import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CollectDataFromYaml {
    WebDriver driver = WebDriverSingleton.getWebDriverInstance();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    Map<String, String> mapOfData = new HashMap<>();
    String powierzchnia = "", liczba_pokoi="",pietro="",czynsz="",obsluga_zdalna="",forma_wlasnosci="",stan_wykonczenia="",
            balko_ogrod_taras="",miejsce_parkingowe="",ogrzewanie="",rynek="",typ_ogloszeniodawcy="",dostepne_od="",rok_budowy="",rodzaj_zabudowy="",
            okna="",winda="",media="",zabezpieczenia="",wyposazenie="",informacje_dodatkowe="",material_budynku="";


    @Test
    public void test(){
        try {
            Yaml yaml = new Yaml();
            FileInputStream fis = new FileInputStream("2024-03-11.yaml");

            // Wczytanie zawartości pliku YAML do mapy
            Map<String, String> yamlMap = yaml.load(fis);

            // Przejście przez mapę i wybór kluczy, których wartość to "link"
            for (Map.Entry<String, String> entry : yamlMap.entrySet()) {
                if (entry.getValue().equals("link")) {
                    System.out.println("Otwieranie linku: " + entry.getKey());
                    driver.get(entry.getKey());
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[1]/div[2]/div")));
                    powierzchnia = driver.findElement(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[1]/div[2]/div")).getText();
                    liczba_pokoi = driver.findElement(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[3]/div[2]/div/a")).getText();
                    pietro = driver.findElement(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[5]/div[2]/div")).getText();
                    czynsz = driver.findElement(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[7]/div[2]/div")).getText();
                    obsluga_zdalna = driver.findElement(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[9]/div[2]")).getText();
                    forma_wlasnosci = driver.findElement(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[2]/div[2]/div")).getText();
                    stan_wykonczenia = driver.findElement(By.xpath("//div[@id=\"__next\"]/main/div[2]/div[2]/div[2]/div/div[4]/div[2]")).getText();
                    System.out.println(powierzchnia);
                    System.out.println(liczba_pokoi);
                    System.out.println(pietro);
                    System.out.println(czynsz);
                    System.out.println(obsluga_zdalna);
                    System.out.println(forma_wlasnosci);
                    System.out.println(stan_wykonczenia);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
