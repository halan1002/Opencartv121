package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{
	WebDriver driver;
	public HomePage(WebDriver driver)
	{
		super(driver);//call driver from BasePage class
	}
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement lknMyAccount;
	
	@FindBy(xpath="//a[normalize-space()='Register']")
	WebElement lknRegister;
	
	@FindBy(linkText="Login")
	WebElement lknLogin;
	
	public void clickMyAccount()
	{
		lknMyAccount.click();
	}
	public void clickLogin()
	{
		lknLogin.click();
	}
	
	public void clickRegister()
	{
		lknRegister.click();
	}
	
	
}
