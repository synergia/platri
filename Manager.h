/*
 *  Manager.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _MANAGER_H_
#define _MANAGER_H_

#include <list>
#include <map>

#include "TuioListener.h"
#include "TuioClient.h"
#include "Application.h"
#include "DirectedConnection.h"

using namespace TUIO;
using namespace std;

typedef map<TuioObject *, Object *> objects_t;
typedef map<TuioCursor *, Cursor *> cursors_t;

class Manager : public TuioListener {
public:
	Manager();
	~Manager(){
		tuioClient->disconnect();
		delete tuioClient;
	}
	
	void display();
	
	void setApp(Application *app);
	Application* app();
	
	// TUIO callbacks
	void addTuioObject(TuioObject *tobj);
	void updateTuioObject(TuioObject *tobj);
	void removeTuioObject(TuioObject *tobj);
	
	void addTuioCursor(TuioCursor *tcur);
	void updateTuioCursor(TuioCursor *tcur);
	void removeTuioCursor(TuioCursor *tcur);
	
	void refresh(TuioTime frameTime);
	
	list<Object *> findCloseObjects(Node<> * node, int range);
	list<Cursor *> findCloseCursors(Node<> * node, int range);
	
	
	void addConnection(Object * from, Object * to);
	void removeConnection(Object * from, Object * to);
	list<DirectedConnection *> connectionsForObject(Object * obj);


	
protected:
	Application *_app;
	TuioClient *tuioClient;
	
	objects_t objects;
	cursors_t cursors;
	
	list<DirectedConnection *> connections;
};

#endif