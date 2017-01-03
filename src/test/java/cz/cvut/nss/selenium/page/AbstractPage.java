package cz.cvut.nss.selenium.page;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author jakubchalupa
 * @since 03.01.17
 */
public abstract class AbstractPage {

    protected RemoteWebDriver driver;

    public AbstractPage(RemoteWebDriver driver) {
        this.driver = driver;
        waitForPageLoad(driver);
        PageFactory.initElements(driver, this);

        Assert.assertTrue(isInitialized());
    }

    protected abstract boolean isInitialized();

    protected void waitForGivenTime(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static void waitForPageLoad(WebDriver driver) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                JavascriptExecutor js = (JavascriptExecutor) d;
                String readyState = js.executeScript("return document.readyState").toString();
                return (Boolean) readyState.equals("complete");
            }
        });
    }


}
