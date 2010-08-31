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

#include "TuioListener.h"
#include "TuioClient.h"
#include "Application.h"

using namespace TUIO;
using namespace std;

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
	
	// tools
	list<Object *> findCloseObjects(Node<> * node, int range, const std::type_info &type);
	list<Object *> findCloseObjects(Node<> * node, int range);
	
	list<Cursor *> findCloseCursors(Node<> * node, int range);

	
protected:
	Application *_app;
	TuioClient *tuioClient;
	
	list<Object*> objects;
	list<Cursor*> cursors;
};

#endif