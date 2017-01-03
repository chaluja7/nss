package cz.cvut.nss.selenium;

import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import cz.cvut.nss.selenium.page.admin.AdminStationCreatePage;
import cz.cvut.nss.selenium.page.admin.AdminStationListPage;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Test vlozeni a nalezeni vlozene stanice
 * Kontroly prubehu spravneho flow obrazovek (asserty) se kontroluji i pri inicializaci jednotlivych Page objektu
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminInsertValidStationSeleniumIT extends AbstractSeleniumIT {

    private String stationTitle;

    @Test
    public void testCreateAndRetrieveStation() {
        AdminHomePage adminHomePage = loginAdmin();
        AdminStationListPage adminStationListPage = adminHomePage.goToStationListPage();
        AdminStationCreatePage adminStationCreatePage = adminStationListPage.goToStationCreatePage();

        stationTitle = "St. " + System.currentTimeMillis();
        adminStationListPage = adminStationCreatePage.createValidStation(stationTitle, stationTitle, "", "");
        adminStationListPage.searchStation(stationTitle);

        WebElement body = driver.findElement(By.tagName("body"));
        int numberOfTitlesOnPage = StringUtils.countMatches(body.getText(), stationTitle);
        Assert.assertTrue(numberOfTitlesOnPage > 0);
    }

}
