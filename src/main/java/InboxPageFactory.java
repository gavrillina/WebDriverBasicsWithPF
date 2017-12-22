import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class InboxPageFactory extends AbstractPageFactory {
    WebDriver driver;

    public InboxPageFactory(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='pm_latest']/header")
    private WebElement welcomeText;

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

    @FindBy(xpath = "//span[text() = 'Черновики']")
    WebElement draft;

    @FindBy(xpath = "//*[@ng-repeat = 'conversation in conversations track by conversation.ID']")
    List<WebElement> draftList;

    @FindBy(xpath = "//*[text()='Отправить']")
    WebElement sendButton;

    public String welcomeText(){
        waitForElementToBeClickable(newMessageButton);
        return welcomeText.getText();
    }


      public void createNewMessage(Mail mail) {

        waitForVisibilityOfAllElementsLocatedBy(newMessageButton);
        welcomeText.getText();

        newMessageButton.click();
        waitForElementToBeClickable(senderMail);
        senderMail.sendKeys(mail.getSenderMail());
        mailTopic.sendKeys(mail.getTopic());

        getDriver().switchTo().frame(frame);

        textContain.click();

        Actions make = new Actions(getDriver());
        Action kbEvents = make.sendKeys(mail.getTextContain()).build();
        kbEvents.perform();

        getDriver().switchTo().defaultContent();

        closeButton.click();

           }

    public  void veryfySendMessage(Mail mail){

        waitForElementToBeClickable(draft);
        draft.click();
        waitForListElements(draftList);

        for (WebElement webElement : draftList) {

           waitForElementToBeClickable(webElement);

            if (senderMail.getText().equals(mail.getSenderMail()) && mailTopic.getText().equals(mail.getTopic())) {

                webElement.click();

                getDriver().switchTo().frame(frame);

                if (textContain.getText().equals(mail.getTextContain())) {
                    getDriver().switchTo().defaultContent();

                    waitForElementToBeClickable(sendButton);
                    sendButton.click();

                }
break;
            }
        }



    }


}



