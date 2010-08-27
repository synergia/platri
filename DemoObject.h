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
	void display();	
};

class DemoGfxObject : public Graphic<Object> {
public:
	DemoGfxObject(Object * obj):Graphic<Object>(obj){};
	
	void display();
};


#endif