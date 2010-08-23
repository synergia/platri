/*
 *  Manager.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <iostream>
#include "Manager.h"

#define log(str) cout << "[TUIO] " << str << endl;


using namespace std;
using namespace TUIO;

Manager::Manager(){
	tuioClient = new TuioClient();
	tuioClient->addTuioListener(this);
	tuioClient->connect();
}


void Manager::addTuioObject(TuioObject *tobj){
	log("Object add");
}

void Manager::updateTuioObject(TuioObject *tobj){
	log("Object update");
}

void Manager::removeTuioObject(TuioObject *tobj){
	log("Object remove");
}

void Manager::addTuioCursor(TuioCursor *tcur){
	log("Cursor add");
}

void Manager::updateTuioCursor(TuioCursor *tcur){
	log("Cursor update");
}

void Manager::removeTuioCursor(TuioCursor *tcur){
	log("Cursor remove");
}

void Manager::refresh(TuioTime ftime){
	log("Refresh");
}
