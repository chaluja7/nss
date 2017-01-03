package cz.cvut.nss.selenium.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class SearchResultsPage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(className = "searchResultTableWrapper")
    private WebElement resultsWrapper;

    public SearchResultsPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return resultsWrapper != null;
    }

    public WebElement getBody() {
        return body;
    }

}
