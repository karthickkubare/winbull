package Bullion.Bullion_Automation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Functions {

    private WebDriver driver;
    private WebDriverWait wait;

   
    public Functions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ---------- CLICK ----------
    public void clickById(String id) {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id(id)));
            try {
                element.click();
                System.out.println(id + " has been clicked");
            } catch (Exception normalClickFailed) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);
            }
        } catch (Exception e) {
            System.out.println(id + " is not visible");
        }
    }

    public void clickByXpath(String xpath) {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            try {
                element.click();
                System.out.println("Xpath has been clicked");
            } catch (Exception normalClickFailed) {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);
            }
        } catch (Exception e) {
            System.out.println("Xpath is not visible: " + xpath);
        }
    }

    // ---------- ENTER TEXT ----------
    public void enterTextById(String id, String text) {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Text entry failed for ID: " + id);
        }
    }

    public void enterTextByXpath(String xpath, String text) {
        try {
            WebElement element = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Text entry failed for Xpath: " + xpath);
        }
    }

    public void enterTextByCss(String cssSelector, String text) {
        try {
            WebElement element = getElementByCss(cssSelector);
            element.clear();
            element.sendKeys(text);
            Thread.sleep(300);
            element.sendKeys(Keys.ENTER);
            System.out.println(text + " has been entered");
        } catch (Exception e) {
            System.out.println("Text entry failed for CSS: " + cssSelector);
        }
    }

    // ---------- GET ELEMENT ----------
    public WebElement getElementById(String id) {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        } catch (Exception e) {
            System.out.println(id + " is not visible");
            return null;
        }
    }

    public WebElement getElementByCss(String cssSelector) {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
        } catch (Exception e) {
            System.out.println("CSS not visible: " + cssSelector);
            return null;
        }
    }

    // ---------- MESSAGE ----------
    public String getSuccessMessage(String xpath) {
        try {
            WebElement message = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return message.getText();
        } catch (Exception e) {
            System.out.println("Success message path not found: " + xpath);
            return null;
        }
    }

    // ---------- BROWSER ----------
    public static WebDriver launchBrowserWithCameraPermission() {

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);

        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    // ---------- SCREENSHOT ----------
    public void captureScreenshot(String testName) {
        try {
            File folder = new File(
                    "D:\\Bullion\\bull");
            if (!folder.exists()) folder.mkdirs();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String destPath = folder + "\\" + testName + "_" + timestamp + ".png";
            FileHandler.copy(src, new File(destPath));

            System.out.println("Screenshot saved at: " + destPath);
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
