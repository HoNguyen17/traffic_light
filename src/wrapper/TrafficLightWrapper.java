package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Trafficlight;

import java.util.List;
import java.util.ArrayList;

public class TrafficLightWrapper {
    String ID;
    TrafficLightWrapper(String temp){
        ID = temp;
        System.out.println("Added " + temp + ".");
    }
//=================GETTER================================
    // get ID
    public String getID(int po) {
        if (po == 1) {System.out.print(" " + ID);}
        return ID;
    }
    // get phase number
    public int getPhaseNum(SimulationWrapper temp, int po) {
        try {
            int tlsPhase = (int)temp.conn.do_job_get(Trafficlight.getPhase(ID));
            if (po == 1) {System.out.println(String.format("tlsPhase of %s: %d", ID, tlsPhase));}
            return tlsPhase;
        }
        catch(Exception A) {
            System.out.println("Failed to get phase number.");
        }
        return -1;
    }
    // get phase definition (Red-Green-Yellow)
    public String getPhaseDef(SimulationWrapper temp, int po) {
        try {
            String lightState = (String)temp.conn.do_job_get(Trafficlight.getRedYellowGreenState(ID));
            if (po == 1) {System.out.println(String.format("Current phase definition of %s: %s", ID, lightState));}
            return lightState;
        }
        catch (Exception B) {
            System.out.println("Failed to get TL phase definition.");
        }
        return null;
    }
    // get controlled links
    public List<String[][]> getControlledLinks(SimulationWrapper temp, int po) {
        try {
            List<String[][]> controlledLinks = (List<String[][]>)temp.conn.do_job_get(Trafficlight.getControlledLinks(ID));
            if (po == 1){System.out.println("Current phase definition of " + ID + ":" + controlledLinks);}
            return controlledLinks;
        }   
        catch (Exception C) {
            System.out.println("Cannot get controlled links of traffic light");
        }
        return null;
    }
//=================SETTER================================

//=================STATIC================================
    // update all traffic light IDs of simulation
    public static void updateTrafficLightIDs(SimulationWrapper temp) {
        try {
            @SuppressWarnings("unchecked")
            List<String> IDsList = (List<String>)temp.conn.do_job_get(Trafficlight.getIDList());
            for (String x : IDsList) {
                TrafficLightWrapper y = new TrafficLightWrapper(x);
                temp.TrafficLightList.add(y);
            }
        }
        catch (Exception A) {
            System.out.println("Set up traffic lights failed.");
        }
    }
}