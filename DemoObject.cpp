/*
 *  DemoObject.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-26.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "DemoObject.h"
#include "helpers.h"
#include <GLUT/glut.h>

using namespace helpers;

void DemoObject::display(){
	color("#f90");
	pushMatrix();
	translate(x(), y());
	rotate(angle());
	square(50);
	popMatrix();
}