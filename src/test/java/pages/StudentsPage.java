package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StudentsPage extends Page {

    @FindBy(xpath = "/html/body/main/div[2]/div/div[1]/ul/li[2]")
    private WebElement header;

    @FindBy(partialLinkText = "Learn More")
    private List<WebElement> learnMoreList;

    public StudentsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getDomainNamesFromLearnMoreList(){
        List<String> resultList = new ArrayList<>();
        URI uri;
        for (WebElement w : learnMoreList){
            try {
                uri = new URI(w.getAttribute("href"));
                resultList.add(uri.getHost());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
