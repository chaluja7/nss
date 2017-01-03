package cz.cvut.nss.selenium;

import cz.cvut.nss.CSVFileReader;
import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import cz.cvut.nss.selenium.page.admin.AdminStationCreatePage;
import cz.cvut.nss.selenium.page.admin.AdminStationListPage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Test, že nejde vložit stanice s neplatnymi udaji
 * Kontroly prubehu spravneho flow obrazovek (asserty) se kontroluji i pri inicializaci jednotlivych Page objektu
 *
 * data se ctou z gridu, kde mam vzdy parametry pro nevalidni stanici
 * File - adminInsertInvalidStationSeleniumITData.csv
 *
 * Data pro testovani vlozeni neplatne stanice byla vygenerovana pomoci PAIRWISE TESTING v aplikaci allPairs.
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
@RunWith(value = Parameterized.class)
public class AdminInsertInvalidStationSeleniumIT extends AbstractSeleniumIT {

    private String title;

    private String name;

    private String lat;

    private String lon;

    public AdminInsertInvalidStationSeleniumIT(String title, String name, String lat, String lon) {
        this.title = StringUtils.isEmpty(title) ? "" : title;
        this.name = StringUtils.isEmpty(name) ? "" : name;
        this.lat = StringUtils.isEmpty(lat) ? "" : lat;
        this.lon = StringUtils.isEmpty(lon) ? "" : lon;
    }

    @Parameters(name = "Radek dat cislo {index}, title {0}, name {1}, lat {2}, lon {3}")
    public static Collection<String[]> testingData() {
        return CSVFileReader.readCSVFileToCollection("src/test/resources/adminInsertInvalidStationSeleniumITData.csv");
    }

    @Test
    public void testInsertInvalidStation() {
        AdminHomePage adminHomePage = loginAdmin();
        AdminStationListPage adminStationListPage = adminHomePage.goToStationListPage();
        AdminStationCreatePage adminStationCreatePage = adminStationListPage.goToStationCreatePage();

        Assert.assertTrue(adminStationCreatePage.getJsfErrors().isEmpty());
        adminStationCreatePage = adminStationCreatePage.createInvalidStation(title, name, lat, lon);
        Assert.assertFalse("Mela vyskocit chyba", adminStationCreatePage.getJsfErrors().isEmpty());
    }

}
