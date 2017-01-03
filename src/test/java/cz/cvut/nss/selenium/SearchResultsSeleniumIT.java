package cz.cvut.nss.selenium;

import cz.cvut.nss.selenium.page.HomePage;
import cz.cvut.nss.selenium.page.SearchResultsPage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testovani vyhledavani spojeni.
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
public class SearchResultsSeleniumIT extends AbstractSeleniumIT {

    @Test
    public void testInvalidSearch() {
        HomePage homePage = new HomePage(driver);
        SearchResultsPage resultsPage = homePage.searchValidResults("Dejvická", "Karlovo náměstí", "14.07.2015", "07:00");

        Assert.assertTrue(resultsPage.getBody().getText().contains("Dejvická --> Karlovo náměstí"));
    }

}
