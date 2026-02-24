package Bullion.Bullion_Automation;

import org.openqa.selenium.WebDriver;

import utils.ExcelUtils;

public class SheetRunner {

	public static void runMaster(WebDriver driver, ExcelUtils excel) {

		int lastRow = excel.getLastRow("Master");

		for (int i = 1; i <= lastRow; i++) {

			String module = excel.getCellData("Master", i, 1);
			String execute = excel.getCellData("Master", i, 2);

			if (!execute.equalsIgnoreCase("Yes")) {
				continue;
				fun[]=module;
				 System.out.println(fun);
			}
			 
		/*	int[] result;
			try {
				switch (module) {

				case "Login":
					result = Login.execute(driver, excel);
					break;
				default:
					excel.setCellData("Master", i, 3, "INVALID MODULE");
					continue;
				}

				excel.setCellData("Master", i, 3, "PASS-" + result[1] + " FAIL-" + result[0]);

			} catch (Exception e) {
				excel.setCellData("Master", i, 3, "ERROR");
				e.printStackTrace();
			}*/
		}
	}
}
