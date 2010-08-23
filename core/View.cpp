/*
 *  View.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <stdlib.h>
#include <GLUT/glut.h>
#include "View.h"
#include "Application.h"

Manager * View::manager;
int View::width;
int View::height;

void View::init(int * argc, char ** argv){
	glutInit(argc, argv);
	
	// TODO: Take these from argv
	bool fullscreen = false;
	width = 1280; //400;
	height = 960; //300;
	const char * name = "platri";
	
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
	
	if(fullscreen){
		// TODO: Add fullscreen init
		glutGameModeString("1280x960:16@60");
		glutEnterGameMode();
	} else {
		glutInitWindowSize(width, height);
		//glutInitWindowPosition(800, 200);
		glutCreateWindow(name);
	}

	glutDisplayFunc(display);
	glutIdleFunc(idle);
	glutReshapeFunc(reshape);
	glutKeyboardFunc(keyboard);
	
	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	
	
	manager = new Manager();
	manager->setApp(new Application());
}

void View::keyboard(unsigned char key, int x, int y){
	switch(key){
		case 27: // Escape
			exit(0);
			break;
	}
}

void View::display(){
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(1.0, 1.0, 1.0, 1.0);

	glPushMatrix();
	manager->displayObjectsAndCursors();
	glPopMatrix();

	glutSwapBuffers();
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