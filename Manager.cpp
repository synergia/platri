/*
 *  Manager.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <stdio.h>
#include "Manager.h"


using namespace std;
using namespace TUIO;

Manager::Manager(){
	tuioClient = new TuioClient();
	tuioClient->addTuioListener(this);
	tuioClient->connect();
}


void Manager::setApp(Application *app){
	_app = app;
	_app->start();
}

Application* Manager::app(){
	// TODO: Add throw Exception if _app is null
	return _app;
}

void Manager::display(){
	// display application
	app()->display();
	
	// display objects	
	tuioClient->lockObjectList(); // prevent TUIO thread disaster
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		(*it)->display();
	}
	tuioClient->unlockObjectList();
	
	// TODO: display cursors
}



// TUIO callbacks

void Manager::addTuioObject(TuioObject *tobj){
	objects.push_back(app()->createObject(tobj));
}

void Manager::updateTuioObject(TuioObject *tobj){
	// update is done automatically via TuioObject pointers in Object instances
	//printf("Objects#size: %d\n", objects.size());
}

void Manager::removeTuioObject(TuioObject *tobj){
	// see http://gist.github.com/545615
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		if((*it)->checkTuioObject(tobj)){
			objects.remove(*it);
			return;
		}
	}
}

void Manager::addTuioCursor(TuioCursor *tcur){
	cursors.push_back(app()->createCursor(tcur));
	printf("Cursors#size: %d\n", (int)cursors.size());}

void Manager::updateTuioCursor(TuioCursor *tcur){
	//log("Cursor update");
}

void Manager::removeTuioCursor(TuioCursor *tcur){
	for (list<Cursor*>::iterator it = cursors.begin(); it != cursors.end(); ++it){
		if((*it)->tcur == tcur){
			cursors.remove(*it);
		}
	}
	
	printf("Cursors#size: %d\n", (int)cursors.size());}

void Manager::refresh(TuioTime ftime){
	//log("Refresh");
}




