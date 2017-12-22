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

 public InboxPageFactory doLogIn(String userName, String userPass){

     waitForVisibilityOfAllElementsLocatedBy(loginField);
     loginField.sendKeys(userName);
     passwordField.sendKeys(userPass);
     enterButton.click();
     return new InboxPageFactory(getDriver());
 }

}
