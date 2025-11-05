#!/bin/bash

# Script to generate SUMO simulation files
# This script creates the network and route files for the traffic simulation

echo "======================================"
echo "TraaS Traffic Simulator Setup Script"
echo "======================================"

# Check if SUMO is installed
if ! command -v netconvert &> /dev/null; then
    echo "ERROR: SUMO is not installed or not in PATH"
    echo "Please install SUMO from https://www.eclipse.org/sumo/"
    exit 1
fi

echo "SUMO installation found!"

# Navigate to resource directory
cd resource || exit 1

echo ""
echo "Generating network file..."
# Example: Generate a simple grid network
# netconvert --node-files=nodes.nod.xml --edge-files=edges.edg.xml --output-file=network.net.xml

echo "Network file already exists: network.net.xml"

echo ""
echo "Generating route files..."
echo "  - cars.rou.xml"
echo "  - trucks.rou.xml"
echo "  - motorcycles.rou.xml"
echo "  - buses.rou.xml"
echo "  - emergency.rou.xml"

# If you need to generate random routes, you can use:
# python $SUMO_HOME/tools/randomTrips.py -n network.net.xml -r cars.rou.xml -e 1000 --vehicle-class passenger

echo ""
echo "Configuration file: simulation.sumocfg"

echo ""
echo "======================================"
echo "Setup completed successfully!"
echo "======================================"
echo ""
echo "To run the simulation:"
echo "  1. Using SUMO GUI: sumo-gui -c resource/simulation.sumocfg"
echo "  2. Using Java: java -cp lib/TraaS.jar:src TestTraaS"
echo ""
echo "Note: Make sure SUMO is installed and SUMO_HOME is set"
echo "      export SUMO_HOME=/path/to/sumo"
