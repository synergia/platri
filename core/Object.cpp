/*
 *  Object.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <stdio.h>
#include <GLUT/glut.h>
#include "Object.h"
#include "View.h"
#include "TuioObject.h"

Object::Object(TuioObject *tobj):_tobj(tobj){
	
}

TuioObject * Object::tobj(){
//	if(_tobj == NULL){
//		throw 10;
//	} else {
//		return _tobj;
//	}
	
	//_tobj = NULL;
	return _tobj;
}

int Object::x(){ 
	return tobj()->getX() * View::windowWidth; 
}

int Object::y(){ 
	return (1.0-tobj()->getY()) * View::windowHeight;
}

int Object::symbolID() { 
	return tobj()->getSymbolID();
};

void Object::display(){
	glPushMatrix();
	glColor3f(1.0, 0.0, 0.0);
	glTranslated(this->x(), this->y(), 0);
	glBegin(GL_QUADS);
		glVertex2d(-50, -50);
		glVertex2d(-50,  50);
		glVertex2d( 50,  50);
		glVertex2d( 50, -50);
	glEnd();
	glPopMatrix();
}