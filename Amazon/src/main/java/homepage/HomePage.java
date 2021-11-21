package homepage;

import common.WebAPI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

//import static homepage.HomepageWebElement.searchBoxLocator;
//import static homepage.HomepageWebElement.searchButtonLocator;
// If we modified the above imported class in following way then we do not need to import that method every single time

import static homepage.HomepageWebElement.*; //( After dot just put * )

public class HomePage extends WebAPI {

    // All action methods are here

    //@Find annotation: First approach

    @FindBy(how= How.ID, using=searchBoxLocator) public WebElement searchBox; // Here import Homepage WebElement class
    @FindBy(how=How.ID, using=searchButtonLocator) public WebElement searchButton;
    @FindBy(how=How.XPATH, using=searchTextLocator) public WebElement searchText;

    // Action method
    public void searchBox(){
        //Entering value in search Box
        searchBox.sendKeys(productName); //searchBox.sendKeys("mask");
        // Clicking the searchButton
        searchButton.click();
    }


//    // @Find Annotation : 2nd approach
//
//    @FindBy(id=searchBoxLocator) public WebElement searchBox1;
//    @FindBy(id=searchButtonLocator) public WebElement searchButton1;
//
//    public void demo1(){
//        searchBox1.sendKeys("mask");
//        searchButton1.click();
//    }
//
//// @Find Annotation :3rd Approach using private (modifier handle with getter and setter method)
//
//    @FindBy(id=searchBoxLocator) public WebElement searchBox2;
//    @FindBy(id=searchButtonLocator) private WebElement searchButton2;
//
//    public WebElement getSearchButton2() {
//        return searchButton2;
//    }
//
//    public void setSearchButton2(WebElement searchButton2) {
//        this.searchButton2 = searchButton2;
//    }
//
//    public void demo3(){
//
//        searchBox2.sendKeys("mask");
//        searchButton2.click();
//    }





}
