/*
 *  OpenGL.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-24.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <GLUT/glut.h>
#include "OpenGL.h"

void rect(int width, int height){
	int w2 = width/2;
	int h2 = height/2;
	
	glColor3f(1.0, 0.0, 0.0);
	glBegin(GL_QUADS);
	glVertex2i(-w2,  h2);
	glVertex2i( w2,  h2);
	glVertex2i( w2, -h2);
	glVertex2i(-w2, -h2);
	glEnd();
}