package gov.utah.dts.quartz.testing;

import org.springframework.stereotype.Component;

/**
 * Created by jjensen on 4/5/16.
 */
@Component
public class EventHolderBean {
    private Boolean eventFired = false;

    public Boolean getEventFired() {
        return eventFired;
    }

    public void setEventFired(Boolean eventFired) {
        this.eventFired = eventFired;
    }
}
