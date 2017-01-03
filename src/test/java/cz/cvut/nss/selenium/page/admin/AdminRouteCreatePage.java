package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminRouteCreatePage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "j_idt32:name")
    private WebElement nameInput;

    @FindBy(name = "j_idt32:j_idt38")
    private WebElement submitButton;

    public AdminRouteCreatePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Přidání nové trasy");
    }

    public AdminRouteListPage createValidRoute(String name) {
        nameInput.sendKeys(name);
        submitButton.click();

        return new AdminRouteListPage(driver);
    }

    public WebElement getBody() {
        return body;
    }

}
