/*
 *  DemoObject.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-26.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "DemoObject.h"

#include <GLUT/glut.h>

void DemoObject::display(){
	glColor3f(1.0, 0.0, 0.0);
	
	glPushMatrix();
	glTranslated(x(), y(), 0);
	glBegin(GL_QUADS);
	glVertex2f(0, 0);
	glVertex2f(0, 50);
	glVertex2f(50, 50);
	glVertex2f(50, 0);
	glEnd();
	glPopMatrix();
}