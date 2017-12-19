
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class TestProtonMail {
    public static void main(String[] args) throws InterruptedException {


        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://protonmail.com");
        driver.findElement(By.xpath(".//*[@id='bs-example-navbar-collapse-1']/ul/li[7]/a")).click();
        driver.findElement(By.xpath(".//*[@id='username']")).sendKeys("automationTest@protonmail.com");
        driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("test123456");
        driver.findElement(By.xpath(".//*[@id='login_btn']")).click();
        driver.findElement(By.xpath(".//*[@id='pm_sidebar']/button")).click();
        driver.findElement(By.xpath(".//*[@id='autocomplete']")).sendKeys("tani455@mail.ru");
        driver.findElement(By.xpath("//*[@id='uid1']/div[2]/div[5]/input")).sendKeys("test");

       //driver.findElement(By.cssSelector(".angular-squire-iframe>body>div")).getTagName();

       driver.switchTo().frame;





        Thread.sleep(5000);
  driver.close();




    }
}