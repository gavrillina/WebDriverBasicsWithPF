import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class TestProtonMail {
    WebDriver driver;

    public static void main(String[] args) {
        org.testng.TestNG.main(args);
    }


    @BeforeClass
    private void openBrowser() {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();
        driver.get("https://protonmail.com");
    }

    @Test
    private void logIn() {
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.xpath(".//*[@id='username']")).sendKeys("automationTest@protonmail.com");
        driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("test123456");
        driver.findElement(By.xpath(".//*[@id='login_btn']")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String WelcomeText = driver.findElement(By.xpath(".//*[@id='pm_latest']/header")).getText();
        Assert.assertEquals("Добро пожаловать", WelcomeText);
    }

    @Test(dataProvider = "dataForTestEmail", dependsOnMethods = {"logIn"})
    private void createNewMessage(String email, String topic, String contain) throws InterruptedException {

        driver.findElement(By.xpath(".//*[@id='pm_sidebar']/button")).click();
        driver.findElement(By.xpath(".//*[@id='autocomplete']")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id='uid1']/div[2]/div[5]/input")).sendKeys(topic);

        WebElement frame = driver.findElement(By.xpath("//iframe[@class = 'squireIframe']"));
        driver.switchTo().frame(frame);

        driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).click();

        Actions make = new Actions(driver);
        Action kbEvents = make.sendKeys(contain).build();
        kbEvents.perform();
        Thread.sleep(2000);

        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//*[@data-original-title = 'Закрыть']")).click();

        //   String textMessage = driver.findElement(By.xpath(".//*[@id='uid6']/header/span")).getText();

        //  Assert.assertEquals("New message", textMessage);
    }

    @Test(dataProvider = "dataForTestEmail", dependsOnMethods = {"createNewMessage"})

    private void sendMessageTetsFromDraft(String email, String topic, String contain) {


        driver.findElement(By.xpath(".//span[text() = 'Черновики']")).click();

        List<WebElement> list = (List<WebElement>) driver.findElements(By.xpath(".//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));

        for (WebElement webElement : list)

        {
            driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);

            if (webElement.findElement(By.xpath("//*[@class = 'senders-name']")).getText().equals(email) && webElement.findElement(By.xpath("//*[@class = 'subject-text ellipsis']")).getText().equals(topic)) {
                webElement.click();
                WebElement fr = driver.findElement(By.xpath("//iframe[@class = 'squireIframe']"));
                driver.switchTo().frame(fr);

                if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals(contain)) {
                    driver.switchTo().defaultContent();

                    driver.findElement(By.xpath(".//*[text()='Отправить']")).click();
                }
            }
        }
    }

    @Test(dataProvider = "dataForTestEmail", dependsOnMethods = {"sendMessageTetsFromDraft"})

    private void verifySendMessageTets(String email, String topic, String contain) {


        driver.findElement(By.xpath(".//span[text() = 'Отправленные']")).click();

        List<WebElement> list = (List<WebElement>) driver.findElements(By.xpath(".//*[@ng-repeat = 'conversation in conversations track by conversation.ID']"));

        for (WebElement webElement : list)

        {
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            if (webElement.findElement(By.xpath("//*[@class = 'senders-name']")).getText().equals(email) && webElement.findElement(By.xpath("//*[@class = 'subject-text ellipsis']")).getText().equals(topic)) {
                webElement.click();
                WebElement fr = driver.findElement(By.xpath(".//iframe[@class = 'squireIframe']"));
                driver.switchTo().frame(fr);

                if (driver.findElement(By.xpath("//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")).getText().equals(contain)) {
                    driver.switchTo().defaultContent();

                    driver.findElement(By.xpath(".//*[@id='body']/header/div/a")).click();
                }
            }
        }
    }

    @Test(dependsOnMethods = {"verifySendMessageTets"})

    private void logOff() {
        driver.findElement(By.xpath(".//*[@class='fa fa-angle-down']")).click();
        driver.findElement(By.xpath(".//*[@class='pm_button primary text-center navigationUser-logout']")).click();
    }

    @DataProvider
    public Object[][] dataForTestEmail() {
        return new Object[][]{{"tani455@mail.ru", "Tatyana", "Some text"}};
    }


    @AfterClass
    private void closeBroser() throws InterruptedException {
        Thread.sleep(5000);
        driver.close();
    }

}