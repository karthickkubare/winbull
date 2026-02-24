package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportUtil {

	private static PrintWriter writer;
	
	public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentSparkReporter sparkReporter;
public static void setup() throws IOException, InterruptedException {

      
        sparkReporter = new ExtentSparkReporter("BullionAutomationReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        System.out.println("Extent initialized");
}

	public static void startReport() throws Exception {
		writer = new PrintWriter(new FileWriter("AutomationBullionReport.html", false));
		writer.println("<html><head><title>Automation BULLION Report</title></head><body>");
		writer.println("<h2>Test Execution Report</h2>");
		writer.println("<table border='1'>");
		writer.println("<tr><th>Function Name</th><th>Status</th><th>Time</th><th>Status Count</th></tr>");
	}

	public static void logResult(String testName, String status, int count) {
		writer.println("<tr>");
		writer.println("<td>" + testName + "</td>");
		writer.println("<td>" + status + "</td>");
		writer.println("<td>" + LocalDateTime.now() + "</td>");
		writer.println("<td>" + count + "</td>");
		writer.println("</tr>");
	}

	public static void endReport() {
		writer.println("</table></body></html>");
		writer.close();
	}
}
