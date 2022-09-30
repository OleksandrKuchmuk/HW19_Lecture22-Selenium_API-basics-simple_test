package firstTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class LoginTest {
    private static final Logger LOGGER = LogManager.getLogger(LoginTest.class);
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        LOGGER.info("Prepare our driver to work in @BeforeMethod");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void loginCorrectTest() {
        LOGGER.info("Start correct login test");
        String expectedResultWelcome = "Welcome to Wikipedia";
        String expectedResultLogin = "Cursor Student";
        String expectedResultUkraine = "Ukraine";

        LOGGER.info("configure our driver timeout");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LOGGER.info("Make our window to full screen");
        driver.manage().window().maximize();
        LOGGER.info("Open site Wikipedia ob Ukrainian language");
        driver.get("https://uk.wikipedia.org/");

        LOGGER.info("Find and press button to switch to English language");
        driver.findElement(By.xpath("//a[@lang='en']")).click();
        LOGGER.info("Find title of main page and check if we switched to english language");
        String actualResultWelcome = driver.findElement(By.xpath("//span[@id='Welcome_to_Wikipedia']")).getText();
        assertEquals(actualResultWelcome, expectedResultWelcome);

        LOGGER.info("Find 'Login' button and press");
        driver.findElement(By.xpath("//li[@id='pt-login']/a")).click();
        LOGGER.info("Find login input field, clear and enter our login ");
        driver.findElement(By.xpath("//input[@id='wpName1']")).clear();
        driver.findElement(By.xpath("//input[@id='wpName1']")).sendKeys("Cursor Student");
        LOGGER.info("Find password input and enter our password");
        driver.findElement(By.xpath("//input[@id='wpPassword1']")).sendKeys("cursorStudent");
        LOGGER.info("Find 'Remember me' checkbox and click on it");
        driver.findElement(By.xpath("//input[@class='mw-userlogin-rememberme']")).click();
        LOGGER.info("Find 'Log in' button and push");
        driver.findElement(By.xpath("//button[@id='wpLoginAttempt']")).click();
        LOGGER.info("Find field 'Your homepage' and check if we are logged in");
        String actualResultLogin = driver.findElement(By.xpath("//a[@title='Your homepage [alt-shift-.]']/span")).getText();
        assertEquals(actualResultLogin, expectedResultLogin);

        LOGGER.info("Find search bar and enter our search query");
        driver.findElement(By.xpath("//input[@class='vector-search-box-input']")).click();
        driver.findElement(By.xpath("//input[@class='vector-search-box-input']")).sendKeys("Ukraine");
        driver.findElement(By.xpath("//input[@class='vector-search-box-input']")).sendKeys(Keys.ENTER);
        LOGGER.info("We find the title of the article and check whether it corresponds to our search query");
        String actualResultUkraine = driver.findElement(By.xpath("//h1[@id='firstHeading']")).getText();
        assertEquals(actualResultUkraine, expectedResultUkraine);

        LOGGER.info("Log out from site");
        driver.findElement(By.xpath("//a[@title='Log out']")).click();
    }

    @Test
    public void loginIncorrectTest(){
        String expectedResultWelcome = "Ласкаво просимо до Вікіпедії,";
        String expectedResultLogin = "Incorrect username or password entered. Please try again.";

        LOGGER.info("configure our driver timeout");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LOGGER.info("Make our window to full screen");
        driver.manage().window().maximize();
        LOGGER.info("Open site Wikipedia ob Ukrainian language");
        driver.get("https://uk.wikipedia.org/");

        LOGGER.info("Find title of main page and check if we are on Ukrainian language");
        String actualResultWelcome = driver.findElement(By.xpath("//span[@id='Ласкаво_просимо_до_Вікіпедії,']")).getText();
        Assert.assertEquals(actualResultWelcome, expectedResultWelcome);
        LOGGER.info("Find and press button to switch to English language");
        driver.findElement(By.xpath("//a[@lang='en']")).click();
        LOGGER.info("Find login input field, clear and enter our login ");
        driver.findElement(By.xpath("//li[@id='pt-login']/a")).click();
        driver.findElement(By.xpath("//input[@id='wpName1']")).clear();
        driver.findElement(By.xpath("//input[@id='wpName1']")).sendKeys("Student");
        LOGGER.info("Find password input and enter our password");
        driver.findElement(By.xpath("//input[@id='wpPassword1']")).sendKeys("cursorStudent");
        LOGGER.info("Find 'Remember me' checkbox and click on it");
        driver.findElement(By.xpath("//input[@class='mw-userlogin-rememberme']")).click();
        LOGGER.info("Find 'Log in' button and push");
        driver.findElement(By.xpath("//button[@id='wpLoginAttempt']")).click();
        LOGGER.info("Find the error message, check its presence and the text of the message");
        String actualResultLogin = driver.findElement(By.xpath("//div[@class='mw-message-box-error mw-message-box']")).getText();
        Assert.assertEquals(actualResultLogin, expectedResultLogin);
    }

    @AfterMethod
    public void quit() {
        driver.quit();
    }
}