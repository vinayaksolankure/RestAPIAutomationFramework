package api.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListenerClass implements ITestListener{
	
	ExtentSparkReporter htmlReporter; // user interface // look and feel of the report
	ExtentReports reports; // common information
	ExtentTest test; // entries for test
	
	public void configureReport()
	{
		String timestamp = new SimpleDateFormat("yyyy.mm.dd.hh.mm.ss").format(new Date());
		String reportName = "PetStoreAPIAutomationTestReport-" + timestamp + ".html";
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//ExtentReport//" + reportName);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);

		// add environment details
		reports.setSystemInfo("Machine", "DESKTOP-5BL66H4");
		reports.setSystemInfo("OS", "Windows 10 Pro");
		reports.setSystemInfo("User", "Vinayak Solankure");

		// Configuration to change look and feel
		htmlReporter.config().setDocumentTitle("Extent Report Listener Demo");
		htmlReporter.config().setReportName("APIFramework Test Execution");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	}
	
	// onStart method is called when any test starts.
	public void onStart(ITestContext context) {
		configureReport();
		System.out.println("On start method invoked...");
	}

	// onFinish method is called after all tests are executed.
	public void onFinish(ITestContext context) {
		System.out.println("On Finish method invoked...");
		reports.flush(); // Writes test information from the started reporters to their output view // Its mandatory.
	}

	// when test case get failed, this method is called.
	public void onTestFailure(ITestResult result) {
		System.out.println("Name of Failed test method: " + result.getName());
		test = reports.createTest(result.getName()); // Create entry in HTML report
		test.log(Status.FAIL, MarkupHelper.createLabel("Name of the failed test case is: " + result.getName(), ExtentColor.RED));
		test.fail(result.getThrowable());
		
		String screenShotPath = System.getProperty("user.dir") + "\\Screenshots\\" + result.getName() + ".png" ;
		File screenshotFile = new File(screenShotPath);
		
		if(screenshotFile.exists())
		{
			test.fail("Captured screenshot is below : " + test.addScreenCaptureFromPath(screenShotPath));
		}
		
		//test.addScreenCaptureFromPath(screenshotFile);
	}

	// when test case get skipped, this method is called.
	public void onTestSkipped(ITestResult result) {
		System.out.println("Name of Skipped test method: " + result.getName());
		test = reports.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel("Name of the skipped test case is: " + result.getName(), ExtentColor.YELLOW));
	}
 
	// when test case get started, this method is called.
	public void onTestStart(ITestResult result) {
		System.out.println("Name of Started test method: " + result.getName());
	}

	// when test case get pass, this method is called.
	public void onTestSuccess(ITestResult result) {
		System.out.println("Name of Successfully executed test method: " + result.getName());
		test = reports.createTest(result.getName());
		test.log(Status.PASS, MarkupHelper.createLabel("Name of the passed test case is: " + result.getName(), ExtentColor.GREEN));
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		//onTestFailure(result);
	}
}
