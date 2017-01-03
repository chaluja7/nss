package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminStationCreatePage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "j_idt32:title")
    private WebElement titleInput;

    @FindBy(id = "j_idt32:name")
    private WebElement nameInput;

    @FindBy(id = "j_idt32:lat")
    private WebElement latInput;

    @FindBy(id = "j_idt32:lon")
    private WebElement lonInput;

    @FindBy(className = "submitButton")
    private WebElement submitButton;

    @FindBy(className = "errorJsf")
    private List<WebElement> jsfErrors;

    public AdminStationCreatePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Přidání nové stanice");
    }

    public AdminStationCreatePage createInvalidStation(String title, String name, String lat, String lon) {
        fillAndSubmitStationForm(title, name, lat, lon);
        return new AdminStationCreatePage(driver);
    }

    public AdminStationListPage createValidStation(String title, String name, String lat, String lon) {
        fillAndSubmitStationForm(title, name, lat, lon);
        return new AdminStationListPage(driver);
    }

    private void fillAndSubmitStationForm(String title, String name, String lat, String lon) {
        titleInput.sendKeys(title);
        nameInput.sendKeys(name);
        latInput.sendKeys(lat);
        lonInput.sendKeys(lon);

        submitButton.click();
    }

    public WebElement getBody() {
        return body;
    }

    public List<WebElement> getJsfErrors() {
        return jsfErrors;
    }
}
