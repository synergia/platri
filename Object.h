/*
 *  Object.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _OBJECT_H_
#define _OBJECT_H_

#include "TuioObject.h"
#include "Node.h"
#include "Graphic.h"
#include "Cursor.h"
#include "DirectedConnection.h"

using namespace TUIO;
using namespace std;

class Object : public Node<TuioObject> {
public:
	Object(TuioObject *tobj):Node<TuioObject>(tobj){};
	virtual ~Object();
	
	// properties accessors
	int symbolID();
	int angle();
	float rotationSpeed();
		
	virtual void display();
	void displayGraphics();
	
	void move();
	
	// proxy
	list<Object *> findCloseObjects(int range, const std::type_info &type);
	list<Object *> findCloseObjects(int range);
	list<Cursor *> findCloseCursors(int range);
	
	void addConnection(Object * from, Object * to);
	void removeConnection(Object * from, Object * to);
	
	void updateCloseObjects(bool recursive);

protected:
	list<Graphic *> graphics;
	list<Object *> old_close;
};


#endif