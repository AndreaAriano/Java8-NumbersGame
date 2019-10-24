package com.bira0003.numbersgame.listeners;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class LoadedEvent extends Event {

    public static final EventType<LoadedEvent> PANE_LOADED =
            new EventType<LoadedEvent>(LoadedEvent.ANY, "PANE_LOADED");

    public LoadedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public LoadedEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
