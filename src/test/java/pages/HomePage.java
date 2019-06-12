package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HomePage extends Page {

    @FindBy(linkText = "WHO WE SERVE")
    private WebElement whoWeServe;

    @FindBy(linkText = "SUBJECTS")
    private WebElement subject;

    @FindBy(linkText = "THE WILEY NETWORK")
    private WebElement theWileyNetwork;

    @FindBy(linkText = "ABOUT")
    private WebElement about;

    @FindBy(xpath = "//*[@id=\"country-location-form\"]/div[3]/button[2]")
    private WebElement yesPopUpButton;

    @FindBy(xpath = " //*[@id=\"Level1NavNode1\"]/ul")
    private WebElement listItemsUnderWhoWeServe;

    @FindBy(xpath = "//*[@id=\"Level1NavNode1\"]/ul/li[1]/a")
    private WebElement students;

    @FindBy(xpath = "//*[@id=\"Level1NavNode2\"]/ul/li[9]/a")
    private WebElement education;

    @FindBy(xpath = "//img[@title=\"Wiley\"]")
    private WebElement logo;

    @FindBy(xpath = "//*[@id=\"js-site-search-input\"]")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement searchButton;

    @FindBy(xpath = "//aside[@class=\"ui-autocomplete ui-front main-navigation-search-autocomplete ui-menu ui-widget ui-widget-content ps-container ps-theme-default\"]")
    private WebElement searchArea;

    @FindBy(xpath = "//section[contains(@class, \"suggestions\")]//a")
    private List<WebElement> listSearchSectionSuggestions;

    @FindBy(xpath = "//section[contains(@class, \"products\")]/div//a")
    private List<WebElement> listSearchSectionProducts;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public List<String> getTextListSearchSectionSuggestions(){
        List<String> result = new ArrayList<>();
        listSearchSectionSuggestions.forEach(webElement -> result.add(webElement.getText()));
        return result;
    }

    public List<String> getTextListSearchSectionProducts(){
        List<String> result = new ArrayList<>();
        listSearchSectionProducts.forEach(webElement -> result.add(webElement.getText()));
        return result;
    }

    public void clickYesPopUpButton() {
        yesPopUpButton.click();
    }

    public void clickWhoWeServe() {
        whoWeServe.click();
    }

    public void clickStudents() {
        students.click();
    }

    public void clickLogo() {
        logo.click();
    }

    public void clearSearchInput(){
        searchInput.click();
        searchInput.clear();
    }

    public void clickSearchButton(){
        searchButton.click();
    }

    //TODO решить с actions
    public void moveToSubjects() {
        Actions actions = new Actions(driver);
        actions.moveToElement(subject).build().perform();
    }

    public void clickSubjects() {
        subject.click();
    }

    public void clickEducation() {
        education.click();
    }

    public List<WebElement> getListItemsUnderWhoWeServe() {
        return listItemsUnderWhoWeServe.findElements(By.tagName("li"));
    }

    public List<String> getListTextItemsUnderWhoWeServe() {
        List<String> listText = new ArrayList<>();
        listItemsUnderWhoWeServe.findElements(By.tagName("li"))
                .forEach(webElement -> listText.add(webElement.getText()));
        return listText;
    }

    public void sendKeysSearchInput(String str){
        searchInput.sendKeys(str);
    }

}
