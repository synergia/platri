/*
 *  View.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <GLUT/glut.h>
#include "View.h"

#include <stdlib.h>

void View::init(int * argc, char ** argv){
	glutInit(argc, argv);
	
	// TODO: Take these from argv
	bool fullscreen = false;
	int width = 400;
	int height = 300;
	const char * name = "platri";
	
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
	
	if(fullscreen){
		// TODO: Add fullscreen init
	} else {
		glutInitWindowSize(width, height);
		glutCreateWindow(name);
	}

	glutDisplayFunc(display);
	glutIdleFunc(idle);
	glutReshapeFunc(reshape);
	glutKeyboardFunc(keyboard);
	
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
}

void View::keyboard(unsigned char key, int x, int y){
	switch(key){
		case 27: // Escape
			exit(0);
			break;
	}
}

void View::display(){
	
}

void View::idle(){
	glutPostRedisplay();
}

void View::reshape(int width, int height){
	glViewport(0, 0, width, height);
	    
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0, width, 0, height);
	glMatrixMode(GL_MODELVIEW);
}

void View::start(){
	glutMainLoop();
}