import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;


public class SampleTest {

    private AndroidDriver driver;
    private WebDriverWait wait;


    By tvInfoBy = By.id("com.example.myapplication:id/tvInfo");
    By etPassCodeBy = By.id("com.example.myapplication:id/etPassCode");
    By etUsernameBy = By.id("com.example.myapplication:id/etUsername");
    By btContinueBy = By.id("com.example.myapplication:id/btContinue");


    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:deviceName", "Pixel_3a_API_32_arm64-v8a");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        desiredCapabilities.setCapability("appium:platformVersion", "12");
        desiredCapabilities.setCapability("appium:skipUnlock", "false");
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        desiredCapabilities.setCapability("appium:app", System.getProperty("user.dir") + "/src/test/resources/files/sampleApp.apk");
        //If the app have already installed, these lines can usable
        //desiredCapabilities.setCapability("appPackage", "com.example.myapplication");
        //desiredCapabilities.setCapability("appActivity", "com.example.myapplication.MainActivity");

        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testSuccessCondition() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tvInfoBy));
        wait.until(ExpectedConditions.visibilityOfElementLocated(etUsernameBy));
        wait.until(ExpectedConditions.visibilityOfElementLocated(etPassCodeBy));
        wait.until(ExpectedConditions.visibilityOfElementLocated(btContinueBy));

        driver.findElement(etUsernameBy).sendKeys("username");
        driver.findElement(etPassCodeBy).sendKeys("passcode");
        driver.hideKeyboard();
        driver.findElement(btContinueBy).click();
        String actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(tvInfoBy)).getText();
        Assert.assertEquals(actualText, "Congrats!");
    }

    @Test
    public void testFailCondition() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(tvInfoBy));
        wait.until(ExpectedConditions.visibilityOfElementLocated(etUsernameBy));
        wait.until(ExpectedConditions.visibilityOfElementLocated(etPassCodeBy));
        wait.until(ExpectedConditions.visibilityOfElementLocated(btContinueBy));

        driver.findElement(etUsernameBy).sendKeys("wrong");
        driver.findElement(etPassCodeBy).sendKeys("wrong");
        driver.hideKeyboard();
        driver.findElement(btContinueBy).click();
        String actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(tvInfoBy)).getText();
        Assert.assertEquals(actualText, "Please write right credentials");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}