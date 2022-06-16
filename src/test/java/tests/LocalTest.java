package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LocalTest {

    protected WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\oklon\\IdeaProjects\\SeleniumTest\\src\\test\\resources\\chromedriver.exe");
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.addArguments("--lang=en-US");
        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 30);
    }

    @Test
    public void smartSlideTest() {
        driver.get("https://www.pixellu.com/");
        driver.findElement(By.cssSelector("a[href='/smartslides/examples/']")).click();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        String title = driver.findElement(By.tagName("h1")).getText();
        String utfString = new String(title.getBytes(Charset.forName("utf-8")));
        Assertions.assertEquals("Нам доверяют лучшие фотографы мира", utfString);
        String originalWindow = driver.getWindowHandle();
        WebElement element = driver.findElement(By.cssSelector("a[href='https://nordica_photography.smartslides.com/best-of-2017']"));
        element.click();
        wait.until(numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(titleIs("Best of 2017"));
        WebElement slideButton = new WebDriverWait(driver, 5)
                .until(d -> d.findElement(By.xpath("//a[@title='Play this Slideshow']")));
        slideButton.click();
        Boolean elementUp = new WebDriverWait(driver, 5)
                .until(d -> d.findElement(By.className("video-screen-wrapper")).getAttribute("style").contains("opacity: 1"));
        Assertions.assertTrue(elementUp);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.className("video-screen-wrapper"))).perform();
        driver.findElement(By.className("video-screen-wrapper")).click();
        actions.moveToElement(driver.findElement(By.className("video-screen-wrapper"))).perform();
        driver.findElement(By.className("video-screen-wrapper")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.className("video-screen-wrapper")).click();
    }

    @Test
    public void registerTest(){
        driver.get("https://www.pixellu.com");
        driver.findElement(By.xpath("//nav/ul/li/span[text()='Login']")).click();
        driver.findElement(By.xpath("//nav[@role='navigation']//ul[contains(@class,'-login')]//a[contains(@title, 'Log in to your Pixellu Account')]")).click();
        String email = "hello11@world.com";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']")));
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(d -> d.findElement(By.xpath("//p[@class='ulf-profile-email']")).getAttribute("style").contains("opacity: 1;"));
        Assertions.assertEquals(email, driver.findElement(By.xpath("//p[@class='ulf-profile-email']")).getText());
        driver.findElement(By.xpath("//div[@id='ulf-register-form']//input[@name='first_name']")).sendKeys("sherlock");
        driver.findElement(By.xpath("//div[@id='ulf-register-form']//input[@name='last_name']")).sendKeys("holmes");
        driver.findElement(By.xpath("//div[@id='ulf-register-form']//input[@name='studio_name']")).sendKeys("holmes");
        driver.findElement(By.xpath("//div[@id='ulf-register-form']//input[@name='password']")).sendKeys("holmes");
        driver.findElement(By.xpath("//div[@id='ulf-register-form']//input[@name='password2']")).sendKeys("holmes");
        WebElement checkbox = driver.findElement(By.xpath("//label[text()='I agree to ']"));
        new Actions(driver).moveToElement(checkbox, 50, 5).click().build().perform();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        //  wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[@class='page-title']")));
        //   driver.findElement(By.xpath("//a[contains(@title, 'Get Started with SmartSlides')]")).click();
    }

    @Test
    public void pixelluDragAndDropLocal() {
        driver.get("https://www.pixellu.com");
        driver.findElement(By.xpath("//nav/ul/li/span[text()='Login']")).click();
        driver.findElement(By.xpath("//nav[@role='navigation']//ul[contains(@class,'-login')]//a[contains(@title, 'Log in to your Pixellu Account')]")).click();
        String email = "hello11@world.com";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']")));
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(email);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(d -> d.findElement(By.xpath("//p[@class='ulf-profile-email']")).getAttribute("style").contains("opacity: 1;"));
        Assertions.assertEquals(email, driver.findElement(By.xpath("//p[@class='ulf-profile-email']")).getText());
        driver.findElement(By.xpath("//div[@id='ulf-login-form']//input[@name='password']")).sendKeys("holmes");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(d -> d.findElement(By.xpath("//footer//a[text()='Access SmartSlides']")).isEnabled());
        String originalWindow = driver.getWindowHandle();
        driver.findElement(By.xpath("//footer//a[text()='Access SmartSlides']")).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
            }
        }
        wait.until(ExpectedConditions.titleIs("SmartSlides"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='px-title']")));
        Assertions.assertEquals("Slideshows", driver.findElement(By.xpath("//div[@class='px-title']")).getText());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//app-slideshows-list//i")));
        driver.findElement(By.xpath("//app-slideshows-list//i")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//app-new-slideshow-dialog")));
        String nameShow = "formula1";
        driver.findElement(By.xpath("//app-new-slideshow-dialog//app-slideshow-options-form//input[@type='text']")).clear();
        driver.findElement(By.xpath("//app-new-slideshow-dialog//app-slideshow-options-form//input[@type='text']")).sendKeys(nameShow);
        driver.findElement(By.xpath("//app-new-slideshow-dialog//app-slideshow-options-form//button[@type='submit']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()=' 1974 ']")));
        //      driver.findElement(By.xpath("//div[text()='1974']//ancestor::datatable-body-cell[last()]")).click();
        WebElement bAdd = driver.findElement(By.xpath("//div[text()=' 1974 ']//ancestor::datatable-body-cell/following-sibling::datatable-body-cell[last()]//px-icon[@icon='px-add']//ancestor::div[@class='c-audio-controls__item']"));
        //new Actions(driver).moveToElement(bAdd).build().perform();
        new Actions(driver).moveToElement(bAdd).click().build().perform();
        driver.findElement(By.xpath("//div[text()=' 1974 ']//ancestor::datatable-body-cell/following-sibling::datatable-body-cell[last()]//px-icon[@icon='px-add']//ancestor::div[@class='c-audio-controls__item']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//app-waveform-player-trim//*[contains(@class, 'px-segment-audio-scrubber')]")));
    //    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//app-waveform-player-trim//div[@role='slider'][1]")));
        WebElement leftSlider = driver.findElement(By.xpath("//app-waveform-player-trim//*[contains(@class, 'px-segment-audio-scrubber')]"));
     //   WebElement rightSlider = driver.findElement(By.xpath("//app-waveform-player-trim//div[@role='slider'][1]"));
        new Actions(driver).moveToElement(leftSlider).dragAndDropBy(leftSlider, 150, 1).build().perform();
     //   new Actions(driver).dragAndDropBy(rightSlider, -30, 1);
        // JavascriptExecutor executor = (JavascriptExecutor) driver;
       // executor.executeScript("arguments[0].click();", bAdd);
//        WebElement bBack = driver.findElement(By.xpath("//px-audio-browser//*[text()=' Back to Slideshow Manager ']"));
//        new Actions(driver).moveToElement(bBack).click().build().perform();
//        wait.until(ExpectedConditions
//                .presenceOfElementLocated(By.xpath("//app-idz//div[@class='md-button-images']")));
//        driver.findElement(By.xpath("//app-slideshow-item//*[text()=' Continue Editing ']")).click();
     //   driver.findElement(By.xpath("//app-idz//div[@class='md-button-images']"));//.click();
    //    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  //      driver.findElement(By.xpath("//app-idz//div[@class='md-button-images']")).sendKeys("C:\\784.jpg");
    //    new Actions(driver).sendKeys("C:\\784.jpg", Keys.ENTER);
    }


    @AfterEach
    public void close() {
        driver.quit();
    }


}
