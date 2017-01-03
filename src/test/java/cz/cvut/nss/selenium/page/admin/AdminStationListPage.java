package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminStationListPage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(linkText = "+")
    private WebElement createStationLink;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchInput;

    public AdminStationListPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Seznam stanic");
    }

    public AdminStationCreatePage goToStationCreatePage() {
        createStationLink.click();
        return new AdminStationCreatePage(driver);
    }

    public void searchStation(String title) {
        searchInput.sendKeys(title);
        waitForGivenTime(3000);
    }

    public WebElement getBody() {
        return body;
    }

}
