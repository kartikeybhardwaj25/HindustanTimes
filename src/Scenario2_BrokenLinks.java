import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Scenario2_BrokenLinks {
	ChromeDriver driver;
  @BeforeTest
  public void chromeIncognitoMode() {
// launching HindustanTimes Website in incognito mode	  
	  DesiredCapabilities capabilities = new DesiredCapabilities();
	  capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));

	  ChromeOptions options = new ChromeOptions();
	  options.addArguments("disable-infobars");
	  options.merge(capabilities);  //this will merge the capabilities to the ChromeOptions
	  System.setProperty("webdriver.chrome.driver","C:\\Selenium_Jars\\ChromeDriver\\chromedriver.exe");  
	  driver = new ChromeDriver(options);
	  
	  //Page Load Time
	  StopWatch pageLoad = new StopWatch();
      pageLoad.start();
	  driver.get("https://www.hindustantimes.com");
	  // we can apply explicit wait on particular element
	  pageLoad.stop();
      //Get the time
      long pageLoadTime_ms = pageLoad.getTime();
      long pageLoadTime_Seconds = pageLoadTime_ms / 1000;
      System.out.println("Total Page Load Time: " + pageLoadTime_ms + " milliseconds");
      System.out.println("Total Page Load Time: " + pageLoadTime_Seconds + " seconds");
	  
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	  
	  driver.manage().window().maximize();
	  
	  
	}
  @Test(priority =0)
  public void assertPagetitle() {
	 
	  String actualTitle = driver.getTitle();
	  System.out.println(actualTitle);
		
		 String expectedTitle ="News Headlines, English News, Today Headlines, Top Stories | Hindustan Times";
		 Assert.assertEquals(actualTitle, expectedTitle);
		 
	  
  }
  
  @Test(priority =1)
  public void extractHyperlink() throws IOException {
	//Get list of web-elements with tagName  - a
	  List<WebElement> allLinks = driver.findElements(By.tagName("a"));
	  
	  //Traversing through the list and printing its text along with link address
	  for(WebElement link:allLinks){
	  System.out.println(link.getText() + " - " + link.getAttribute("href"));
	  try
      {
	  URL url = new URL(link.getAttribute("href"));
	  HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
	  httpURLConnect.setConnectTimeout(5000);
	  httpURLConnect.connect();
	  System.out.println(link.getAttribute("href")+" - "+httpURLConnect.getResponseMessage());
	  System.out.println(link.getAttribute("href")+" - "+httpURLConnect.getResponseCode());
	  }	 
	  catch (Exception e) {
      }
	  }
	  
  }
  
 
  
  @AfterTest
  public void endsession() {
	  driver.quit();
  }
  
  
  
  
}
