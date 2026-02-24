/*
 * package Winbull.trade;
 * 
 * import java.util.regex.Pattern; import java.util.concurrent.TimeUnit; import
 * org.openqa.selenium.*; import org.openqa.selenium.chrome.ChromeDriver; import
 * org.openqa.selenium.support.ui.Select; import
 * org.apache.commons.io.FileUtils; import org.apache.poi.ss.usermodel.Row;
 * import org.apache.poi.ss.usermodel.Sheet;
 * 
 * import java.io.File; import java.time.Duration;
 * 
 * public class Premium { public static int[] execute(WebDriver driver) throws
 * InterruptedException {
 * 
 * int passCount = 0; int failCount = 0;
 * 
 * By masters = By.xpath("//span[text()='Masters']"); Bfun.click(driver,
 * masters);
 * 
 * By trader = By.xpath("//span[text()='Trader (Customer & Supplier)']");
 * Bfun.click(driver, trader);
 * 
 * for (int i = 1; i <=1;i++ ) {
 * 
 * try { Row row = ExcelUtils.premiumSheet.getRow(i); if (row == null) continue;
 * 
 * String gname = row.getCell(1).getStringCellValue(); String gdes =
 * row.getCell(2).getStringCellValue(); String activeStatus =
 * row.getCell(3).toString().trim(); String updto =
 * row.getCell(4).getStringCellValue();
 * 
 * 
 * Bfun.click(driver,By.linkText("Masters"));
 * Bfun.click(driver,By.xpath("//nav[@id='sidebar']/ul/li[2]/ul/li[9]/a/span"));
 * 
 * Bfun.click(driver,By.linkText("Add"));
 * driver.findElement(By.id("prem_group_name")).clear();
 * 
 * Bfun.type(driver, By.id("prem_group_name"),"Rs100");
 * driver.findElement(By.id("prem_group_desc")).clear(); Bfun.type(driver,
 * By.id("prem_group_desc"),("hello")); if
 * (activeStatus.equalsIgnoreCase("Yes")) {
 * 
 * By yesRadio = By.xpath("//label[normalize-space()='Yes']");
 * 
 * if (!((Sheet) yesRadio).isSelected()) { Bfun.click(driver, yesRadio); }
 * 
 * } else {
 * 
 * WebElement noRadio = driver.findElement(
 * By.xpath("//label[normalize-space()='No']") );
 * 
 * if (!noRadio.isSelected()) { noRadio.click(); } };
 * driver.findElement(By.name("fv[prem_group_com][73][prem_combuy_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][73][prem_comsell_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][87][prem_combuy_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][87][prem_comsell_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][91][prem_combuy_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][91][prem_comsell_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][96][prem_combuy_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][96][prem_combuy_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][96][prem_combuy_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][96][prem_comsell_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][98][prem_combuy_active]")).
 * click();
 * driver.findElement(By.name("fv[prem_group_com][98][prem_comsell_active]")).
 * click(); driver.findElement(By.id("prem_buy_premium66")).click();
 * driver.findElement(By.id("prem_buy_premium66")).clear();
 * driver.findElement(By.id("prem_buy_premium66")).sendKeys("1");
 * driver.findElement(By.id("prem_sel_premium66")).clear();
 * driver.findElement(By.id("prem_sel_premium66")).sendKeys("2");
 * driver.findElement(By.id("prem_buy_premium73")).click();
 * driver.findElement(By.id("prem_buy_premium73")).clear();
 * driver.findElement(By.id("prem_buy_premium73")).sendKeys("3");
 * driver.findElement(By.id("prem_sel_premium73")).click();
 * driver.findElement(By.id("prem_sel_premium73")).clear();
 * driver.findElement(By.id("prem_sel_premium73")).sendKeys("4");
 * driver.findElement(By.id("prem_buy_premium87")).click();
 * driver.findElement(By.id("prem_buy_premium87")).clear();
 * driver.findElement(By.id("prem_buy_premium87")).sendKeys("5");
 * driver.findElement(By.id("prem_sel_premium87")).click();
 * driver.findElement(By.id("prem_sel_premium87")).clear();
 * driver.findElement(By.id("prem_sel_premium87")).sendKeys("6");
 * driver.findElement(By.id("prem_buy_premium91")).click();
 * driver.findElement(By.id("prem_buy_premium91")).clear();
 * driver.findElement(By.id("prem_buy_premium91")).sendKeys("7");
 * driver.findElement(By.id("prem_sel_premium91")).click();
 * driver.findElement(By.id("prem_sel_premium91")).clear();
 * driver.findElement(By.id("prem_sel_premium91")).sendKeys("8");
 * driver.findElement(By.id("prem_buy_premium96")).click();
 * driver.findElement(By.id("prem_buy_premium96")).clear();
 * driver.findElement(By.id("prem_buy_premium96")).sendKeys("9");
 * driver.findElement(By.id("prem_sel_premium96")).click();
 * driver.findElement(By.id("prem_sel_premium96")).clear();
 * driver.findElement(By.id("prem_sel_premium96")).sendKeys("10");
 * driver.findElement(By.id("prem_buy_premium98")).click();
 * driver.findElement(By.id("prem_buy_premium98")).clear();
 * driver.findElement(By.id("prem_buy_premium98")).sendKeys("11");
 * driver.findElement(By.id("prem_sel_premium98")).click();
 * driver.findElement(By.id("prem_sel_premium98")).clear();
 * driver.findElement(By.id("prem_sel_premium98")).sendKeys("12");
 * driver.findElement(By.xpath("//button[@type='submit']")).click();
 * driver.findElement(By.id("prem_group_desc")).click();
 * driver.findElement(By.id("prem_group_desc")).clear();
 * driver.findElement(By.id("prem_group_desc")).
 * sendKeys("hello welcome to the world of metals");
 * driver.findElement(By.xpath("//button[@type='submit']")).click(); driver.get(
 * "http://bullion_v4.logimaxindia.com/admin/index.php/C_prem_group/open_listingform"
 * ); driver.findElement(By.xpath("//input[@type='search']")).click();
 * driver.findElement(By.xpath("//input[@type='search']")).clear();
 * driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Rs100");
 * driver.findElement(By.xpath("//input[@type='search']")).sendKeys(Keys.ENTER);
 * driver.findElement(By.xpath("//table[@id='grid-data']/tbody/tr/td[2]")).click
 * ();
 * driver.findElement(By.xpath("//table[@id='grid-data']/tbody/tr/td[2]")).click
 * ();
 * 
 * } } }
 * 
 * @After public void tearDown() throws Exception { driver.quit(); String
 * verificationErrorString = verificationErrors.toString(); if
 * (!"".equals(verificationErrorString)) { fail(verificationErrorString); } }
 * 
 * private boolean isElementPresent(By by) { try { driver.findElement(by);
 * return true; } catch (NoSuchElementException e) { return false; } }
 * 
 * private boolean isAlertPresent() { try { driver.switchTo().alert(); return
 * true; } catch (NoAlertPresentException e) { return false; } }
 * 
 * private String closeAlertAndGetItsText() { try { Alert alert =
 * driver.switchTo().alert(); String alertText = alert.getText(); if
 * (acceptNextAlert) { alert.accept(); } else { alert.dismiss(); } return
 * alertText; } finally { acceptNextAlert = true; } } }
 */