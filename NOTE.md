### How to run
Change directory to src, then
```
javac -cp "../lib/TraaS.jar;." TestTraaS.java; java -cp "../lib/TraaS.jar;." TestTraaS
```
. : to start at current directory
### Requirement
- Create JavaDoc for every class created
### Features for wrapper (Need to specify library's branch)
#### Basic Description
TraaS uses static methods and procedural patterns. Your task is to build a clean, reusable, 
and extensible object-oriented wrapper around it. This wrapper should: 
- Encapsulate vehicle, edge, and traffic light logic
- Support querying and control via instance methods 
- Enable grouping, filtering, and event handling

Source for building wrapper:
https://sumo.dlr.de/javadoc/traas/ 
1. Use the TraaS API to connect to a running SUMO simulation.
From it.polito.appeal.traci.SumoTraciConnection:
``` 
SumoTraciConnection  conn  =  new  SumoTraciConnection(sumo_bin, config_file);
```
2. Step through the simulation in real time
From it.polito.appeal.traci.SumoTraciConnection:
```
conn.do_timestep();
```
3. Traffic Light Management
- Display traffic light states and phase durations:
From de.tudresden.sumo.cmd.Trafficlight:
```
int  tlsPhase  = (int)conn.do_job_get(Trafficlight.getPhase("InSertNameHere"));
String  tlsPhaseName  = (String)conn.do_job_get(Trafficlight.getPhaseName("InserNameHere"));
```
- Show traffic lights with current phase indicators. (same with above??)
- Allow users to adjust phase durations and observe effects on traffic flow. 
4. Vehicle Related
- Display moving vehicles with color-coded icons.
- Show subsets of vehicles according to their properties (filtered by e.g. color, speed, 
or location) (needed in wrapper)
5. Statistics & Analytics
  Track metrics such as: 
- Average speed 
- Vehicle density per edge 
- Congestion hotspots 
- Travel time distribution 
- Display charts and summaries in real time. 

- Inject vehicles, read telemetry, and control traffic lights programmatically.
### Feature unrelated with wrapper (maybe)
1. Interactive Map Visualization 
- Render the road network.
- Support zooming, panning, and camera rotation. 
3. Vehicle Injection & Control 
- Allow users to create vehicles on specific edges via GUI. (could be in wrapper)
- Support batch injection for stress testing. 
- Enable control over vehicle parameters (speed, color, route). (could be in wrapper)
4. Traffic Light Management 
- Enable manual phase switching via GUI. 
5. Exportable Reports 
- Save simulation statistics to CSV for external analysis. 
- Generate PDF summaries with charts, metrics, and timestamps. 
- Include filters (e.g. only red cars, only congested edges) in exports. 





