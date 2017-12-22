import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


public class InboxPageFactory extends AbstractPageFactory {
    WebDriver driver;

    public InboxPageFactory(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@class='compose pm_button sidebar-btn-compose']")
    WebElement newMessageButton;

    @FindBy(css = "#autocomplete")
    WebElement senderMail;

    @FindBy(xpath = "//*[@id='uid1']/div[2]/div[5]/input")
    WebElement mailTopic;

    @FindBy(xpath = "//iframe[@class = 'squireIframe']")
    WebElement frame;


    @FindBy(xpath = "//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")
    WebElement textContain;

    @FindBy(xpath = "//*[@data-original-title = 'Закрыть']")
    WebElement closeButton;


    public void clickNewMessage() {

        newMessageButton.click();
    }

    public void createNewMessage(Mail mail) {

        senderMail.sendKeys(mail.getSenderMail());
        mailTopic.sendKeys(mail.getTopic());

        getDriver().switchTo().frame(frame);

        textContain.click();


        Actions make = new Actions(getDriver());
        Action kbEvents = make.sendKeys(mail.getTextContain()).build();
        kbEvents.perform();

        getDriver().switchTo().defaultContent();
    }

    public void closeMessage() {
        closeButton.click();
    }

    private boolean isNewMessageButtonDisplayed() {
        try {
            return newMessageButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForNewMessageButtonToLoad() {
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isNewMessageButtonDisplayed();
        while (!isPageOpenedIndicator && secondsCount < 5) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsCount++;
            isPageOpenedIndicator = isNewMessageButtonDisplayed();
        }
        if (!isPageOpenedIndicator) {
            throw new AssertionError("Page was not opened");
        }
    }

    private boolean isSenderMailFieldDisplayed() {
        try {
            return senderMail.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForSendermMilFieldToLoad() {
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isSenderMailFieldDisplayed();
        while (!isPageOpenedIndicator && secondsCount < 5) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsCount++;
            isPageOpenedIndicator = isSenderMailFieldDisplayed();
        }
        if (!isPageOpenedIndicator) {
            throw new AssertionError("Page was not opened");
        }
    }

    private boolean isMailTopicDisplayed() {
        try {
            return mailTopic.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForMailTopicToLoad() {
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isMailTopicDisplayed();
        while (!isPageOpenedIndicator && secondsCount < 5) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsCount++;
            isPageOpenedIndicator = isMailTopicDisplayed();
        }
        if (!isPageOpenedIndicator) {
            throw new AssertionError("Page was not opened");
        }
    }

    private boolean isCloseButtonisplayed() {
        try {
            return mailTopic.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForButtonCloseToLoad() {
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isCloseButtonisplayed();
        while (!isPageOpenedIndicator && secondsCount < 5) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsCount++;
            isPageOpenedIndicator = isCloseButtonisplayed();
        }
        if (!isPageOpenedIndicator) {
            throw new AssertionError("Page was not opened");
        }
    }


    private boolean isFrameDisplayed() {
        try {
            return frame.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void waitForFrameToLoad() {
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isFrameDisplayed();
        while (!isPageOpenedIndicator && secondsCount < 5) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsCount++;
            isPageOpenedIndicator = isFrameDisplayed();
        }
        if (!isPageOpenedIndicator) {
            throw new AssertionError("Page was not opened");
        }
    }
}



