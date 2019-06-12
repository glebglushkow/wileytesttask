package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EducationPage extends Page {

    @FindBy(xpath = "/html/body/main/div[3]/div/div/div[1]/ul/li[3]")
    private WebElement header;


    @FindBy(xpath = "//*[@class=\"side-panel\"]/ul/li")
    private List<WebElement> subjectLinkList;

    public EducationPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getTextSubjectLinkList() {
        List<String> result = new ArrayList<>();
        for (WebElement webElement : subjectLinkList) {
            result.add(webElement.getText());
        }
        return result;
    }

}
