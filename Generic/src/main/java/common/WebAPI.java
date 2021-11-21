package common;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ExtentManager;
import reporting.ExtentTestManager;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.StringUtils.splitByCharacterTypeCamelCase;


public class WebAPI {    // This class is our configuration class

    public static ExtentReports extent;
    private static Object StringUtils;

    @BeforeSuite
    public void extentSetup(ITestContext context){
        ExtentManager.setOutputDirectory(context);
        extent = ExtentManager.getInstance();
    }
@BeforeMethod
    public void startExtent(Method method){
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName().toLowerCase();
        ExtentTestManager.startTest(method.getName());
        ExtentTestManager.getTest().assignCategory(className);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public void afterEachTestMethod(ITestResult result){
   ExtentTestManager.getTest().setStartedTime(getTime(result.getStartMillis()));
        ExtentTestManager.getTest().setEndedTime(getTime(result.getStartMillis()));
        for(String group :result.getMethod().getGroups()){
            ExtentTestManager.getTest().assignCategory(group);
        }
        if (result.getStatus() == 1){
            ExtentTestManager.getTest().log(LogStatus.PASS,"Test passed");
        }else if(result.getStatus() == 2){
          ExtentTestManager.getTest().log(LogStatus.FAIL,getStackTrace(result.getThrowable()));
        }else if(result.getStatus() == 3){
            ExtentTestManager.getTest().log(LogStatus.SKIP,"Test skipped");
        }
ExtentTestManager.endTest();
        extent.flush();
        if(result.getStatus() ==ITestResult.FAILURE){
            captureScreenShot(driver,result.getName());
        }
        driver.quit();
    }

  public void generateReport(){extent.close();}
    private Date getTime(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static void captureScreenShot(WebDriver driver,String screenshotName){
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
        Date date =new Date();
        df.format(date);
        File file =((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(file, new File(new File(System.getProperty("user.dir") + "/Screenshots") + screenshotName + df.format(date) + "png"));
System.out.println("Screenshot captured");
        }catch (Exception e){
            System.out.println("Exception while taking screenshot");
        }

    }
    public static String convertToString(String st){
        String splitString= " ";
//        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), " ");
    return splitString;
    }

//    ================================================================================================

    // Browser setup
  public static WebDriver driver = null;
    public static String browserStack_userName = "demo_qHnrzE";
    public static String browserStack_accessKey = "sQnzF5XthzRs5xuPvPZt";
    public static String sauceLabs_userName = "";
    public static String sauceLabs_accessKey = "";

  @Parameters({"useCloudEnv","cloudEnvName", "OS", "OS_version", "browserName", "browserVersion", "url"})
@BeforeMethod
  public void setUp(boolean useCloudEnv, String cloudEnvName, String OS, String OS_version,
                    String browserName, String browserVersion, String url) throws MalformedURLException{

      // set up method in 2nd way :In case of missing any parameter value in test runner file then better to use following pattern
//          public void setUp(@Optional("false") boolean useCloudEnv, @Optional ("sauceLabs") String cloudEnvName, @Optional("windows") String OS, @Optional("10") String OS_version,
//                      @Optional("firefox") String browserName, @Optional("89") String browserVersion, @Optional("www.google.com") String url) throws MalformedURLException


// platform : Local Machine/ Cloud Machine
        if(useCloudEnv == true){
           if(cloudEnvName.equalsIgnoreCase("browserStack")){
               getCloudDriver(cloudEnvName,browserStack_userName,browserStack_accessKey,OS,OS_version,browserName,browserVersion);
           }else if(cloudEnvName.equalsIgnoreCase("sauceLabs")){
               getCloudDriver(cloudEnvName,sauceLabs_userName, sauceLabs_accessKey,OS,OS_version,browserName,browserVersion);
           }
        }else{
            getLocalDriver(OS, browserName);
        }
       driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.get(url);
        driver.manage().window().maximize();
    }

    public WebDriver getLocalDriver( String OS, String browserName){
        if(browserName.equalsIgnoreCase("chrome")){
               if (OS.equalsIgnoreCase("OS X")){
                   System.setProperty("Webdriver.chrome. driver", "../Generic/BrowserDriver/Mac/chromedriver_mac64");
               } else if(OS.equalsIgnoreCase("windows")){
                   System.setProperty("Webdriver.chrome.driver", "..Generic/BrowserDriver/Windows/chromedriver.exe");
               }
              driver = new ChromeDriver();

        }else if(browserName.equalsIgnoreCase("chrome-option")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable- notifications");

                if (OS.equalsIgnoreCase("OS X")){
                    System.setProperty("Webdriver.chrome.driver", "..Generic/BrowserDriver/Mac/chromedriver_mac64");
                } else if(OS.equalsIgnoreCase("windows")){
                    System.setProperty("Webdriver.chrome. driver", "BrowserDriver/Windows/chromedriver.exe");
                }
                driver = new ChromeDriver(options);

        }else if(browserName.equalsIgnoreCase("firefox")) {
            if (OS.equalsIgnoreCase("OS X")) {
                System.setProperty("Webdriver.gecko. driver", "..Generic/BrowserDriver/Mac/geckodriver-v0.30.0-macos.tar.gz");
            } else if (OS.equalsIgnoreCase("windows")) {
                System.setProperty("Webdriver.gecko. driver", "..Generic/BrowserDriver/Windows/geckodriver-v0.30.0-win64");
            }
            driver = new FirefoxDriver();


        }
      return driver;
    }

    public WebDriver getCloudDriver(String envName,String envUsername,String envAccesskey, String OS,String OS_version,
                                    String browserName, String browser_Version) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "latest");

        if(envName.equalsIgnoreCase("SauceLabs")){
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + envAccesskey +
                    "@ondemand.saucelabs.com:80/wd/hub"), caps);
        }else if(envName.equalsIgnoreCase("Browserstack")){
            caps.setCapability("resolution","1024x768");
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ";" + envAccesskey +
                    "@hub-cloud.browserstack.com/wd/hub"), caps);
        }

     return driver;
    }
