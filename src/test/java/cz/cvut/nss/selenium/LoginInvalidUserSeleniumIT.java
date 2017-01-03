package cz.cvut.nss.selenium;

import cz.cvut.nss.CSVFileReader;
import cz.cvut.nss.selenium.page.HomePage;
import cz.cvut.nss.selenium.page.LoginPage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Test, že nejde přihlásit uživatel s neplatným jménem/heslem.
 * Kontroly prubehu spravneho flow obrazovek (asserty) se kontroluji i pri inicializaci jednotlivych Page objektu
 *
 * data se ctou z gridu, kde mam vzdy parametry pro nevalidni prihlaseni
 * File - loginInvalidUserSeleniumITData.csv
 *
 * Data pro testovani neplatneho prihlaseni byla vygenerovana pomoci PAIRWISE TESTING v aplikaci allPairs.
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
@RunWith(value = Parameterized.class)
public class LoginInvalidUserSeleniumIT extends AbstractSeleniumIT {

    private String username;

    private String password;

    public LoginInvalidUserSeleniumIT(String username, String password) {
        this.username = StringUtils.isEmpty(username) ? "" : username;
        this.password = StringUtils.isEmpty(password) ? "" : password;
    }

    @Parameters(name = "Radek dat cislo {index}, username {0}, password {1}")
    public static Collection<String[]> testingData() {
        return CSVFileReader.readCSVFileToCollection("src/test/resources/loginInvalidUserSeleniumITData.csv");
    }

    @Test
    public void testInvalidLogin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToLoginPage();

        loginPage = loginPage.loginInvalidUser(username, password);
        Assert.assertTrue(loginPage.getBody().getText().contains("Zadali jste špatné přihlašovací údaje. Zkuste to prosím znovu."));

        loginPage = loginPage.loginInvalidUser(username, password);
        Assert.assertTrue(loginPage.getBody().getText().contains("Zadali jste špatné přihlašovací údaje. Zkuste to prosím znovu."));
    }

}
