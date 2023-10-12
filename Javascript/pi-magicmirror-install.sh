#!/bin/bash
mv MagicMirror MagicMirror-`date +'%F-%T'`
git clone https://github.com/MichMich/MagicMirror
cd MagicMirror/
npm install
cp config/config.js.sample config/config.js
npm run start 
