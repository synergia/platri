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
	graphics.push_back(new DemoGfxObject(this, 1));
	graphics.push_back(new DemoGfxObject(this, 3));
}

void DemoGfxObject::display(){
	angle += multiply;
	if(angle >= 360) angle = 0;
	
	pushMatrix();
	translate(multiply*parent->x(), parent->y());
	rotate(angle);
	
	selectTexture(0);
	texRect(200, 200);
	
	
	popMatrix();
}