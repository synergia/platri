/*
 *  DemoObject.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-26.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _DEMO_OBJECT_
#define _DEMO_OBJECT_H_

#include "Object.h"

class DemoObject : public Object {
public:
	DemoObject(TuioObject *tobj);
};

class DemoGfxObject : public Graphic<Object> {
public:
	DemoGfxObject(Object * obj, int m):Graphic<Object>(obj),angle(0),multiply(m){};
	
	void display();
	
protected:
	int angle;
	int multiply;
};


#endif
