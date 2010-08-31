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
using namespace std;

DemoObject::DemoObject(TuioObject *tobj):Object(tobj){
	graphics.push_back(new DemoGraphics(this, 1));
}

void DemoObject::onUpdate(){
	list<Object *> close = findCloseObjects(300, typeid(DemoObject));
	
}

void DemoGraphics::display(){
	color("#fff");
	angle += multiply;
	if(angle >= 360) angle = 0;
	
	pushMatrix();
	translate(parent->x(), parent->y());
	rotate(angle);
	
	selectTexture(17);
	texRect(200, 200);
	
	
	popMatrix();
}