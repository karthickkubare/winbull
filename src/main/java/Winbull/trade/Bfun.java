package Winbull.trade;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Bfun {

	public static void openURL(WebDriver driver, String url) {
		driver.get(url);
	}

	public static void type(WebDriver driver, By locator, String value) {
		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(value);
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

	public static void setCheckboxByCommodityName(WebDriver driver, String tableId, String commodityName,
			int columnIndex, String excelValue) {

		if (excelValue == null)
			return;

// Accept both "yes" and "on"
		if (!(excelValue.equalsIgnoreCase("yes") || excelValue.equalsIgnoreCase("on"))) {
			return;
		}

		List<WebElement> rows = driver.findElements(By.xpath("//table[@id='" + tableId + "']/tbody/tr"));

		for (WebElement row : rows) {

			String uiCommodity = row.findElement(By.xpath("./td[1]")).getText().trim();

			if (uiCommodity.equalsIgnoreCase(commodityName)) {

				WebElement checkbox = row.findElement(By.xpath("./td[" + columnIndex + "]//input[@type='checkbox']"));

				if (!checkbox.isSelected()) {
					checkbox.click();
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
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = driver.findElement(locator);

		((JavascriptExecutor) driver)
				.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
		wait.until(ExpectedConditions.elementToBeClickable(locator));

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

	public static void setCheckboxWithExcelValue(WebDriver driver, By checkboxLocator, By inputLocator,
			String excelValue) {

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

	public static void setvalidTillDate(WebDriver driver, By locator, String date) {
		String DoM = date;
		String[] Date = DoM.split("-");
		String year = Date[2].toString();
		int month = Integer.parseInt(Date[1]);
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
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
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
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
