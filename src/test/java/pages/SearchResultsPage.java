package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchResultsPage extends Page {

    @FindBy(xpath = "//div[@class=\"product-content\"]/h3/a")
    private List<WebElement> listResultsTitles;

    @FindBy(xpath = "//section[@class=\"product-item \"]")
    private List<WebElement> listResultItems;

    @FindBy(xpath = "//input[@type=\"search\" and @id=\"js-site-search-input\"]")
    private WebElement inputSearch;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getListTextResultsTitles(){
        List<String> results = new ArrayList<>();
        listResultsTitles.forEach(s -> results.add(s.getText()));
        return results;
    }

    public List<Integer> getListCountsResultWithButtonAddToCart(){
        List<Integer> results = new ArrayList<>();
        listResultItems.forEach(webElement ->
                results.add(webElement.findElements(By.xpath(".//button[text()=\"Add to cart\"]")).size()));
        return results;
    }

    public void clearInputSearch(){
        inputSearch.click();
        inputSearch.clear();
    }

    public void sendKeysSearchInput(String str){
        inputSearch.sendKeys(str);
    }

}