@AfterMethod(alwaysRun = true)
    public void cleanUp(){

      driver.quit();
    }

    public static void sleepFor( int seconds) throws InterruptedException {
      Thread.sleep(seconds*1000);
    }

    // Some Helper method for click
    public void clickOnElement(String locator){
      try{
          driver.findElement(By.cssSelector(locator)).click();
      }catch(Exception e){
          try {
              driver.findElement(By.className(locator)).click();
          }catch(Exception e1){
              try {
                  driver.findElement(By.id(locator)).click();
              }catch (Exception e2){
                  driver.findElement(By.xpath(locator)).click();
              }
          }
      }
    }

    // Some Helper method for type
    public void typeOnElement(String locator, String value){
      try{
          driver.findElement(By.cssSelector(locator)).sendKeys(value);
      }catch(Exception e){
          driver.findElement(By.xpath(locator)).sendKeys(value);
      }
    }

    // useful for @FindBy Approach
    public void typeOnElement1(WebElement locator, String value){
      try {
          locator.sendKeys(value);
      }catch (Exception exe){
          locator.sendKeys(value);
      }
    }

    // Some Helper method for type
    public static void typeOnElementAndEnter(String locator, String value){
      try {
          driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
      }catch(Exception ex){
          try {
              System.out.println(" First attempt was not successful");
              driver.findElement(By.name(locator)).sendKeys(value, Keys.ENTER);
          }catch(Exception ex1){
              try {
                  System.out.println(" Second attempt was not successful");
                  driver.findElement(By.xpath(locator)).sendKeys(value, Keys.ENTER);
              }catch (Exception ex2){
                  System.out.println(" third attempt was not successful");
                  driver.findElement(By.id(locator)).sendKeys(value, Keys.ENTER);
              }
          }
      }
    }

    public static void  typeOnElementNEnter(String locator,String value, WebDriver driver1){
      try {
          driver1.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
      }catch (Exception exe){
          try{
              driver1.findElement(By.id(locator)).sendKeys(value, Keys.ENTER);
          }catch (Exception exe1){
           try {
               driver1.findElement(By.name(locator)).sendKeys(value, Keys.ENTER);
           }catch(Exception exe2){
                   driver1.findElement(By.xpath(locator)).sendKeys(value,Keys.ENTER);
           }
          }
      }
    }

    public void clearField(String locator){
      driver.findElement(By.id(locator)).clear();
    }

    public void navigateBack(){
      driver.navigate().back();
  }

  public void typeOnInputField(String locator, String value){
      try{
          driver.findElement(By.cssSelector(locator)).sendKeys(value);
      }catch(Exception excep){
          driver.findElement(By.xpath(locator)).sendKeys(value);
      }
    }

    public void clickByXpath(String locator){
      driver.findElement(By.xpath(locator)).click();
    }

    public void typeByCssSelector(String locator,String value){
        driver.findElement(By.cssSelector(locator)).sendKeys(value);
    }

    public List<WebElement> getListOfWebElementsById(String locator){
      List<WebElement> list = new ArrayList<>();
      list = driver.findElements(By.id(locator));
      return list;
    }

    public List<String> getTextFromWebElements(String locator){
      List<WebElement> element = new ArrayList<>();
      List<String> text = new ArrayList<>();
      element =driver.findElements(By.cssSelector(locator));
        for (WebElement web : element){
            String st = web.getText();
            text.add(st);
        }
        return text;
    }

    public List<String> getTextFromWebElements(String locator,WebDriver driver1){
        List<WebElement> element = new ArrayList<>();
        List<String> text = new ArrayList<>();
        element =driver.findElements(By.cssSelector(locator));
        for (WebElement web : element){
            String st = web.getText();
            text.add(st);
        }
        return text;
    }

    public List<WebElement> getListOfWebElementsById(String locator,WebDriver driver1){
        List<WebElement> list = new ArrayList<>();
        list = driver.findElements(By.id(locator));
        return list;
    }

  }
