#!/bin/bash

# Temperature (divide by 1000)
calc() { awk "BEGIN{ print $* }" ;}
temperature=`calc \`cat /sys/class/thermal/thermal_zone0/temp\`/1000`
echo `date` "temperature= $temperature 'C"

