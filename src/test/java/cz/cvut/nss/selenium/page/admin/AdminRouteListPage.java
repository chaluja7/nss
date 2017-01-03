package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminRouteListPage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(linkText = "+")
    private WebElement createRouteLink;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchInput;

    public AdminRouteListPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Seznam tras");
    }

    public AdminRouteCreatePage goToRouteCreatePage() {
        createRouteLink.click();
        return new AdminRouteCreatePage(driver);
    }

    public void searchRoute(String name) {
        searchInput.sendKeys(name);
        waitForGivenTime(3000);
    }

    public WebElement getBody() {
        return body;
    }

}
