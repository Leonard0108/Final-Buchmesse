package kickstart.Exception;

import kickstart.Events.Event;


public class Event_Full_Exception extends Exception {

    private static final String DEFAULT_MESSAGE = "Event is full";

    public Event_Full_Exception() {
        super(DEFAULT_MESSAGE);
    }

    public Event_Full_Exception(Event event) {
        super(String.format("Event %s is full", event.getName()));
    }

}