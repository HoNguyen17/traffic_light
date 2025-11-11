package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Trafficlight;

public class TrafficLight {
    TrafficLight(){
        System.out.println("Test");
    }
    public static void listTrafficLight(SumoTraciConnection temp) {
        try {
            int tlsPhase = (int)temp.do_job_get(Trafficlight.getPhase("J1"));
            String tlsPhaseName = (String)temp.do_job_get(Trafficlight.getPhaseName("J1"));
            System.out.println(String.format("tlsPhase %s (%s)", tlsPhase, tlsPhaseName));
        }
        catch (Exception A) {
        System.out.println("Didn't work");
    }
    }
}