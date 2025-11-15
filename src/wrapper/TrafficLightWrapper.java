package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Trafficlight;

import java.util.List;
import java.util.ArrayList;

public class TrafficLightWrapper {
    TrafficLightWrapper(){
        System.out.println("Test");
    }
    // List all traffic light IDs
    public static List<String> listTrafficLight(SumoTraciConnection temp, int po) {
        try {
            List<String> IDsList = (List<String>)temp.do_job_get(Trafficlight.getIDList());
            if (po == 1) {
                System.out.println("List of Traffic Light IDs: ");
                for (String x : IDsList) {System.out.print(x + " ");}
                System.out.print("\n");
            }
            return IDsList;
        }
        catch (Exception A) {
            System.out.println("Didn't work");
        }
        List<String> bin = new ArrayList<String>();
        return bin;
    }
    // test
    public static void Test(SumoTraciConnection temp){
        try {
            int tlsPhase = (int)temp.do_job_get(Trafficlight.getPhase("J1"));
            System.out.println(String.format("tlsPhase of J1: %d", tlsPhase));
        }
        catch(Exception B) {
            System.out.println("Didn't work");
        }
    }
}