
import de.tudresden.sumo.cmd.*;
import de.tudresden.ws.container.SumoStringList;
import de.tudresden.ws.container.SumoVehicleData;
import it.polito.appeal.traci.SumoTraciConnection;

/**
 * SUMO utility methods for TraaS Provides helper methods for common SUMO
 * operations
 *
 * @author Traffic Light Project Team
 */
public class TraaSTools {

    /**
     * Get the number of vehicles currently in the simulation
     */
    public static int getVehicleCount(SumoTraciConnection conn) throws Exception {
        SumoStringList vehicles = (SumoStringList) conn.do_job_get(Vehicle.getIDList());
        return vehicles.size();
    }

    /**
     * Get the current simulation time
     */
    public static double getSimulationTime(SumoTraciConnection conn) throws Exception {
        return (double) conn.do_job_get(Simulation.getTime());
    }

    /**
     * Get list of all vehicle IDs in the simulation
     */
    public static SumoStringList getVehicleIDs(SumoTraciConnection conn) throws Exception {
        return (SumoStringList) conn.do_job_get(Vehicle.getIDList());
    }

    /**
     * Get the speed of a specific vehicle
     */
    public static double getVehicleSpeed(SumoTraciConnection conn, String vehicleID) throws Exception {
        return (double) conn.do_job_get(Vehicle.getSpeed(vehicleID));
    }

    /**
     * Get the position of a specific vehicle
     */
    public static Object getVehiclePosition(SumoTraciConnection conn, String vehicleID) throws Exception {
        return conn.do_job_get(Vehicle.getPosition(vehicleID));
    }

    /**
     * Get the road ID where the vehicle is currently located
     */
    public static String getVehicleRoadID(SumoTraciConnection conn, String vehicleID) throws Exception {
        return (String) conn.do_job_get(Vehicle.getRoadID(vehicleID));
    }

    /**
     * Set the speed of a specific vehicle
     */
    public static void setVehicleSpeed(SumoTraciConnection conn, String vehicleID, double speed) throws Exception {
        conn.do_job_set(Vehicle.setSpeed(vehicleID, speed));
    }

    /**
     * Change the target/route of a vehicle
     */
    public static void setVehicleRoute(SumoTraciConnection conn, String vehicleID, SumoStringList edgeList) throws Exception {
        conn.do_job_set(Vehicle.setRoute(vehicleID, edgeList));
    }

    /**
     * Get list of all traffic light IDs
     */
    public static SumoStringList getTrafficLightIDs(SumoTraciConnection conn) throws Exception {
        return (SumoStringList) conn.do_job_get(Trafficlight.getIDList());
    }

    /**
     * Get the current state of a traffic light
     */
    public static String getTrafficLightState(SumoTraciConnection conn, String tlID) throws Exception {
        return (String) conn.do_job_get(Trafficlight.getRedYellowGreenState(tlID));
    }

    /**
     * Set the state of a traffic light
     */
    public static void setTrafficLightState(SumoTraciConnection conn, String tlID, String state) throws Exception {
        conn.do_job_set(Trafficlight.setRedYellowGreenState(tlID, state));
    }

    /**
     * Get the number of vehicles waiting at a traffic light
     */
    public static int getWaitingVehicles(SumoTraciConnection conn, String edgeID) throws Exception {
        return (int) conn.do_job_get(Edge.getLastStepHaltingNumber(edgeID));
    }

    /**
     * Get list of all edge IDs in the network
     */
    public static SumoStringList getEdgeIDs(SumoTraciConnection conn) throws Exception {
        return (SumoStringList) conn.do_job_get(Edge.getIDList());
    }

    /**
     * Get the mean speed on an edge
     */
    public static double getEdgeMeanSpeed(SumoTraciConnection conn, String edgeID) throws Exception {
        return (double) conn.do_job_get(Edge.getLastStepMeanSpeed(edgeID));
    }

    /**
     * Get the number of vehicles on an edge
     */
    public static int getEdgeVehicleCount(SumoTraciConnection conn, String edgeID) throws Exception {
        return (int) conn.do_job_get(Edge.getLastStepVehicleNumber(edgeID));
    }

    /**
     * Print simulation statistics
     */
    public static void printSimulationStats(SumoTraciConnection conn) throws Exception {
        double time = getSimulationTime(conn);
        int vehicleCount = getVehicleCount(conn);

        System.out.println("=== Simulation Statistics ===");
        System.out.println("Time: " + time + " seconds");
        System.out.println("Active vehicles: " + vehicleCount);
        System.out.println("============================");
    }

    /**
     * Print detailed vehicle information
     */
    public static void printVehicleInfo(SumoTraciConnection conn, String vehicleID) throws Exception {
        double speed = getVehicleSpeed(conn, vehicleID);
        String roadID = getVehicleRoadID(conn, vehicleID);

        System.out.println("Vehicle: " + vehicleID);
        System.out.println("  Speed: " + speed + " m/s");
        System.out.println("  Road: " + roadID);
    }
}
