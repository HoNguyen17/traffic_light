import wrapper.*;

import java.util.List;

public class MainTest { 
public static void main ( String [] args){ 
        // config_file path is based on this class path
        String config_file = "../resource/test_2_traffic.sumocfg"; 
        double step_length = 1;
        String sumo_bin = "sumo-gui";
        SimulationWrapper A = new SimulationWrapper(config_file, step_length, sumo_bin);
        try {
            A.Start();
            A.printTrafficLightList();
            for (int i = 0; i < 100; i++) {
                A.Step();
                A.getTime(1);
                A.getTLPhaseNum(0);
                //A.getTLPhaseDef(0);
                //A.getTLControlledLinks(0);
                if(i == 10){
                    //A.setTLPhaseDef2(0,"rrrrrrrrrrrr");
                    class Test2 extends Thread {
                        public void run() {
                            A.setTLPhaseDef(0,"GGGGGGGGGGGG");
                        }
                    }
                    Test2 hmm = new Test2();
                    hmm.start();
                }

            }
            A.End();
        }
        catch (Exception e) {
            System.out.println("Error in Main");
        }
    }
} 
