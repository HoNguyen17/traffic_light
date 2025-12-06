package wrapper;

import it.polito.appeal.traci.SumoTraciConnection;
import it.polito.appeal.traci.TraCIException;

import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Trafficlight;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.cmd.Inductionloop;

import de.tudresden.sumo.util.Observer;
import de.tudresden.sumo.util.Observable;

import de.tudresden.sumo.config.Constants;

import de.tudresden.sumo.subscription.VariableSubscription;
import de.tudresden.sumo.subscription.SubscribtionVariable;
import de.tudresden.sumo.subscription.SubscriptionObject;
import de.tudresden.sumo.subscription.ResponseType;

import de.tudresden.sumo.objects.SumoVehicleData;
import de.tudresden.sumo.objects.SumoStringList;
import de.tudresden.sumo.objects.SumoPrimitive;
import de.tudresden.sumo.objects.SumoPosition2D;

import java.util.List;
import java.util.ArrayList;

public class SimulationWrapper implements Observer{
    protected static SumoTraciConnection conn;
    protected final List<TrafficLightWrapper> TrafficLightList = new ArrayList<TrafficLightWrapper>();
    //protected final List<EdgeWrapper> EdgeList = new ArrayList<EdgeWrapper>();
    //List<String> VehicleList = new ArrayList<String>();

    protected String sumocfg;
    protected int delay = 200;
    double step_length;

    // Constructor 1
    public SimulationWrapper() {

    }
    public SimulationWrapper(String sumocfg, double step_length, String sumo_bin){
        this.sumocfg = sumocfg;
        this.step_length = step_length;
        conn = new SumoTraciConnection(sumo_bin, sumocfg);
        conn.addOption("step-length", step_length + "");
        conn.addOption("start", "true"); //start sumo immediately
        System.out.println("Simulation created");
    }
    // Constructor 2
    SimulationWrapper(String sumocfg){
        String sumo_bin = "sumo";
        double step_length = 1;
        conn = new SumoTraciConnection(sumo_bin, sumocfg);
        conn.addOption("step-length", step_length + "");
        conn.addOption("start", "true"); //start sumo immediately
        System.out.println("Simulation created");
    }
//===== SIMULATION STUFF ==================================
    // Start simulation, update TrafficLightList, more will be implemented
    public void Start(){
        try {
            conn.runServer();
            conn.setOrder(1);
            conn.addObserver(new SimulationWrapper());
            
            VariableSubscription vs = new VariableSubscription(SubscribtionVariable.simulation, 0, 100000 * 60, "");
            vs.addCommand(Constants.VAR_DEPARTED_VEHICLES_IDS);
            conn.do_subscription(vs);

            TrafficLightWrapper.updateTrafficLightIDs(this);
            System.out.println("Started successfully.");
        }
        catch(Exception e) {System.out.println("Failed to start.");}
    }
    // Do a simulation's time step
    public void Step(){
        try {
            Thread.sleep(delay);
            conn.do_timestep();
        }
        catch(Exception e) {System.out.println("Failed to step.");}
    }
    // Close simulation
    public void End() {
        conn.close();
    }
    // Get simulation time
    public double getTime(int po) {
        try {
            double time = (double)conn.do_job_get(Simulation.getTime());
            if (po == 1) {System.out.println("Current Time: " + time);}
            return time;
        }
        catch(Exception e) {System.out.println("Can't get the time.");}
        return -1;
    }
    //testing
    public void update(Observable arg0, SubscriptionObject so) {
        //System.out.println("Subscription id=" + so.id + " domain=" +  so.domain + " name=" + so.name + " var=" + so.variable + " status=" + so.status + " ret=" + so.return_type + " resp=" + so.response.getID());

        if (so.response == ResponseType.SIM_VARIABLE) {
            assert(so.variable == Constants.VAR_DEPARTED_VEHICLES_IDS);
            SumoStringList ssl = (SumoStringList) so.object;
            if (ssl.size() > 0) {
                for (String vehID : ssl) {
                    System.out.println("Subscription Departed vehicles: " + vehID);
                    VariableSubscription vs = new VariableSubscription(SubscribtionVariable.vehicle, 0, 100000 * 60, vehID);
                    vs.addCommand(Constants.VAR_POSITION);
                    vs.addCommand(Constants.VAR_SPEED);
                    try {
                        conn.do_subscription(vs);
                    } catch (Exception ex) {
                        System.err.println("subscription to " + vehID + " failed");
                    }
                }
            }
        } else if (so.response == ResponseType.VEHICLE_VARIABLE) {
            if (so.variable == Constants.VAR_SPEED) {
                SumoPrimitive sp = (SumoPrimitive) so.object;
                System.out.println("Speed of vehicle " + so.id + ": "  + sp.val);
            } else if (so.variable == Constants.VAR_POSITION) {
                SumoPosition2D sc = (SumoPosition2D) so.object;
                System.out.println("Position of vehicle " + so.id + ": x = " + sc.x + " y = " + sc.y);
            }
        }

    }

//===== TRAFFIC LIGHT STUFF ===============================
//===== GETTER ============================================
    // print all traffic light IDs
    public void printTrafficLightList() {
        System.out.println("List of Traffic Light IDs:");
        for (TrafficLightWrapper x : TrafficLightList) {
            x.getID(1);
        }
        System.out.println("");
    }
    // get phase number of a traffic light
    public int getTLPhaseNum(int temp) {
        TrafficLightWrapper x = TrafficLightList.get(temp);
        int phaseNum= x.getPhaseNum(this, 1);
        return phaseNum;
    }
    // get phase definition of a traffic light (current light state)
    public String getTLPhaseDef(int temp) {
        TrafficLightWrapper x = TrafficLightList.get(temp);
        String phaseDef = x.getPhaseDef(this, 1);
        return phaseDef;
    }
    public List<String[][]> getTLControlledLinks(int temp) {
        TrafficLightWrapper x = TrafficLightList.get(temp);
        List<String[][]> controlledLinks = x.getControlledLinks(this, 1);
        return null;
    }
//===== SETTER ============================================
    public void setTLPhaseDef(int temp, String input) {
        TrafficLightWrapper x = TrafficLightList.get(temp);
        x.setPhaseDefWPT(this, input, 10);
    }
//===== VEHICLE STUFF =====================================
    // public double getVehicleSpeed(int temp) {
    //     double speed = Vehicle.getSpeed(this, VehicleList.get(temp), 1);
    //     return speed;
    // }
//===== GETTER ============================================
//===== SETTER ============================================
}