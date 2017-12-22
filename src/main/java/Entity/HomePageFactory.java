package Entity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePageFactory extends AbstractPageFactory {

    public HomePageFactory(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")
    private WebElement loginButton;

    public LoginPageFactory clickLoginButton(){

        waitForElementToBeClickable(loginButton);
        loginButton.click();
        return  new LoginPageFactory(getDriver());
    }
}