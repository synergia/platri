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


#endif