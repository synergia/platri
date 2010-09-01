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
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		(*it)->display();
	}
	
	// display objects connections
	for (list<DirectedConnection*>::iterator it = connections.begin(); it != connections.end(); ++it){
		(*it)->display();
	}
	tuioClient->unlockObjectList();
	

	// display cursors
	tuioClient->lockCursorList(); // prevent TUIO thread disaster
	for (list<Cursor*>::iterator it = cursors.begin(); it != cursors.end(); ++it){
		(*it)->display();
	}
	tuioClient->unlockCursorList();
}



// TUIO callbacks

void Manager::addTuioObject(TuioObject *tobj){
	Object * obj = app()->createObject(tobj);
	obj->move();
	objects.push_back(obj);
}

void Manager::updateTuioObject(TuioObject *tobj){
	tuioClient->lockObjectList(); // prevent TUIO thread disaster

	// update is done automatically via TuioObject pointers in Object instances
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		if((*it)->checkSource(tobj)) {
			(*it)->move();
		}
	}
	
	
	
	for (list<DirectedConnection*>::iterator it = connections.begin(); it != connections.end(); ++it){
		DirectedConnection * con = *it;
		printf("(%ld, %ld), ", con->from->sid(), con->to->sid());
	}
	
	printf("\n");
	tuioClient->unlockObjectList();
}

void Manager::removeTuioObject(TuioObject *tobj){
	tuioClient->lockObjectList(); // prevent TUIO thread disaster
	
	for (list<Object*>::iterator it = objects.begin(); it != objects.end();){
		Object * obj = *it;
		if(obj->checkSource(tobj)){
			
			for (list<DirectedConnection*>::iterator cit = connections.begin(); cit != connections.end();){
				DirectedConnection * con = *cit;
				if(con->check(obj)){
					cit = connections.erase(cit);
					delete con;
				} else {
					cit++;
				}
			}
			
			
			it = objects.erase(it);
			delete obj;
		} else {
			it++;
		}

	}
	
	tuioClient->unlockObjectList();
}


void Manager::addTuioCursor(TuioCursor *tcur){
	cursors.push_back(app()->createCursor(tcur));
}

void Manager::updateTuioCursor(TuioCursor *tcur){
	// update is done automatically via TuioCursor pointers in Cursor instances
	for (list<Cursor*>::iterator it = cursors.begin(); it != cursors.end(); ++it){
		(*it)->call(E_MOVE);
	}
}

void Manager::removeTuioCursor(TuioCursor *tcur){
	for (list<Cursor*>::iterator it = cursors.begin(); it != cursors.end(); ++it){
		Cursor * cur = *it;
		if(cur->checkSource(tcur)){
			cursors.remove(cur);
			delete cur;
			return;
		}
	}
}


void Manager::refresh(TuioTime ftime){
	//log("Refresh");
}


// tools
list<Object *> Manager::findCloseObjects(Node<> * node, int range, const std::type_info &type){
	list<Object *> close;
	
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		if(node != (Node<>*)(*it) && typeid(**it) == type && node->distanceTo((Node<>*)*it) <= range){
			close.push_back(*it);
		}
	}
	
	return close;
}

list<Object *> Manager::findCloseObjects(Node<> * node, int range){
	// TODO: Implement this
	list<Object *> close;
	
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		if(node != (Node<>*)(*it) && node->distanceTo((Node<>*)*it) <= range){
			close.push_back(*it);
		}
	}
	
	return close;
}

list<Cursor *> Manager::findCloseCursors(Node<> * node, int range){
	// TODO: Implement this
	list<Cursor *> close;
	
	for (list<Cursor*>::iterator it = cursors.begin(); it != cursors.end(); ++it){
		if(node != (Node<>*)(*it) && node->distanceTo((Node<>*)*it) <= range){
			close.push_back(*it);
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


