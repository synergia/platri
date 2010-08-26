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

void DemoApp::display(){
	//gluDisk(gluNewQuadric(), 0, 50, 24, 10);
	//gluDisk(gluNewQuadric(), 0, 50, 24, 10);
}

Object * DemoApp::createObject(TuioObject * tobj){
	return new DemoObject(tobj);
}
