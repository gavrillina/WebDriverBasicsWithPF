import Entity.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestProtonMail {
    WebDriver driver;
    HomePageFactory homePageFactory;
    InboxPageFactory inboxPageFactory;

    @BeforeClass
    private void openBrauser() {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://protonmail.com/");
        driver.manage().window().maximize();
    }


    @Test(dataProvider = "testDataForLogIn")
    private void logInToBox(String userName, String userPassword) throws CannotLoginException {

        homePageFactory = new HomePageFactory(driver);
        inboxPageFactory = new InboxPageFactory(driver);
        homePageFactory.clickLoginButton().doLogIn(userName, userPassword);

    }

    @Test(dataProvider = "testDataForMail", dependsOnMethods = {"logInToBox"})
    private void createNewMail(Mail mail) {

        inboxPageFactory.createNewMessage(mail);
    }

    @Test(dataProvider = "testDataForMail", dependsOnMethods = {"createNewMail"})
    private void checkingDraftPresence(Mail mail) throws DraftNotFoundException {

        inboxPageFactory.veryfySendMessage(mail);
    }

    @DataProvider
    public Object[][] testDataForMail() {
        Mail mail = new Mail("tani455@mail.ru", "Tatyana", "Some text");
        return new Object[][]{{mail}};
    }

    @DataProvider
    public Object[][] testDataForLogIn() {
        return new Object[][]{{"automationTest@protonmail.com", "test123456"}};
    }

    @AfterClass
    private void closeBrowser() {
        driver.quit();
    }

}