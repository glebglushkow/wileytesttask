package pages;

import org.openqa.selenium.WebDriver;

abstract class Page {

    WebDriver driver;

    Page(WebDriver driver){
        this.driver = driver;
    }

}
