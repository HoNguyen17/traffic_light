package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.cmd.Vehicletype;
import de.tudresden.sumo.objects.SumoPosition2D;
import de.tudresden.sumo.objects.SumoColor;


// import javax.swing.text.Position;
import java.util.List;
import java.util.ArrayList;

public class VehicleWrapper {
    String ID;
    VehicleWrapper(String temp){
        ID = temp;
        System.out.println("Added " + temp + ".");
    }

    // get Vehicle ID
    public String getID(int po) {
        if (po == 1) {System.out.println(ID);}
        return ID;
    }

    // get Vehicle position
    public SumoPosition2D getPosition(wrapper.SimulationWrapper temp, int po) {
        try {
            SumoPosition2D position = (SumoPosition2D) temp.conn.do_job_get(Vehicle.getPosition(ID));
            if (po==1) {
                System.out.println(String.format("Position of the current vehicle: %s", position));
            }
            return position;
        }
        catch(Exception e) {
            System.out.println("Cannot get position." + e.getMessage());
            return null;
        }
    }

    // get Vehicle speed
    public double getSpeed(wrapper.SimulationWrapper temp, int po) {
        try {
            double speed = (double) temp.conn.do_job_get(Vehicle.getSpeed(ID));
            if (po==1) {
                System.out.println(String.format("Speed of the current vehicle: %s m/s", speed));
            }
            return speed;
        }
        catch(Exception e) {
            System.out.println("Cannot get speed. " + e.getMessage());
            return 0;
        }
    }

    // get Vehicle's ID list
    public static List<String> getIDList(wrapper.SimulationWrapper temp, int po) { // the method should be static, because it returns all vehicles, not one.
        try {
            @SuppressWarnings("unchecked")
            List<String> idList = (List<String>) temp.conn.do_job_get(Vehicle.getIDList());

            if (po==1) {
                System.out.println(String.format("ID list of all vehicle in the current simulation: %s", idList));
            }
            return idList;
        }

        catch(Exception e) {
            System.out.println("Cannot get vehicle ID list." + e.getMessage());
            return null;
        }
    }

    // get Vehicle's type ID
    public String getTypeID(wrapper.SimulationWrapper temp, int po) {
        try {
            String typeID = (String) temp.conn.do_job_get(Vehicle.getTypeID(ID));

            if (po==1) {
                System.out.println(String.format("Type ID of vehicle %s: %s", typeID, ID));
            }
            return typeID;
        }

        catch(Exception e) {
            System.out.println("Cannot get type ID list of vehicle " + ID + e.getMessage());
            return null;
        }
    }

    // get Vehicle's color
    public SumoColor getColor(wrapper.SimulationWrapper temp, int po) {
        try {
            SumoColor color = (SumoColor) temp.conn.do_job_get(Vehicle.getColor(ID));

            // SUMO default color (undefined)
            if (color.r == -1 && color.g == -1 && color.b == 0 && color.a == -1) {
                if (po == 1) {
                    System.out.println("Vehicle " + ID + " has no custom color (using SUMO default which has the format r#g#b#a): " + color);
                }
                return color;
            }

            if (po==1) {
                System.out.println(String.format("Color of vehicle " + ID + ": " + color));
            }
            return color;
        }

        catch (Exception e) {
            System.out.println("Cannot get color of vehicle " + ID + e.getMessage());
            return null;
        }
    }
}
