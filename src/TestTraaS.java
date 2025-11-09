// Code taken from https://github.com/eclipse-sumo/sumo/blob/main/tests/complex/traas/simple/data/Main.java
import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.cmd.Inductionloop;
import de.tudresden.sumo.cmd.Trafficlight;
import de.tudresden.sumo.objects.SumoVehicleData;
class TestTraa { 
public static void main ( String [] args){ 
String sumo_bin = "sumo";
        String config_file = "data/test_1.sumocfg";
        double step_length = 1;

        if (args.length > 0) {
            sumo_bin = args[0];
        }
        if (args.length > 1) {
            config_file = args[1];
        }

        try {
            SumoTraciConnection conn = new SumoTraciConnection(sumo_bin, config_file);
            conn.addOption("step-length", step_length + "");
            conn.addOption("start", "true"); //start sumo immediately

            //start Traci Server
            conn.runServer();
            conn.setOrder(1);

            for (int i = 0; i < 100; i++) {
                Thread.sleep(200);
                conn.do_timestep();
            //     conn.do_job_set(Vehicle.addFull("v" + i, "r1", "car", "now", "0", "0", "max", "current", "max", "current", "", "", "", 0, 0));
                double timeSeconds = (double)conn.do_job_get(Simulation.getTime());
                int tlsPhase = (int)conn.do_job_get(Trafficlight.getPhase("J1"));
                String tlsPhaseName = (String)conn.do_job_get(Trafficlight.getPhaseName("J1"));
                System.out.println(String.format("Step %s, tlsPhase %s (%s)", timeSeconds, tlsPhase, tlsPhaseName));
                   
                System.out.println(timeSeconds);

            //     SumoVehicleData vehData = (SumoVehicleData)conn.do_job_get(Inductionloop.getVehicleData("loop1"));
            //     for (SumoVehicleData.VehicleData d : vehData.ll) {
            //         System.out.println(String.format("  veh=%s len=%s entry=%s leave=%s type=%s", d.vehID, d.length, d.entry_time, d.leave_time, d.typeID));
            //     }
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
} 
