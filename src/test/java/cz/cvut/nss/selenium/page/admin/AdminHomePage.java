package cz.cvut.nss.selenium.page.admin;

import cz.cvut.nss.selenium.page.AbstractPage;
import cz.cvut.nss.selenium.page.LoginPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminHomePage extends AbstractPage {

    @FindBy(tagName = "body")
    private WebElement body;

    @FindBy(linkText = "Odhlásit")
    private WebElement logoutLink;

    @FindBy(linkText = "Seznam stanic")
    private WebElement stationsLink;

    @FindBy(linkText = "Seznam tras")
    private WebElement routesLink;

    @FindBy(linkText = "Seznam spojů")
    private WebElement linesLink;

    @FindBy(linkText = "Seznam platnosti jízd")
    private WebElement operationIntervalsLink;

    public AdminHomePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isInitialized() {
        return body.getText().contains("Vítejte v administraci!");
    }

    public LoginPage logout() {
        logoutLink.click();
        return new LoginPage(driver);
    }

    public AdminStationListPage goToStationListPage() {
        stationsLink.click();
        return new AdminStationListPage(driver);
    }

    public AdminRouteListPage goToRouteListPage() {
        routesLink.click();
        return new AdminRouteListPage(driver);
    }

    public AdminLineListPage goToLineListPage() {
        linesLink.click();
        return new AdminLineListPage(driver);
    }

    public AdminOperationIntervalListPage goToOperationIntervalListPage() {
        operationIntervalsLink.click();
        return new AdminOperationIntervalListPage(driver);
    }

    public WebElement getBody() {
        return body;
    }

}
