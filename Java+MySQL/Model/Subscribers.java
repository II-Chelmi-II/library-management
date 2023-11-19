package Model;

import java.util.Date;

public class Subscribers {
    private int subscriberId;
    private String name;
    private Date subscriptionDate;

    public Subscribers(int subscriberId, String name, Date subscriptionDate) {
        this.subscriberId = subscriberId;
        this.name = name;
        this.subscriptionDate = subscriptionDate;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}