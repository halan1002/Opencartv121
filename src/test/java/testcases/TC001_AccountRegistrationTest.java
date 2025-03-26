package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.AccountRegistrationPage;
import pageobjects.HomePage;


public class TC001_AccountRegistrationTest extends BaseClass{

	
	@Test(groups={"Regression","Master"})
	public void verifyAccountRegistration() throws InterruptedException {
		logger.info("*** Starting TC001_AccountRegistrationTest ***");
		try {
			
		HomePage hp = new HomePage(driver);// create a new object
		hp.clickMyAccount();
		logger.info("*** Clicked on My Account link ***");
		hp.clickRegister();
		
		logger.info("*** Clicked on Register link ***");

		AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
		logger.info("*** Providing Customer Info ***");
		regPage.setFirstName(generateRandomString().toUpperCase());
		regPage.setLastName(generateRandomString().toUpperCase());
		regPage.setEmail(generateRandomString() + "@gmail.com");
		regPage.setTelephone(generateRandomNumber());

		String password = generateRandomAlphaNumber();
		regPage.setPassword(password);
		regPage.setConfirmPassword(password);

		regPage.setPolicy();
		
		Thread.sleep(1000);
		regPage.clickContinue();
		
		logger.info("*** Validating expected message ... ***");
		String confirmMsg = regPage.getConfirmationMsg();
		Thread.sleep(2000);
		if(confirmMsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("*** Test failed ... ***");
			logger.debug("*** Debug logs ... ***");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confirmMsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			
			Assert.fail();
		}
		logger.info("*** Finish TC001_AccountRegistrationTest ***");
	}

	
}
