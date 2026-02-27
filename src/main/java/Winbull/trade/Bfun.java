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
	
	public static void setComtraper(WebDriver driver, String tableId, String commodityName, String buy,String sell, String amount) {

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
			String sellValue,String diffTypeValue, String tradeBuyValue, String tradeSellValue, String buyPremiumValue,
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

		                List<WebElement> radios = rowElement.findElements(
		                        By.xpath("./td[4]//input[@type='radio']"));

		                for (int j = 0; j < radios.size(); j++) {

		                    WebElement radio = radios.get(j);
		                    String value = radio.getAttribute("value");

		                    if (diffTypeValue.equalsIgnoreCase("auto")
		                            && value.equalsIgnoreCase("auto")) {

		                        if (!radio.isSelected()) {
		                            radio.click();
		                        }
		                    }

		                    if (diffTypeValue.equalsIgnoreCase("manual")
		                            && value.equalsIgnoreCase("manual")) {

		                        if (!radio.isSelected()) {
		                            radio.click();
		                        }
		                    }
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
