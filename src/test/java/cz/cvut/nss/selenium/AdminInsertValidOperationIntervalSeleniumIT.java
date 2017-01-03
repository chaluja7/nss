package cz.cvut.nss.selenium;

import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import cz.cvut.nss.selenium.page.admin.AdminOperationIntervalCreatePage;
import cz.cvut.nss.selenium.page.admin.AdminOperationIntervalListPage;
import cz.cvut.nss.utils.DateTimeUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Test vlozeni a nalezeni vlozeneho spoje
 * Kontroly prubehu spravneho flow obrazovek (asserty) se kontroluji i pri inicializaci jednotlivych Page objektu
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
public class AdminInsertValidOperationIntervalSeleniumIT extends AbstractSeleniumIT {

    private String operationIntervalDate;

    @Test
    public void testCreateAndRetrieveOperationInterval() {
        AdminHomePage adminHomePage = loginAdmin();
        AdminOperationIntervalListPage adminOperationIntervalListPage = adminHomePage.goToOperationIntervalListPage();
        AdminOperationIntervalCreatePage adminOperationIntervalCreatePage = adminOperationIntervalListPage.goToOperationIntervalCreatePage();

        operationIntervalDate = new SimpleDateFormat(DateTimeUtils.DATE_PATTERN).format(new Date());
        adminOperationIntervalListPage = adminOperationIntervalCreatePage.createValidOperationInterval(operationIntervalDate, operationIntervalDate);
        adminOperationIntervalListPage.searchOperationInterval(operationIntervalDate);

        WebElement body = driver.findElement(By.tagName("body"));
        int numberOfDatesOnPage = StringUtils.countMatches(body.getText(), operationIntervalDate);
        Assert.assertTrue(numberOfDatesOnPage > 1);
    }

}
