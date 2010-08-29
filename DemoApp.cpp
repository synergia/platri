/*
 *  DemoApp.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-24.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <GLUT/glut.h>
#include "DemoApp.h"
#include "DemoObject.h"
#include "TuioObject.h"
#include "Fader.h"

#include "helpers.h"

using namespace helpers;

#define DEG2RAD(x) (x * M_PI / 180)
#define RAD2DEG(x) (x * 180 / M_PI)


void DemoApp::display(){
	clear("#fff");
}

Object * DemoApp::createObject(TuioObject * tobj){
	switch(SYMBOL(tobj)){
		case 0:
			return new Fader(tobj);
			break;
			
		default:
			return new DemoObject(tobj);
			break;
	}
}
