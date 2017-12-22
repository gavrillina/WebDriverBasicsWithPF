public class Mail {

    private String senderMail;
    private String topic;
    private String textContain;

    public Mail(String senderMail, String topic, String textContain) {
        this.senderMail = senderMail;
        this.topic = topic;
        this.textContain = textContain;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public String getTopic() {
        return topic;
    }

    public String getTextContain() {
        return textContain;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTextContain(String textContain) {
        this.textContain = textContain;
    }

}
