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

using namespace TUIO;

class Object {
public:
	Object(TuioObject *tobj);
	virtual ~Object();
	
	// properties accessors
	int symbolID();
	int x();
	int y();
	int angle();	
	
	// calbacks
	virtual void onUpdate(){};
	
	virtual void display();
	void displayGraphics();
	
	bool checkTuioObject(TuioObject * obj);
	
protected:	
	TuioObject * tobj;
	std::list<Graphic<Object> *> graphics;
};


#endif