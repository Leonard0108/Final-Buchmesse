package kickstart.Exception;

import kickstart.Equipments.Equipment;


public class All_Equipment_Booked extends Exception {

    private static final String DEFAULT_MESSAGE = "Sorry, the item is fully booked";

    public All_Equipment_Booked() {
        super(DEFAULT_MESSAGE);
    }

    public All_Equipment_Booked(Equipment equipment) {
        super(String.format("Equipment %s is fully booked", equipment.getName()));
    }

}