package cz.cvut.nss.selenium;

import cz.cvut.nss.selenium.page.HomePage;
import cz.cvut.nss.selenium.page.LoginPage;
import cz.cvut.nss.selenium.page.admin.AdminHomePage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author jakubchalupa
 * @since 02.01.17
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractSeleniumIT {

    protected static final String APP_BASE_URL = "http://localhost:8080/NSS/";

    protected static RemoteWebDriver driver;

    protected static final String ADMIN_USERNAME = "admin";

    protected static final String ADMIN_PASSWORD = "a";

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "/Users/jakubchalupa/Downloads/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterClass
    public static void afterClass() {
        driver.quit();
    }

    @Before
    public void setUp() {
        driver.get(APP_BASE_URL);
    }

    protected AdminHomePage loginAdmin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.goToLoginPage();
        return loginPage.loginValidUser(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    public static void waitForElement(RemoteWebDriver driver, final By by){
        Wait<WebDriver> wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElement(by).isDisplayed();
            }
        });
    }

}
