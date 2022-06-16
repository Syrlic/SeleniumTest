package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RemoteTest extends LocalTest {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("browserVersion", "90");
        chromeOptions.setCapability("enableVNC", true);
        chromeOptions.setCapability("enableVideo", false);
        chromeOptions.setCapability("screenResolution", "2560x1440x24");
        chromeOptions.setCapability("browserVersion", "90");
        chromeOptions.addArguments("--lang=en-US");
        try {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), chromeOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        driver.get("https://www.pixellu.com/");
        Boolean ready = new WebDriverWait(driver, 3)
                .until(d -> d.findElement(By.cssSelector(".-smartslides"))
                        .findElement(By.cssSelector(".boaster__text__footer__button")).isEnabled());
        Assertions.assertTrue(ready);
        driver.findElement(By.cssSelector(".-smartslides")).findElement(By.cssSelector(".boaster__text__footer__button")).click();
        driver.findElement(By.xpath("//input[@formcontrolname='email']")).sendKeys("hello22@world.com");
        driver.switchTo().frame("intercom-frame");
        driver.switchTo().defaultContent();
        String email = "rachmaninoff@gmail.com";
        driver.findElement(By.id("ulf-email-form")).findElement(By.cssSelector("input[formcontrolname=email]")).sendKeys(email);
        driver.findElement(By.id("ulf-email-form")).findElement(By.cssSelector("button[type=submit]")).click();
        Boolean paragraph = new WebDriverWait(driver, 5)
                .until(d -> d.findElement(By.cssSelector("p.ulf-profile-email")).getAttribute("style").contains("opacity: 1"));
        Assertions.assertTrue(paragraph);
        Assertions.assertEquals(email, driver.findElement(By.cssSelector("p.ulf-profile-email")).getText());
    }
}
