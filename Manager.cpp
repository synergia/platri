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
	objects.push_back(app()->createObject(tobj));
}

void Manager::updateTuioObject(TuioObject *tobj){
	// update is done automatically via TuioObject pointers in Object instances
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		(*it)->onUpdate();
	}
}

void Manager::removeTuioObject(TuioObject *tobj){
	// see http://gist.github.com/545615
	for (list<Object*>::iterator it = objects.begin(); it != objects.end(); ++it){
		Object * obj = *it;
		if(obj->checkSource(tobj)){
			objects.remove(obj);
			delete obj;
			return;
		}
	}
}


void Manager::addTuioCursor(TuioCursor *tcur){
	cursors.push_back(app()->createCursor(tcur));
}

void Manager::updateTuioCursor(TuioCursor *tcur){
	// update is done automatically via TuioCursor pointers in Cursor instances
	for (list<Cursor*>::iterator it = cursors.begin(); it != cursors.end(); ++it){
		(*it)->onUpdate();
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


