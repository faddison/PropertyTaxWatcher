package ca.ubc.cs310.golddiggers.propertytaxwatcher.test;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleneseTestCase;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class UITest extends SeleneseTestCase {
	
	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://golddiggers310.appspot.com/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}
	
	
	@Test
	public void testFullApplication() throws Exception 
	{
		testSignIn();
		testHomePage();
		testSearchPage();
		//testTwitterPage();
		testComparePage();
		testDataPage();
		testAboutPage();
		testLogout();
	}
	
	
	
	//@Test
	public void testSignIn() throws Exception 
	{
		selenium.open("/");
		selenium.click("link=Sign In");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=Email", "golddiggers310");
		selenium.type("id=Passwd", "propertytax310");
		selenium.click("id=signIn");
		selenium.waitForPageToLoad("30000");
		//selenium.isElementPresent("class=\"twtr-profile-img-anchor\"");
		Thread.sleep(1000);
	}

	//@Test
	public void testHomePage() throws Exception
	{
		// home
		selenium.click("css=button.gwt-Button");
		Thread.sleep(1000);
	}

	//@Test
	public void testSearchPage() throws Exception
	{
		// search
		selenium.click("xpath=(//button[@type='button'])[2]");
		Thread.sleep(1000);
	}

	

	//@Test
	public void testComparePage() throws Exception {
		// compare
				selenium.click("xpath=(//button[@type='button'])[3]");
				Thread.sleep(1000);
	}

	//@Test
	public void testDataPage() throws Exception {
		// data
				selenium.click("xpath=(//button[@type='button'])[4]");
				Thread.sleep(1000);
	}

	//@Test
	public void testAboutPage() throws Exception {
		// about
		selenium.click("xpath=(//button[@type='button'])[5]");
		Thread.sleep(1000);
	}

	//@Test
	public void testLogout() throws Exception {
		// logout
		selenium.click("xpath=(//button[@type='button'])[6]");
		Thread.sleep(1000);
	}

	//@Test
	public void testTwitterPage() throws Exception {
		
	}
	
	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
