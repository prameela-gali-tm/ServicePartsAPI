package com.toyota.scs.serviceparts;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import com.toyota.scs.serviceparts.reports.ExtentManager;
import com.toyota.scs.serviceparts.util.TestClass;
import com.toyota.scs.serviceparts.util.TestEnvironment;

public class BaseTest {

	public static ExtentReports rep = ExtentManager.startReport();
	public static ExtentTest test;
	ITestResult result;

	@BeforeClass
	public void PrintClassnameInreport() {

		System.out.println("test class started " + this.getClass().getName());
		System.out.println(".................................");
		System.out.println("................");
	}

	@AfterMethod
	public void Quit(ITestResult result) {
		rep.endTest(test);
		rep.flush();
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getThrowable());
		}
	}

	@BeforeTest()

	public void Intialize() {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		TestEnvironment.initializeEnvironent();
		ExtentManager.startReport().addSystemInfo("Environment", TestEnvironment.ENVIRONMENT);
	}

	@AfterClass
	public void addClassinfoToRun() {

	}

/*	@AfterSuite
	public void sendEmail() {

		// String to = "Logistics2@internal.toyota.com";
		String to = "prameela.gali@toyota.com";
		// Sender's email ID needs to be mentioned
		String from = "prameela.gali@toyota.com";

		// Assuming you are sending email from localhost
		String host = "smtp.services.toyota.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("TSCS API test Execution Results!");

			// Now set the actual message
			message.setText("This is actual message");
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText(" Results Attached..");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String file = System.getProperty("user.dir") + "\\test-output\\" + "extent.html";
			String filename = "Exucution Status.html";
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}*/

}
