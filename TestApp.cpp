/*
 *  TestApp.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-24.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "TestApp.h"
#include <GLUT/glut.h>

void TestApp::display(){
	gluDisk(gluNewQuadric(), 0, 50, 24, 10);
	gluDisk(gluNewQuadric(), 0, 50, 24, 10);
}
