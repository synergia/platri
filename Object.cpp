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

Object::Object(TuioObject *tobj):tobj(tobj){
	
}

// properties accessors

int Object::symbolID() { 
	return tobj->getSymbolID();
};

int Object::x(){ 
	return tobj->getX() * View::windowWidth; 
}

int Object::y(){ 
	return tobj->getY() * View::windowHeight;
}

int Object::angle(){
	return tobj->getAngleDegrees();	
}


void Object::display(){
	glPushMatrix();
	glColor3f(1.0, 0.0, 0.0);
	glTranslated(x(), y(), 0);
	glBegin(GL_QUADS);
		glVertex2d(-50, -50);
		glVertex2d(-50,  50);
		glVertex2d( 50,  50);
		glVertex2d( 50, -50);
	glEnd();
	glPopMatrix();
}

void Object::displayGraphics(){
	for (list<Graphic<Object> *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		(*it)->display();
	}
}

bool Object::checkTuioObject(TuioObject * obj){
	return (tobj == obj);
}