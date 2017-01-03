package cz.cvut.nss.selenium.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class HomePage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(linkText = "Administrace")
    private WebElement loginPageLink;

    @FindBy(id = "from")
    private WebElement fromInput;

    @FindBy(id = "to")
    private WebElement toInput;

    @FindBy(id = "date")
    private WebElement dateInput;

    @FindBy(id = "time")
    private WebElement timeInput;

    @FindBy(id = "neo4j")
    private WebElement neo4jCheckbox;

    @FindBy(className = "submitButton")
    private WebElement submitButton;

    public HomePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Najdi svůj spoj");
    }

    public LoginPage goToLoginPage() {
        loginPageLink.click();
        return new LoginPage(driver);
    }

    public SearchResultsPage searchValidResults(String from, String to, String date, String time) {
        fromInput.sendKeys(from);
        toInput.sendKeys(to);
        dateInput.sendKeys(date);
        timeInput.sendKeys(time);

        submitButton.click();
        return new SearchResultsPage(driver);
    }

    public WebElement getBody() {
        return body;
    }

    public WebElement getLoginPageLink() {
        return loginPageLink;
    }

}
