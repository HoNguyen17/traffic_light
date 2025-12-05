import de.tudresden.sumo.cmd.Vehicle;
import wrapper.*;

import java.util.List;

public class MainTest { 
public static void main ( String [] args){ 
        // config_file path is based on this class
        String config_file = "../resource/test_2_traffic.sumocfg";
        // String sumo_bin = "sumo-gui";
        double step_length = 1;
        SimulationWrapper A = new SimulationWrapper(config_file, step_length);
        try {
            A.Start();
            A.printTrafficLightList();
            for (int i = 0; i < 100; i++) {
                Thread.sleep(200);
                A.Step();
                A.getTime(1);
                A.getTLPhase(0);

                // Test Vehicle Stuff
                List<String> vehID = A.getIDList(); // Get IDs list of all current vehicles in the current simulation
                if (!vehID.isEmpty()) { // Check if there is at least one vehicle in the simulation
                    String firstVehID = vehID.get(0); // Choose the first vehicle in the list to test
                    System.out.println("Testing for the vehicle: " + firstVehID);
                    A.getTypeID(firstVehID);
                    A.getColor(firstVehID);
                    A.getPosition(firstVehID);
                    A.getSpeed(firstVehID);
                    A.setSpeed(firstVehID, 36.369);
                    A.setColor(firstVehID, 255, 0, 0, 255); // red
                    A.setColor(firstVehID, 0, 255, 0, 255); // green
                    A.setColor(firstVehID, 0, 0, 255, 255); // blue
                }
                else  {
                    System.out.println("No vehicles found");
                }
                System.out.println("-----------------------------------------------");
            }
            A.End();
        }
        catch (Exception e) {
            System.out.println("Error in Main");
        }
    }
}
