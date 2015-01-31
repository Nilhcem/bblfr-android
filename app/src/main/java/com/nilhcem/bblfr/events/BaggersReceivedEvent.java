package com.nilhcem.bblfr.events;

import com.nilhcem.bblfr.model.Baggers;

public class BaggersReceivedEvent {

    public final Baggers baggers;

    public BaggersReceivedEvent(Baggers receivedBaggers) {
        baggers = receivedBaggers;
    }
}
