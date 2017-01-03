package cz.cvut.nss.selenium;

import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import cz.cvut.nss.selenium.page.admin.AdminRouteCreatePage;
import cz.cvut.nss.selenium.page.admin.AdminRouteListPage;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Test vlozeni a nalezeni vlozene trasy
 * Kontroly prubehu spravneho flow obrazovek (asserty) se kontroluji i pri inicializaci jednotlivych Page objektu
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminInsertValidRouteSeleniumIT extends AbstractSeleniumIT {

    private String routeName;

    @Test
    public void testCreateAndRetrieveRoute() {
        AdminHomePage adminHomePage = loginAdmin();
        AdminRouteListPage adminRouteListPage = adminHomePage.goToRouteListPage();
        AdminRouteCreatePage adminRouteCreatePage = adminRouteListPage.goToRouteCreatePage();

        routeName = "Ro. " + System.currentTimeMillis();
        adminRouteListPage = adminRouteCreatePage.createValidRoute(routeName);
        adminRouteListPage.searchRoute(routeName);

        WebElement body = driver.findElement(By.tagName("body"));
        int numberOfNamesOnPage = StringUtils.countMatches(body.getText(), routeName);
        Assert.assertTrue(numberOfNamesOnPage > 0);
    }

}
