package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;


public class AvicTests {

    private WebDriver driver;

    @BeforeTest
    public void  setUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkThatUserCanNotWriteEmptuFeedback(){
    driver.findElement(By.xpath("//a[contains(@class,'js_addMessage')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_addMessage")));
    driver.findElement(By.xpath("//a[contains(text(),'Директор')]")).click();
    driver.findElement(By.xpath("//div[@class='tab-content shown']//button[@type='submit']")).click();
    assertNotNull(driver.findElement(By.xpath("//div[@class='tab-content shown']//div[contains(@class,'error')] [2]")).isDisplayed());
    }

    @Test(priority = 2)
    public void checkFacebookLinkWork(){
        driver.findElement(By.xpath("//ul[contains(@class,'footer')]//a[contains(@href,'facebook')]")).click();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        assertTrue(driver.getCurrentUrl().contains("https://www.facebook.com/avicshop/"));
    }

    @Test(priority = 3)
    public void checkTelegramLinkWork(){
        driver.findElement(By.xpath("//ul[contains(@class,'footer')]//a[contains(@href,'t.me')]")).click();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        assertTrue(driver.getCurrentUrl().contains("https://t.me/OnlineShopAvicUa"));
    }
    @Test(priority = 4)
    public void checkInstagramLinkWork(){
        driver.findElement(By.xpath("//ul[contains(@class,'footer')]//a[contains(@href,'instagram')]")).click();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        assertTrue(driver.getCurrentUrl().contains("https://www.instagram.com/avicshop/"));
    }
    @Test(priority = 5)
    public void checkYoutubeLinkWork(){
        driver.findElement(By.xpath("//ul[contains(@class,'footer')]//a[contains(@href,'youtube')]")).click();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'ytd-topbar-logo-renderer')]")));
        assertTrue(driver.getCurrentUrl().contains("https://www.youtube.com/channel/UCeDZJkBQu8KMn3OPoU71HvA/featured"));
    }

    @Test(priority = 6)
    public void checkInStockAvailable(){
        driver.findElement(By.xpath("//span[contains(text(),'Apple Store')]")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div[@class='brand-box__title']//a[contains(@href,'apple-tv')]")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//div[@data-product='3435']//a[contains(@class,'main-btn')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_availableProductNotification")));
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//div[@class='col-xs-12']//button")).click();
        String textAlert = driver.findElement(By.xpath("//div[@id='modalAlert']//div[text()='Успешно!']")).getText();
        assertEquals(textAlert,"Успешно!");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
