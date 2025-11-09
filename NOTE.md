### How to run
Change directory to src, then
```
javac -cp "../lib/TraaS.jar;." TestTraaS.java; java -cp "../lib/TraaS.jar;." TestTraaS
```
. : to start at current directory
### Requirement
- Create JavaDoc for every class created
### Features needed (Need to specify library's branch)
Source:
https://sumo.dlr.de/javadoc/traas/
From TraaS.jar:
``` 
SumoTraciConnection  conn  =  new  SumoTraciConnection(sumo_bin, config_file);
```
- Step through the simulation in real time
From TraaS.jar:
```
conn.do_timestep();
```
- Display traffic light states and phase durations:
- 
```
int  tlsPhase  = (int)conn.do_job_get(Trafficlight.getPhase("InSertNameHere"));
String  tlsPhaseName  = (String)conn.do_job_get(Trafficlight.getPhaseName("InserNameHere"));
```
- Inject vehicles
- Read telemetry
- Control traffic lights programmatically

- Enable control over vehicle parameters (speed, color, route)
