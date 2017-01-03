package cz.cvut.nss.selenium.page;

import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class LoginPage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "userName")
    private WebElement userNameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(name = "submit")
    private WebElement submitButton;

    public LoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Přihlášení do administrace");
    }

    public AdminHomePage loginValidUser(String username, String password) {
        fillAndSubmitLoginForm(username, password);
        return new AdminHomePage(driver);
    }

    public LoginPage loginInvalidUser(String username, String password) {
        fillAndSubmitLoginForm(username, password);
        return new LoginPage(driver);
    }

    private void fillAndSubmitLoginForm(String username, String password) {
        userNameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
    }

    public WebElement getBody() {
        return body;
    }

}
