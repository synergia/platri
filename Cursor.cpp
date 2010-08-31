/*
 *  Cursor.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "Cursor.h"
#include "View.h"
#include <GLUT/GLUT.h>

Cursor::Cursor(TuioCursor * tcur):Node<TuioCursor>(tcur){
	
}

void Cursor::display(){
	glPushMatrix();
	glColor3f(1.0, 0.0, 0.0);
	glTranslated(x(), y(), 0);
	GLUquadric * quad = gluNewQuadric();
	gluDisk(quad, 0, 20, 24, 10);
	gluDeleteQuadric(quad);
	glPopMatrix();
}
