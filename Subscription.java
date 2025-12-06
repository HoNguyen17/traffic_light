import it.polito.appeal.traci.SumoTraciConnection;
import it.polito.appeal.traci.TraCIException;
import de.tudresden.sumo.cmd.Simulation;
import de.tudresden.sumo.cmd.Vehicle;
import de.tudresden.sumo.cmd.Inductionloop;
import de.tudresden.sumo.cmd.Trafficlight;
import de.tudresden.sumo.config.Constants;
import de.tudresden.sumo.util.Observer;
import de.tudresden.sumo.util.Observable;
import de.tudresden.sumo.subscription.VariableSubscription;
import de.tudresden.sumo.subscription.SubscribtionVariable;
import de.tudresden.sumo.subscription.SubscriptionObject;
import de.tudresden.sumo.subscription.ResponseType;
import de.tudresden.sumo.objects.SumoVehicleData;
import de.tudresden.sumo.objects.SumoStringList;
import de.tudresden.sumo.objects.SumoPrimitive;
import de.tudresden.sumo.objects.SumoPosition2D;

public class Subscription implements Observer {

    static SumoTraciConnection conn = null;

    public static void main(String[] args) {
        String sumo_bin = "sumo";
        String config_file = "../resource/test_2_traffic.sumocfg";
        double step_length = 1.0;

        if (args.length > 0) {
            sumo_bin = args[0];
        }
        if (args.length > 1) {
            config_file = args[1];
        }

        try {
            conn = new SumoTraciConnection(sumo_bin, config_file);
            conn.addOption("step-length", step_length + "");
            conn.addOption("start", "true"); //start sumo immediately

            //start Traci Server
            conn.runServer();
            conn.setOrder(1);
            conn.addObserver(new Subscription());

            VariableSubscription vs = new VariableSubscription(SubscribtionVariable.simulation, 0, 100000 * 60, "");
            vs.addCommand(Constants.VAR_DEPARTED_VEHICLES_IDS);
            conn.do_subscription(vs);


            for (int i = 0; i < 100; i++) {

                Thread.sleep(200);
                conn.do_timestep();
                //conn.do_job_set(Vehicle.addFull("v" + i, "r1", "DEFAULT_VEHTYPE", "now", "0", "0", "max", "current", "max", "current", "", "", "", 0, 0));
                double timeSeconds = (double)conn.do_job_get(Simulation.getTime());
                System.out.println("Step: " + i);

            }

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

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



}