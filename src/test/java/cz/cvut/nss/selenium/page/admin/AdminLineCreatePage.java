package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminLineCreatePage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "j_idt32:name")
    private WebElement nameInput;

    @FindBy(className = "submitButton")
    private WebElement submitButton;

    public AdminLineCreatePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Přidání nového spoje");
    }

    public AdminLineListPage createValidLine(String name) {
        nameInput.sendKeys(name);
        submitButton.click();

        return new AdminLineListPage(driver);
    }

    public WebElement getBody() {
        return body;
    }

}
