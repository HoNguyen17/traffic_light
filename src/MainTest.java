import wrapper.*;

import java.util.List;

public class MainTest { 
public static void main ( String [] args){ 
        // config_file path is based on this class
        String config_file = "../resource/test_2_traffic.sumocfg"; 
        double step_length = 1;
        SimulationWrapper A = new SimulationWrapper(config_file, step_length);
        try {
            A.Start();
            for (int i = 0; i < 100; i++) {
                Thread.sleep(200);
                A.Step();
                A.Test();
                A.getTime(1);
                // print all traffic light IDs
                if ( i == 0 ) {
                    TrafficLightWrapper.listTrafficLight(A.getSimulation(), 1);
                }
            }
            A.End();
        }
        catch (Exception e) {
            System.out.println("Error in Main");
        }
    }
} 
