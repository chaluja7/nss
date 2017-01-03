package cz.cvut.nss.selenium;

import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import cz.cvut.nss.selenium.page.admin.AdminLineCreatePage;
import cz.cvut.nss.selenium.page.admin.AdminLineListPage;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Test vlozeni a nalezeni vlozeneho spoje
 * Kontroly prubehu spravneho flow obrazovek (asserty) se kontroluji i pri inicializaci jednotlivych Page objektu
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminInsertValidLineSeleniumIT extends AbstractSeleniumIT {

    private String lineName;

    @Test
    public void testCreateAndRetrieveLine() {
        AdminHomePage adminHomePage = loginAdmin();
        AdminLineListPage adminLineListPage = adminHomePage.goToLineListPage();
        AdminLineCreatePage adminLineCreatePage = adminLineListPage.goToLineCreatePage();

        lineName = "Li. " + System.currentTimeMillis();
        adminLineListPage = adminLineCreatePage.createValidLine(lineName);
        adminLineListPage.searchLine(lineName);

        WebElement body = driver.findElement(By.tagName("body"));
        int numberOfNamesOnPage = StringUtils.countMatches(body.getText(), lineName);
        Assert.assertTrue(numberOfNamesOnPage > 0);
    }

}
