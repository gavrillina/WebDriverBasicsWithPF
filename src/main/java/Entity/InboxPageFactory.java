package Entity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class InboxPageFactory extends AbstractPageFactory {
    WebDriver driver;

    public InboxPageFactory(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@class='compose pm_button sidebar-btn-compose']")
    WebElement newMessageButton;

    @FindBy(css = "#autocomplete")
    WebElement senderMail;

    @FindBy(xpath = "//*[@class = 'senders-name']")
    WebElement senderName;

    @FindBy(xpath = "//*[@id='uid1']/div[2]/div[5]/input")
    WebElement mailTopic;

    @FindBy(xpath = "//*[@class = 'subject-text ellipsis']")
    private WebElement subjectText;

    @FindBy(xpath = "//iframe[@class = 'squireIframe']")
    WebElement frame;


    @FindBy(xpath = "//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")
    WebElement textContain;

    @FindBy(xpath = "//*[@data-original-title = 'Закрыть']")
    WebElement closeButton;

    @FindBy(xpath = "//*[@aria-label='Сохранить']")
    private WebElement saveButton;

    @FindBy(xpath = "//span[text() = 'Черновики']")
    WebElement draft;

    @FindBy(xpath = "//*[@ng-repeat = 'conversation in conversations track by conversation.ID']")
    List<WebElement> draftList;

    @FindBy(xpath = "//*[text()='Отправить']")
    WebElement sendButton;

    @FindBy(xpath = "//span[@ng-bind-html = '$message']")
    private WebElement messagePopUp;


    public void createNewMessage(Mail mail) {


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

        saveButton.click();

        waitForVisibilityOfAllElementsLocatedBy(messagePopUp);


    }

    public void veryfySendMessage(Mail mail) throws DraftNotFoundException {

        waitForElementToBeClickable(draft);
        draft.click();
        waitForListElements(draftList);


        List<WebElement> list = (List<WebElement>) draftList;

        for (WebElement webElement : list) {
            if (senderName.getText().equals(mail.getSenderMail()) && subjectText.getText().equals(mail.getTopic())) {

                webElement.click();

                WebElement iFrame = frame;
                getDriver().switchTo().frame(iFrame);


                if (textContain.getText().equals(mail.getTextContain())) {
                    getDriver().switchTo().defaultContent();


                    sendButton.click();
                    waitForVisibilityOfAllElementsLocatedBy(messagePopUp);

                } else throw new DraftNotFoundException("The draft has not been found");
            }
            break;
        }


    }


}

