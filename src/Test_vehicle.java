import it.polito.appeal.traci.SumoTraciConnection;
import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.cmd.Inductionloop;
import de.tudresden.sumo.cmd.Trafficlight;
import de.tudresden.sumo.objects.SumoVehicleData;

import java.util.List;

public class Test_vehicle {
    public static void main ( String [] args){
        String sumo_bin = "sumo-gui";
        String config_file = "../resource/test_2_traffic.sumocfg";
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

            for (int i = 0; i < 200; i++) {
                Thread.sleep(200);
                conn.do_timestep();

                // --- vehicle & simulation info ---
                List<String> vehicleID = (List<String>) conn.do_job_get(Vehicle.getIDList());
                System.out.println("Vehicle IDs:");
                for (String id : vehicleID) {
                    System.out.println("  " + id);
                }


                // Optional: Uncomment to show induction loop data
                // SumoVehicleData vehData = (SumoVehicleData) conn.do_job_get(Inductionloop.getVehicleData("loop1"));
                // for (SumoVehicleData.VehicleData d : vehData.ll) {
                //     System.out.println(String.format("  veh=%s len=%s entry=%s leave=%s type=%s",
                //         d.vehID, d.length, d.entry_time, d.leave_time, d.typeID));
                // }
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
