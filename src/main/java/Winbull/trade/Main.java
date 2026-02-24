package Winbull.trade;

import java.io.IOException;
import Utils.PropertyUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {

	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentSparkReporter sparkReporter;

	public static void setup() throws IOException, InterruptedException {
		sparkReporter = new ExtentSparkReporter("BullionAutomationReport.html");
		extent = new ExtentReports();

		extent.attachReporter(sparkReporter);
		System.out.println("Extent initialized");
	}

	public static void main(String[] args) throws Exception {

		setup();
		WebDriver driver = null;
		String excelPath = PropertyUtil.getProperty("excelPath");
		String browser = PropertyUtil.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			throw new RuntimeException("Browser not supported: " + browser);
		}

		driver.manage().window().maximize();
		ExcelUtils.openExcel(excelPath);

		int pass = 0;
		int fail = 0;

		for (int i = 0; i < ExcelUtils.functions.size(); i++) {

			String functionName = ExcelUtils.functions.get(i);
			String executionStatus = ExcelUtils.execution.get(i);

			System.out.println(functionName);
			System.out.println(executionStatus);

			test = extent.createTest(functionName);

			switch (functionName.toLowerCase()) {

			case "login":
				if ("yes".equalsIgnoreCase(executionStatus)) {
					int[] result = Login.execute(driver);
					pass = result[0];
					fail = result[1];
					System.out.println("PASS : " + pass);
					System.out.println("FAIL : " + fail);
					ExcelUtils.updateMaster(i + 1, pass, fail);
				} else {
					System.out.println("Execution Fail");
				}
				break;

			case "trader":
				if ("yes".equalsIgnoreCase(executionStatus)) {

					int[] traderResult = Trader.execute(driver);
					pass = traderResult[0];
					fail = traderResult[1];
					ExcelUtils.updateMaster(i + 1, pass, fail);
					
				}
				break;


			case "rcommoditytype":
				if ("yes".equalsIgnoreCase(executionStatus)) {
					int[] result = RcommodityType.execute(driver);
					pass = result[0];
					fail = result[1];
					System.out.println("PASS : " + pass);
					System.out.println("FAIL : " + fail);
					ExcelUtils.updateMaster(i + 1, pass, fail);
				} else {
					System.out.println("Execution Fail");
				}
				break;


				
			default:
				System.out.println("No matching function for : ");
			}

			if (pass > 0) {
				test.pass("pass count" + pass);
			} else {
				test.fail("fail Count: " + fail);
			}

			
			ExcelUtils.saveExcel(excelPath);
		}

		Thread.sleep(2000);
		driver.quit();

		extent.flush();
	}

}
