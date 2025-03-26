package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTest extends BaseClass{
	
	//getting data provider from different class
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups={"DataDriven","Master"})
	public void verifyLoginDataDriven(String email, String pwd, String expected) {
		
		logger.info("*** Starting TC003_LoginDataDrivenTest ***");
		try
		{
			// HomePage
			HomePage hp = new HomePage(driver);// create a new object HomePage
			hp.clickMyAccount();
			logger.info("*** Clicked on My Account link ***");
			hp.clickLogin();
			logger.info("*** Clicked on Login link ***");
	
			// LoginPage
			LoginPage lp = new LoginPage(driver);// create a new object - LoginPage
			lp.setEmail(email);// get data from excel file
			lp.setPassword(pwd);// get data from excel file
			lp.clickLogin();
			logger.info("*** Clicked on Login button ***");
	
			// MyAccountPage
			MyAccountPage myAccPage = new MyAccountPage(driver);// create a new object - MyAccountPage
			boolean targerPage = myAccPage.isMyAccountPageExisted();
			
			/*
			 Data is valid --> Login success --> test pass --> logout
			                --> Login fail --> test fail
			 
			 Data is invalid --> Login fail --> test pass
			                 --> Login success --> test FAIL --> logout
			 
			 */
			if(expected.equalsIgnoreCase("Valid"))
			{
				if(targerPage==true)
				{
					
					myAccPage.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			if(expected.equalsIgnoreCase("Invalid"))
			{
				if(targerPage==true)
				{
					
					myAccPage.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("*** End TC003_LoginDataDrivenTest ***");
	}

}
