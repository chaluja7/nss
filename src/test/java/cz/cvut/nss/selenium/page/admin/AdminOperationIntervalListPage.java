package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminOperationIntervalListPage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(linkText = "+")
    private WebElement createOperationIntervalLink;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchInput;

    public AdminOperationIntervalListPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Seznam platností jízd");
    }

    public AdminOperationIntervalCreatePage goToOperationIntervalCreatePage() {
        createOperationIntervalLink.click();
        return new AdminOperationIntervalCreatePage(driver);
    }

    public void searchOperationInterval(String value) {
        searchInput.sendKeys(value);
        waitForGivenTime(3000);
    }

    public WebElement getBody() {
        return body;
    }

}
