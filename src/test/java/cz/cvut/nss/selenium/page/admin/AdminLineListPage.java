package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminLineListPage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(linkText = "+")
    private WebElement createLineLink;

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchInput;

    public AdminLineListPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Seznam stanic");
    }

    public AdminLineCreatePage goToLineCreatePage() {
        createLineLink.click();
        return new AdminLineCreatePage(driver);
    }

    public void searchLine(String name) {
        searchInput.sendKeys(name);
        waitForGivenTime(3000);
    }

    public WebElement getBody() {
        return body;
    }

}
