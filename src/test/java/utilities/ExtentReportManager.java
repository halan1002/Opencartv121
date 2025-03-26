package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testcases.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;// UI of the report
	public ExtentReports extent; // populate common info on the report
	public ExtentTest test;// creating test case entries in the report and update status of the methods
	
	String repName;
	
	public void onStart(ITestContext context) {
		
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);*/
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //the same 3 above statements
		repName="test-report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter (".\\reports\\" + repName); //specify report location
		
		sparkReporter.config().setDocumentTitle("OpenCart Automation Report");// Title of the report
		sparkReporter.config().setReportName("OpenCart Functional Tesing");// Name of the report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub-Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os=context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGroups=context.getCurrentXmlTest().getIncludedGroups();
		//in case we not define any group in xml file 
		//if not empty -> defined groups in xml
		if(!includeGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includeGroups.toString());
		}

	}

	// executed multiple times, any time when starting the test
	
	public void onTestSuccess(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());//create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); //to display group in the report
		test.log(Status.PASS, "Test case PASSED is: " +result.getName());//update status Pass/Fail/Skip
	}

	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, "Test case FAILED is: " +result.getName());
		test.log(Status.INFO, "Test case FAILED cause is: " +result.getThrowable().getMessage());
		
		//try
		//{
			//must create a new object for BaseClass because captureScreen is not a static method
			String imgPath=new BaseClass().captureScreen(result.getName());//file name includes name of method
			test.addScreenCaptureFromPath(imgPath);
		/*}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}*/
	}

	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.log(Status.SKIP, "Test case SKIPPED is: " +result.getName());
		test.log(Status.INFO, "Test case SKIPPED cause is: " +result.getThrowable().getMessage());
	}

	// executed only once
	public void onFinish(ITestContext context) {
		extent.flush();
		
		//auto open the report after finishing
		String pathOfExtentReport=System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport=new File(pathOfExtentReport);
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI()); //open file
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		/*
		//send the report via email to the team
		try
		{
			String strURL="file:////" + System.getProperty("user.dir") + "\\reports\\" + repName;
			URI uri = new URI(strURL);
			URL url=uri.toURL();
			
			//Create the email message
			ImageHtmlEmail email =new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("stmp.googleemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("abc@gmail.com", "test@123"));
			email.setSSLOnConnect(true);
			email.setFrom("abc@gmail.com");//sender
			email.setSubject("Test Results");
			email.setMsg("Please see the attached report....");
			email.addTo("xyz@gmail.com");
			email.attach(url,"Extent Report", "Please check report...");
			email.send();//send the email
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
	}

}
