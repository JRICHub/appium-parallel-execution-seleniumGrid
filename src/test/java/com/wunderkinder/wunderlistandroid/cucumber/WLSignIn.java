package com.wunderkinder.wunderlistandroid.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.appium.seleniumgrid.parallel.poc.AppiumGridSetup;
import com.wunderkinder.wunderlistandroid.cucumber.pages.*;

//@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/WunderlistAndroid.feature"}, strict = false, format = { "pretty","json:target/cucumber.json" }, tags = { "~@ignore" })
public class WLSignIn extends AbstractTestNGCucumberTests{
    //private AndroidDriver driver;
    protected AppiumGridSetup gridObject = null;
    protected WLLandingPage HomePage;
    protected WLSignInPage SignInPage;    
    
    public void WLSignSetObject(){
	System.out.println("WLSign");
	setGridObjectDriver(gridObject);
    }
    
    //@BeforeClass
    //This approach breaks parallle run thus used TestNG Listener approach
    //Also 
    public void launchAppiumServer() throws MalformedURLException {
	try {          	   	    
        	    gridObject = new AppiumGridSetup();
        	    gridObject.SetupSeleniumGridAndAppiumNodesTest();
        	    
        	    createMobileDriver(gridObject);
        	    HomePage  =  new WLLandingPage(gridObject.driverParent);
        	    SignInPage =  new WLSignInPage(gridObject.driverParent);
        	    	    
        	    System.out.println(gridObject.hashCode());            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void createMobileDriver(AppiumGridSetup gridObjectMethodParameter) {
	try{
        	  //?? gridObject is resetting to null, need to find out why
        	  gridObject = gridObjectMethodParameter;
        	    
                  String directoryPath = System.getProperty("user.dir");
                  
                  DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                  desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ANDROID");
                  desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
                  desiredCapabilities.setCapability("applicationName", "dummy_Android_1");
                  desiredCapabilities.setCapability(MobileCapabilityType.APP, directoryPath + "/com.wunderkinder.wunderlistandroid.apk");
                  desiredCapabilities.setCapability("appActivity", "com.wunderkinder.wunderlistandroid.activity.WLStartViewFragmentActivity");
                  gridObject.driverParent = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), desiredCapabilities);
                  
                  System.out.println(gridObject.driverParent.hashCode());
	}catch(Exception e) {
	    e.printStackTrace();
	}
    }
    
    //https://stackoverflow.com/questions/45559950/run-as-testng-is-not-shown-for-class-extending-abstracttestngcucumbertests
    //@Test(enabled=false)
    //public void dummyTestMethod() {}
    
    //@BeforeMethod
    public AppiumDriver getGridObjectDriver() {
	return gridObject.driverParent;
    }
    
    public void setGridObjectDriver(AppiumGridSetup appiumGridObject ) {
	this.gridObject=appiumGridObject;
    }
    
    @AfterClass
    public void killAppiumServer() throws IOException {
	//AppiumServerController.stopDriver();
	//AppiumServerController.stopAppiumServer();
	////if (driver != null) {
            ////driver.quit();
        ////}
    }
    
}
