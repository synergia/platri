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
}

void DemoObject::display(){
	// do some display
	displayGraphics();
}

DemoGfxObject::DemoGfxObject(Object * obj):Graphic<Object>(obj){
	printf(">>> DemoGfxObject()\n");
	animation = new DemoAnimation(&angle);
};

DemoGfxObject::~DemoGfxObject(){
	delete animation;
};

void DemoGfxObject::display(){
	pushMatrix();
	translate(parent->x(), parent->y());
	rotate(parent->angle());
	
	enableTextures();
	selectTexture(0);
	
	for(int i = 0; i < 12; ++i){
		rotate(30);
		glBegin(GL_QUADS);
		tex(0.0, 1.0); vertex(-20, 100);
		tex(1.0, 1.0); vertex( 20, 100);
		tex(1.0, 0.0); vertex( 20,  80);
		tex(0.0, 0.0); vertex(-20,  80);
		glEnd();
	}
	
	disableTextures();
	
	popMatrix();
}