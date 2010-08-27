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

DemoObject::DemoObject(TuioObject *tobj):Object(tobj){
	graphics.push_back(new DemoGfxObject(this));
	graphics.push_back(new DemoGfxObject(this));
}

void DemoObject::display(){
	color("#f90");
	pushMatrix();
	translate(x(), y());
	rotate(angle());
	square(50);
	popMatrix();
	
	displayGraphics();
}

void DemoGfxObject::display(){
	color("#00f");
	pushMatrix();
	translate(parent->x(), parent->y());
	rotate(parent->angle());
	square(20);
	popMatrix();
}