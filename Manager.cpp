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

#include "Fader.h"

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
	for (objects_t::iterator it = objects.begin(); it != objects.end(); ++it){
		it->second->display();
	}
	
	// display objects connections
	for (list<DirectedConnection*>::iterator it = connections.begin(); it != connections.end(); ++it){
		(*it)->display();
	}
	tuioClient->unlockObjectList();
	

	// display cursors
	tuioClient->lockCursorList(); // prevent TUIO thread disaster
	for (cursors_t::iterator it = cursors.begin(); it != cursors.end(); ++it){
		it->second->display();
	}
	tuioClient->unlockCursorList();
}



// TUIO callbacks

void Manager::addTuioObject(TuioObject *tobj){
	Object * obj = app()->createObject(tobj);
	obj->move();
	objects[tobj] = obj;
}

void Manager::updateTuioObject(TuioObject *tobj){
	tuioClient->lockObjectList(); // prevent TUIO thread disaster
	objects[tobj]->move();
	tuioClient->unlockObjectList();
}

void Manager::removeTuioObject(TuioObject *tobj){
	tuioClient->lockObjectList(); // prevent TUIO thread disaster
	
	Object * obj = objects[tobj];
	
	for(list<DirectedConnection *>::iterator it = connections.begin(); it != connections.end();){
		DirectedConnection * con = *it;
		if(con->check(obj)){
			it = connections.erase(it);
			delete con;
		} else {
			it++;
		}
	}
	
	objects.erase(tobj);

	tuioClient->unlockObjectList();
}


void Manager::addTuioCursor(TuioCursor *tcur){
	cursors[tcur] = app()->createCursor(tcur);
}

void Manager::updateTuioCursor(TuioCursor *tcur){
	tuioClient->lockCursorList();
	cursors[tcur]->move();
	tuioClient->unlockCursorList();
}

void Manager::removeTuioCursor(TuioCursor *tcur){
	tuioClient->lockCursorList();
	//Cursors * cur = cursors[tcur];
	cursors.erase(tcur);
	//delete cur;
	tuioClient->unlockCursorList();
}


void Manager::refresh(TuioTime ftime){
	//log("Refresh");
}


// tools
list<Object *> Manager::findCloseObjects(Node<> * node, int range){
	list<Object *> close;
	
	for (objects_t::iterator it = objects.begin(); it != objects.end(); ++it){
		Object * obj = it->second;
		if(node != (Node<>*)obj && node->distanceTo((Node<>*)obj) <= range){
			close.push_back(obj);
		}
	}
	
	return close;
}

list<Cursor *> Manager::findCloseCursors(Node<> * node, int range){
	list<Cursor *> close;
	
	for (cursors_t::iterator it = cursors.begin(); it != cursors.end(); ++it){
		Cursor * cur = it->second;
		if(node != (Node<>*)(cur) && node->distanceTo((Node<>*)cur) <= range){
			close.push_back(cur);
		}
	}
		
	return close;
}

void Manager::addConnection(Object * from, Object * to){
	connections.push_back(new DirectedConnection(from, to));
}

void Manager::removeConnection(Object * from, Object * to){
	for (list<DirectedConnection*>::iterator it = connections.begin(); it != connections.end(); ++it){
		DirectedConnection * con = *it;
		if(con->check(from, to)){
			connections.remove(con);
			delete con;
			return;
		}
	}
}

list<DirectedConnection *> Manager::connectionsForObject(Object * obj){
	list<DirectedConnection *> found;
	
	for (list<DirectedConnection*>::iterator it = connections.begin(); it != connections.end(); ++it){
		DirectedConnection * con = *it;
		if(con->check(obj)){
			found.push_back(con);
		}
	}
	
	return found;
}


