import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.PageFactory;
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
    private void logInToBox(String userName, String userPassword) {

        homePageFactory = new HomePageFactory(driver);
        inboxPageFactory = new InboxPageFactory(driver);
        homePageFactory.clickLoginButton().doLogIn(userName, userPassword);

        Assert.assertEquals("Добро пожаловать", inboxPageFactory.welcomeText());

    }

    @Test(dataProvider = "testDataForMail", dependsOnMethods = {"logInToBox"})
    private void createNewMail(Mail mail) {

        inboxPageFactory.createNewMessage(mail);
    }

    @Test(dataProvider = "testDataForMail", dependsOnMethods = {"createNewMail"})
    private void checkingDraftPresence(Mail mail) {

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