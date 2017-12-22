package Entity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AbstractPageFactory {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public AbstractPageFactory(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForElementToBeClickable(WebElement locator) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForVisibilityOfAllElementsLocatedBy(WebElement locator) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOf(locator));
    }

    public void waitForListElements(List<WebElement> locator) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(locator));
    }

}
