package com.nilhcem.bblfr.events.splashscreen;

import com.nilhcem.bblfr.model.JsonData;

public class BaggersReceivedEvent {

    public final JsonData baggers;

    public BaggersReceivedEvent(JsonData receivedBaggers) {
        baggers = receivedBaggers;
    }
}
