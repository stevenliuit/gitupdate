package com.jcloud.b2c.platform.domain.vo;

public class MqTopic {

    private String name;

    private String nunOfCusmor;

    private String messageEnqueued;

    private String messageDequeued;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNunOfCusmor() {
        return nunOfCusmor;
    }

    public void setNunOfCusmor(String nunOfCusmor) {
        this.nunOfCusmor = nunOfCusmor;
    }

    public String getMessageEnqueued() {
        return messageEnqueued;
    }

    public void setMessageEnqueued(String messageEnqueued) {
        this.messageEnqueued = messageEnqueued;
    }

    public String getMessageDequeued() {
        return messageDequeued;
    }

    public void setMessageDequeued(String messageDequeued) {
        this.messageDequeued = messageDequeued;
    }


}
