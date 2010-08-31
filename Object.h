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
#include "Graphic.h"

#define DEG2RAD(x) (x * M_PI / 180)
#define RAD2DEG(x) (x * 180 / M_PI)

using namespace TUIO;
using namespace std;

class Object {
public:
	Object(TuioObject *tobj);
	virtual ~Object();
	
	// properties accessors
	int symbolID();
	int x();
	int y();
	int angle();
	long sid(); // sessionID
	
	// calbacks
	virtual void onUpdate(){};
	
	virtual void display();
	void displayGraphics();
	
	// tools
	bool checkTuioObject(TuioObject * obj);
	list<Object *> findCloseObjects(int range, const std::type_info &type);
	list<Object *> findCloseObjects(int range);
	float distanceTo(Object * that);
	float angleTo(Object * that);
	
protected:	
	TuioObject * tobj;
	std::list<Graphic<Object> *> graphics;
};


#endif