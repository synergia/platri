/*
 *  TestApp.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-24.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _DEMO_APP_H_
#define _DEMO_APP_H_

#include "Application.h"

class DemoApp : public Application {
public:
	Object * createObject(TuioObject * tobj);
	
	void start();
	void display();
	
};

class Base : public Object {
public:
	Base(TuioObject *tobj);
	void onEvent(Event event);
};

class Child : public Object {
public:
	Child(TuioObject *tobj);
	void onEvent(Event event);
};


class DemoGraphics : public Graphic {
public:
	DemoGraphics(Object * parent, int m):Graphic(parent),angle(0),multiply(m){};
	
	void display();
	
protected:
	int angle;
	int multiply;
};

#endif