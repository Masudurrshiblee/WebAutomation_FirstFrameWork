package homepagetest;

import common.WebAPI;
import homepage.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class HomePageTest extends WebAPI {    // Test class method

    // Now creating the reference variable of Homepage class which need to call
    HomePage homepage;

    //So this is the way we can call multiple class
    // Registration registration;
@BeforeMethod
    public void getInit(){ // This get init method providing the driver to the class which is need to be executed

        // now initialize that homepage reference variable using pageFactory
        // which is the design of page object design pattern
        homepage = PageFactory.initElements(driver,HomePage.class);
       // registration=PageFactory.initElements(driver, Registration.class);
    }
//@Ignore
@Test(enabled = true)
public void testSearchBox(){
        //Calling action method by reference variable
        homepage.searchBox();

}

}
