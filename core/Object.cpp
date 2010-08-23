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

Object::Object(TuioObject *tobj):tobj(tobj){
	
}

int Object::x(){ return tobj->getX() * View::width; }
int Object::y(){ return (1.0-tobj->getY()) * View::height; }

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