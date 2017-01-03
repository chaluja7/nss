package cz.cvut.nss.selenium;

import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import org.junit.Test;

/**
 * Test přihlášení a odhlášení. Kontroly prubehu spravneho flow obrazovek (asserty) se kontroluji pri inicializaci
 * jednotlivych Page objektu
 *
 * @author jakubchalupa
 * @since 02.01.17
 */
public class LoginValidUserSeleniumIT extends AbstractSeleniumIT {

    @Test
    public void testLoginAndLogout() {
        AdminHomePage adminHomePage = loginAdmin();
        adminHomePage.logout();
    }

}
