package Entity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageFactory extends AbstractPageFactory {

    public LoginPageFactory(WebDriver driver) {
        super(driver);
    }
    @FindBy(xpath = "//*[@id='username']")
    private WebElement loginField;

    @FindBy(xpath = "//*[@id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id='login_btn']")
    private WebElement enterButton;

    @FindBy(xpath = "//div[@ng-if='showWelcome']/header")
    private WebElement welcomeMessage;

    public InboxPageFactory doLogIn(String userName, String userPass) throws CannotLoginException {

        waitForVisibilityOfAllElementsLocatedBy(loginField);
        loginField.sendKeys(userName);
        passwordField.sendKeys(userPass);
        enterButton.click();
        waitForVisibilityOfAllElementsLocatedBy(welcomeMessage);
        if (welcomeMessage.isDisplayed()) {
            return new InboxPageFactory(getDriver());
        } else
            throw new CannotLoginException("Login failed");

    }

}
