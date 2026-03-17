package Winbull.trade;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Bfun {

    public static void openURL(WebDriver driver, String url) {
        driver.get(url);
    }

    public static void type(WebDriver driver, By locator, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    public static void uploadFile(WebDriver driver, By locator, String filePath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        element.sendKeys(filePath);
    }

    public static void typel(WebDriver driver, By locator, List<String> values) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        element.clear();

        for (String value : values) {
            element.sendKeys(value);
            element.sendKeys(Keys.ENTER); // optional (for search fields)
        }
    }

    public static void click(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        try {
            element.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    public static String getToastMessageIfPresent(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement msg = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(
                            By.xpath("//span[contains(@class,'text-break')]")));
            return msg.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static void setComtraper(WebDriver driver, String tableId, String commodityName, String buy, String sell,
            String amount) {

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='" + tableId + "']/tbody/tr"));
        for (int i = 0; i < rows.size(); i++) {

            WebElement rowElement = rows.get(i);

            String uiCommodity = rowElement.findElement(By.xpath("./td[1]")).getText().trim();

            if (uiCommodity.equalsIgnoreCase(commodityName)) {

                WebElement buyCheckbox = rowElement.findElement(By.xpath("./td[4]//input[@type='checkbox']"));

                if (buy != null && buy.equalsIgnoreCase("yes")) {

                    if (!buyCheckbox.isSelected()) {
                        buyCheckbox.click();
                    }
                }

                WebElement sellCheckbox = rowElement.findElement(By.xpath("./td[5]//input[@type='checkbox']"));

                if (sell != null && sell.equalsIgnoreCase("yes")) {

                    if (!sellCheckbox.isSelected()) {
                        sellCheckbox.click();
                    }
                }

                WebElement amntCheckbox = rowElement.findElement(By.xpath("./td[6]//input[@type='checkbox']"));

                if (amount != null && amount.equalsIgnoreCase("yes")) {

                    if (!amntCheckbox.isSelected()) {
                        amntCheckbox.click();
                    }
                }

            }
        }
    }

    public static void setCommodityValues(WebDriver driver, String tableId, String commodityName, String buyValue,
            String sellValue, String diffTypeValue, String tradeBuyValue, String tradeSellValue, String buyPremiumValue,
            String sellPremiumValue, String deliveryDaysValue) {

        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='" + tableId + "']/tbody/tr"));

        for (int i = 0; i < rows.size(); i++) {

            WebElement rowElement = rows.get(i);

            String uiCommodity = rowElement.findElement(By.xpath("./td[1]")).getText().trim();

            if (uiCommodity.equalsIgnoreCase(commodityName)) {

                WebElement buyCheckbox = rowElement.findElement(By.xpath("./td[2]//input[@type='checkbox']"));

                if (buyValue != null && buyValue.equalsIgnoreCase("yes")) {

                    if (!buyCheckbox.isSelected()) {
                        buyCheckbox.click();
                    }

                    WebElement buyPremiumBox = rowElement.findElement(By.xpath("./td[5]//input"));
                    buyPremiumBox.clear();
                    buyPremiumBox.sendKeys(buyPremiumValue);
                    Main.test.log(Status.PASS, "BUY premium value is updated");
                }

                WebElement sellCheckbox = rowElement.findElement(By.xpath("./td[3]//input[@type='checkbox']"));

                if (sellValue != null && sellValue.equalsIgnoreCase("yes")) {

                    if (!sellCheckbox.isSelected()) {
                        sellCheckbox.click();
                    }

                    WebElement sellPremiumBox = rowElement.findElement(By.xpath("./td[6]//input"));
                    sellPremiumBox.clear();
                    sellPremiumBox.sendKeys(sellPremiumValue);
                    Main.test.log(Status.PASS, "SELL premium value is updated");

                }

                if (diffTypeValue != null && !diffTypeValue.trim().isEmpty()) {

                    WebElement radio = rowElement.findElement(By.xpath(".//input[contains(@name,'com_premium_type') and @value='0']"));
                    WebElement radio1 = rowElement.findElement(By.xpath(".//input[contains(@name,'com_premium_type') and @value='1']"));

                    if (diffTypeValue.equalsIgnoreCase("auto")) {

                        if (!radio.isSelected()) {
                            try {
                                radio.click();
                            } catch (Exception e) {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
                            }
                        }
                        Main.test.log(Status.PASS, "Diff Type set to AUTO for: " + commodityName);
                    }

                    if (diffTypeValue.equalsIgnoreCase("manual")) {

                        if (!radio1.isSelected()) {
                            try {
                                radio1.click();
                            } catch (Exception e) {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio1);
                            }
                        }
                        Main.test.log(Status.PASS, "Diff Type set to MANUAL for: " + commodityName);
                    }
                }

                WebElement tradeBuyCheckbox = rowElement.findElement(By.xpath("./td[7]//input[@type='checkbox']"));

                if (tradeBuyValue != null && tradeBuyValue.equalsIgnoreCase("yes")) {

                    if (!tradeBuyCheckbox.isSelected()) {
                        tradeBuyCheckbox.click();
                    }
                }

                WebElement tradeSellCheckbox = rowElement.findElement(By.xpath("./td[8]//input[@type='checkbox']"));

                if (tradeSellValue != null && tradeSellValue.equalsIgnoreCase("yes")) {

                    if (!tradeSellCheckbox.isSelected()) {
                        tradeSellCheckbox.click();
                    }
                }

                if (deliveryDaysValue != null && !deliveryDaysValue.isEmpty()) {

                    WebElement deliveryBox = rowElement.findElement(By.xpath("./td[9]//input"));
                    deliveryBox.clear();
                    deliveryBox.sendKeys(deliveryDaysValue);
                }

                break;
            }
        }
    }

    public static boolean clickEdit(WebDriver driver, String dispName) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            By editBtn = By.xpath("//table//tbody//tr[td[2][normalize-space()='" + dispName + "']]//td[7]/a[1]");

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(editBtn));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

            return true;

        } catch (Exception e) {
            System.out.println("Edit not clicked: " + e.getMessage());
            return false;
        }
    }

    public static String getText(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public static void scrollToElement(WebDriver driver, By locator) {

        WebElement element = driver.findElement(locator);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public static void enableCheckbox(WebDriver driver, By id) {

        WebElement checkbox = driver.findElement(id);
        if (!checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    public static void setCheckbox(WebDriver driver, By locator, String value) {

        WebElement checkbox = driver.findElement(locator);

        boolean shouldBeChecked = value.equalsIgnoreCase("yes");

        if (checkbox.isSelected() != shouldBeChecked) {
            checkbox.click();
        }
    }

    public static void selectByText(WebDriver driver, By locator, String text) {
        WebElement dropdownElement = driver.findElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(text);
    }

    public static void setCheckboxWithExcelValue(WebDriver driver, By checkboxLocator, By inputLocator, String excelValue) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkboxLocator));
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLocator));

            if (excelValue != null && !excelValue.trim().isEmpty()) {

                if (!checkbox.isSelected()) {
                    wait.until(ExpectedConditions.elementToBeClickable(checkbox));
                    checkbox.click();
                }

                input.clear();
                input.sendKeys(excelValue);

            } else {

                if (checkbox.isSelected()) {
                    checkbox.click();
                }
            }

        } catch (Exception e) {
            System.out.println("Checkbox handling error: " + e.getMessage());
        }
    }

    public static void setCheckboxWithBSValue(WebDriver driver, String tableId, String commodityName, String buy, String sell, String excelValue, String excelValue1) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            List<WebElement> rows = driver.findElements(By.xpath("//table[@id='" + tableId + "']/tbody/tr"));
            System.out.println(tableId);
            for (int i = 0; i < rows.size(); i++) {

                WebElement rowElement = rows.get(i);

                String uiCommodity = rowElement.findElement(By.xpath("./td[1]")).getText().trim();
                if (uiCommodity.equalsIgnoreCase(commodityName)) {

                    WebElement buyInput = rowElement.findElement(By.xpath(".//td[2]//input"));
                    WebElement buyCheckbox = rowElement.findElement(By.id("prem_combuy_active"));
                    if (excelValue != null && !excelValue.trim().isEmpty()) {

                        if (!buyCheckbox.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buyCheckbox);
                        }

                        buyInput.clear();
                        buyInput.sendKeys(excelValue);

                    } else {

                        if (buyCheckbox.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buyCheckbox);
                        }
                    }

                    WebElement sellInput = rowElement.findElement(By.xpath("./td[3]//input"));
                    WebElement sellCheckbox = rowElement.findElement(By.id("prem_comsell_active"));

                    if (excelValue1 != null && !excelValue1.trim().isEmpty()) {

                        if (!sellCheckbox.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sellCheckbox);
                        }

                        sellInput.clear();
                        sellInput.sendKeys(excelValue1);

                    } else {

                        if (sellCheckbox.isSelected()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sellCheckbox);
                        }
                    }

                    break; // commodity matched and processed; no need to continue
                }
            }

        } catch (Exception e) {
            System.out.println("Checkbox handling error: " + e.getMessage());
        }
    }

    public static void setvalidTillDate(WebDriver driver, By locator, String date) {
        String DoM = date;
        String[] Date = DoM.split("-");
        String year = Date[2].toString();
        int month = Integer.parseInt(Date[1]);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        String mon = months[(month) - 1];
        String mOnTh = mon.substring(0, 3);
        System.out.println(mOnTh);
        String date1 = Date[0].toString();
        driver.findElement(By.xpath("//*[@class=\"datepicker\"]")).click();

        for (int i = 1; i <= 12; i++) {
            String monthDate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/table/thead/tr/th[2]")).getText();
            System.out.println("MonthDate is " + monthDate);
            if (monthDate.contains(year)) {
                System.out.println("Year Matches");

                System.out.println(mOnTh);
                driver.findElement(By.xpath("//span[contains(text(),'" + mOnTh + "')]")).click();

                System.out.println(date);
                driver.findElement(By.xpath("//td[@class='day' and contains(text(),'" + date1 + "')]")).click();
                break;
            } else {
                WebElement prevBtn = driver.findElement(By.xpath("//th[@class='prev']"));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", prevBtn);
            }
        }
    }

    public static void setRadioButton(WebDriver driver, By locator) {
        WebElement radio = driver.findElement(locator);
        if (!radio.isSelected()) {
            radio.click();
        }
    }

    public static void setValidTillDate(WebDriver driver, By locator, String date) {

        WebElement dateField = driver.findElement(locator);

        dateField.clear();
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.TAB); // triggers blur event

    }

    public static void scrollToBottom(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(1000, document.body.scrollHeight)");

    }

    public static boolean setCustomerGroup(WebDriver driver, String customerName, String groupName) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // 1. CLEAR then FILTER: clear the search box, then type the customer name
            WebElement searchBox = driver.findElement(By.id("customersearch"));
            searchBox.clear();
            searchBox.sendKeys(customerName);

            // Give the table a moment to filter the new DOM elements 
            Thread.sleep(1000);

            // 2. RETRIEVE THE ROW: Grab the now-filtered list of rows 
            List<WebElement> tableRows = driver.findElements(By.xpath("//table[@id='data_grid']/tbody/tr"));

            for (WebElement tableRow : tableRows) {

                String uiCustomer = tableRow.findElement(By.xpath("./td[2]")).getText().trim();

                if (uiCustomer.equalsIgnoreCase(customerName)) {

                    // 3. TARGET THE DROPDOWN: Find the specific dropdown for this filtered target row
                    WebElement groupDropdown = tableRow.findElement(
                            By.xpath("./td[4]//select[contains(@class,'cgitems_comgroupid')]"));

                    // Wait until it is clickable, then apply the select value
                    wait.until(ExpectedConditions.elementToBeClickable(groupDropdown));
                    Select select = new Select(groupDropdown);

                    // Print all available options for debugging
                    System.out.print("Available groups for " + customerName + ": ");
                    for (WebElement opt : select.getOptions()) {
                        System.out.print("[" + opt.getText().trim() + "] ");
                    }
                    System.out.println();

                    // Try exact match first, then case-insensitive partial match as fallback
                    boolean selected = false;
                    for (WebElement opt : select.getOptions()) {
                        if (opt.getText().trim().equalsIgnoreCase(groupName)) {
                            select.selectByVisibleText(opt.getText().trim());
                            selected = true;
                            break;
                        }
                    }
                    if (!selected) {
                        // Fallback: partial match
                        for (WebElement opt : select.getOptions()) {
                            if (opt.getText().trim().toLowerCase().contains(groupName.toLowerCase())) {
                                select.selectByVisibleText(opt.getText().trim());
                                selected = true;
                                System.out.println("Partial match used: '" + opt.getText().trim() + "' for requested: '" + groupName + "'");
                                break;
                            }
                        }
                    }

                    if (selected) {
                        System.out.println("Successfully set group '" + groupName + "' for customer: " + customerName);
                        return true;
                    } else {
                        System.out.println("No matching group option found for: '" + groupName + "' (customer: " + customerName + ")");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in setCustomerGroup: " + e.getMessage());
        }

        return false;
    }

    public static void selectDropdownCaseInsensitive(WebDriver driver, By locator, String text) {
        WebElement dropdownElement = driver.findElement(locator);
        Select select = new Select(dropdownElement);

        // Print available options for debugging
        List<WebElement> options = select.getOptions();
        System.out.print("  Dropdown options: ");
        for (WebElement opt : options) {
            System.out.print("[" + opt.getText().trim() + "] ");
        }
        System.out.println();

        for (WebElement opt : options) {
            if (opt.getText().trim().equalsIgnoreCase(text)) {
                select.selectByVisibleText(opt.getText().trim());
                System.out.println("  Selected: " + opt.getText().trim());
                return;
            }
        }

        for (WebElement opt : options) {
            if (opt.getText().trim().toLowerCase().contains(text.toLowerCase())) {
                select.selectByVisibleText(opt.getText().trim());
                System.out.println("  Partial match selected: " + opt.getText().trim() + " for: " + text);
                return;
            }
        }

        throw new RuntimeException("No dropdown option found matching: '" + text + "'");
    }

    public static void selectDropdownByValue(WebDriver driver, By locator, String value) {

        WebElement dropdown = driver.findElement(locator);

        Select select = new Select(dropdown);

        select.selectByValue(value);
    }

    public static void waiT(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void captureScreenshot(WebDriver driver, String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File("Screenshot/" + testName + ".png");

        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

}
