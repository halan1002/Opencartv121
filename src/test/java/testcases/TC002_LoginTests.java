package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;

public class TC002_LoginTests extends BaseClass
{
	@Test(groups={"Sanity","Master"})
	public void verifyLogin()
	{
		
		logger.info("*** Starting TC002_LoginTests ***");
		try
		{
		//HomePage
		HomePage hp = new HomePage(driver);// create a new object HomePage
		hp.clickMyAccount();
		logger.info("*** Clicked on My Account link ***");
		hp.clickLogin();
		logger.info("*** Clicked on Login link ***");
		
		//LoginPage
		LoginPage lp = new LoginPage(driver);// create a new object - LoginPage
		lp.setEmail(p.getProperty("email"));//get data from properties file
		lp.setPassword(p.getProperty("password"));//get data from properties file
		lp.clickLogin();
		logger.info("*** Clicked on Login button ***");
		
		//MyAccountPage
		MyAccountPage myAccPage = new MyAccountPage(driver);// create a new object - MyAccountPage
		boolean targerPage=myAccPage.isMyAccountPageExisted();
		Assert.assertEquals(targerPage, true,"Login Failed");
		//Assert.assertTrue(targerPage);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		logger.info("*** Fisnish TC002_LoginTests ***");
		
	}
}
