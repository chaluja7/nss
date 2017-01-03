package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminOperationIntervalCreatePage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(id = "j_idt32:startDateInputDate")
    private WebElement startDateInput;

    @FindBy(id = "j_idt32:endDateInputDate")
    private WebElement endDateInput;

    @FindBy(className = "submitButton")
    private WebElement submitButton;

    public AdminOperationIntervalCreatePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Přidání nového intervalu jízd");
    }

    public AdminOperationIntervalListPage createValidOperationInterval(String startDate, String endDate) {
        startDateInput.sendKeys(startDate);
        endDateInput.sendKeys(endDate);
        submitButton.click();

        return new AdminOperationIntervalListPage(driver);
    }

    public WebElement getBody() {
        return body;
    }

}
