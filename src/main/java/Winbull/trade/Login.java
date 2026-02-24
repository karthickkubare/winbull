package Winbull.trade;

import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;

public class Login {

	public static int[] execute(WebDriver driver) {

		int passCount = 0;
		int failCount = 0;

		int lastRow = ExcelUtils.loginSheet.getLastRowNum();// =================================>
		for (int i = 1; i <= lastRow; i++) {

			try {
				Row row = ExcelUtils.loginSheet.getRow(i);
				if (row == null)
					continue;

				String url = row.getCell(0).getStringCellValue();
				String user = row.getCell(1).getStringCellValue();
				String pwd = row.getCell(2).getStringCellValue();
			
				Bfun.openURL(driver, url);

				Bfun.type(driver, By.id("user_name"), user);
				Bfun.type(driver, By.id("user_password"), pwd);

				Bfun.scrollToElement(driver, By.id("login"));
				Bfun.click(driver, By.id("remember"));
				Bfun.click(driver, By.id("login"));

				String msg = Bfun.getText(driver, By.xpath("//h4[text()='Dashboard']")); // ==============================================>

				if ("Dashboard".equalsIgnoreCase(msg)) { // ======================================================>
					Main.test.log(Status.PASS, "Login successful for user: " + user);

				
					ExcelUtils.writeLoginResult(i, "Pass", msg);
					passCount++;
				} else {
					String error = Bfun.getText(driver, By.xpath("//span[text()='Invalid username or password']"));
					System.out.println(error);

					Main.test.log(Status.FAIL, "Login failed for user: " + user + " | Error: " + error);

					ExcelUtils.writeLoginResult(i, "Fail", error);
					Bfun.captureScreenshot(driver, "Login failed" + i);
					failCount++;
				}

				Bfun.refresh(driver);
				Thread.sleep(1000);

			} catch (Exception e) {
				Main.test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
				ExcelUtils.writeLoginResult(i, "Fail", "");
			
				failCount++;
			}

		}

		return new int[] { passCount, failCount };
	}
}
