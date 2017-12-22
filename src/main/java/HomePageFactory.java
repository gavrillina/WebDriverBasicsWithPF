import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePageFactory extends AbstractPageFactory {

    public HomePageFactory(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id='username']")
    private WebElement login;

    @FindBy(xpath = "//*[@id='password']")
    private WebElement password;

    @FindBy(xpath = "//*[@id='login_btn']")
    private WebElement enterButton;

    @FindBy(xpath = "//*[@id='pm_latest']/header")
    private WebElement welcomeText;

    public String welcomeText() {
        return welcomeText.getText();
    }

    public void cliclLoginButton() {

        waitForElementToBeClickable(loginButton);
        loginButton.click();
    }

    public HomePageFactory login(String userName, String userPassword) {

        waitForElementToBeClickable(login);
        login.sendKeys(userName);
        password.sendKeys(userPassword);
        enterButton.click();
        return new HomePageFactory(getDriver());
    }
}