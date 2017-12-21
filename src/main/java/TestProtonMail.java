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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestProtonMail {
    WebDriver driver;
    HomePageFactory homePageFactory;
    InboxPageFactory inboxPageFactory;

    public static void main(String[] args) {
        org.testng.TestNG.main(args);
    }


    @BeforeClass
    private void openBrauser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://protonmail.com/");
        driver.manage().window().maximize();


    }


    @Test(dataProvider = "testDataForLogIn")

    private void logIn(String login, String password) {

        homePageFactory = new HomePageFactory(driver);
        homePageFactory.cliclLoginButton();
        homePageFactory.login(login,password);
        Assert.assertEquals("Добро пожаловать", homePageFactory.welcomeText());

    }

    @Test(dataProvider = "testDataForMail", dependsOnMethods = {"logIn"})
    private void createNewMail(Mail mail) {

        InboxPageFactory inboxPageFactory = new InboxPageFactory(driver);

        inboxPageFactory.waitForNewMessageButtonToLoad();

        inboxPageFactory.clickNewMessage();
        inboxPageFactory.waitForSendermMilFieldToLoad();

        inboxPageFactory.waitForMailTopicToLoad();
        inboxPageFactory.createNewMessage(mail);


        inboxPageFactory.waitForFrameToLoad();

        inboxPageFactory.waitForButtonCloseToLoad();

        inboxPageFactory.closeMessage();


    }

    @Test(dataProvider = "testDataForMail", dependsOnMethods = {"createNewMail"})
    private void checkingDraftPresence(String email, String subject, String textContent) throws InterruptedException {
        driver.findElement(By.xpath(".//span[text() = 'Черновики']")).click();
        List<WebElement> list = (List<WebElement>) driver.findElements(By.xpath(".//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));

        for (WebElement webElement : list) {
            driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

            if (webElement.findElement(By.xpath("//*[@class = 'senders-name']")).getText().equals(email) && webElement.findElement(By.xpath("//*[@class = 'subject-text ellipsis']")).getText().equals(subject)) {
                webElement.click();
                WebElement frame = driver.findElement(By.xpath("//iframe[@class = 'squireIframe']"));
                driver.switchTo().frame(frame);

                if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals(textContent)) {
                    driver.switchTo().defaultContent();
                    driver.findElement(By.xpath(".//*[text()='Отправить']")).click();
                    driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

                }

            }
        }

    }

    @Test(dataProvider = "testDataForMail", dependsOnMethods = {"checkingDraftPresence"})
    private void verifySendTest(String email, String subject, String textContent) throws InterruptedException {
        driver.findElement(By.xpath(".//span[text() = 'Отправленные']")).click();
        List<WebElement> list = (List<WebElement>) driver.findElements(By.xpath(".//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));

        for (WebElement webElement : list) {


            if (webElement.findElement(By.xpath("//*[@class = 'senders-name']")).getText().equals(email) && webElement.findElement(By.xpath("//*[@class = 'subject-text ellipsis']")).getText().equals(subject)) {
                webElement.click();
                WebElement frame = driver.findElement(By.xpath("//iframe[@class = 'squireIframe']"));
                driver.switchTo().frame(frame);

                if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals(textContent)) {
                    driver.switchTo().defaultContent();
                    driver.findElement(By.xpath(".//*[@class='headerSecuredDesktop-logo headerDesktop-logo protonmailLogo-container logo']")).click();
                    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                }

            }
        }
    }


    @Test(dependsOnMethods = {"verifySendTest"})
    private void logOff() {
        driver.findElement(By.xpath(".//*[@class='fa fa-angle-down']")).click();
        driver.findElement(By.xpath(".//*[@class='pm_button primary text-center navigationUser-logout']")).click();

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