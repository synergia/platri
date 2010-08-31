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

void DemoObject::onEvent(Event event){
	switch(event.type){
		case E_MOVE:
			printf("[EVENT] E_MOVE\n");
			break;
			
		case E_NEW_CLOSE_OBJECT:
			printf("[EVENT] E_NEW_CLOSE_OBJECT\n");
			break;
			
		case E_REMOVE_CLOSE_OBJECT:
			printf("[EVENT] E_REMOVE_CLOSE_OBJECT\n");
			break;
	}

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

