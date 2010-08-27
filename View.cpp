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
#include "helpers.h"

int View::frame;
int View::_time;
int View::timebase = 0;
char View::s[30];

Manager * View::manager;
int View::windowWidth;
int View::windowHeight;

void View::init(){
	init(1280, 960, false);
}

void View::init(int width, int height, bool fullscreen){
	int argc = 0;
	char **argv;
	init(width, height, fullscreen, &argc, argv);
}

void View::init(int width, int height, bool fullscreen, int * argc, char ** argv){
	windowWidth = width;
	windowHeight = height;
	
	glutInit(argc, argv);
		
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
	
	if(fullscreen){
		char buffer[20];
		sprintf(buffer, "%dx%d:16@60", width, height);
		glutGameModeString("1280x960:16@60");
		glutEnterGameMode();
	} else {
		glutInitWindowSize(width, height);
		// glutInitWindowPosition(800, 200);
		glutCreateWindow("platri");
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
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glClearColor(1.0, 1.0, 1.0, 1.0);

	glPushMatrix();
	manager->display();
	glPopMatrix();
	
	displayFPS();

	glutSwapBuffers();
}

void View::idle(){
	glutPostRedisplay();
}

void View::reshape(int width, int height){
	glViewport(0, 0, width, height);
	    
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0, width, height, 0); // Use top-left as (0,0) and bottom-right as (width, height)
	glMatrixMode(GL_MODELVIEW);
}

void View::start(Application * app){
	manager = new Manager();
	manager->setApp(app);
	
	glutMainLoop();
}

void View::displayFPS(){
	frame++;
	_time = glutGet(GLUT_ELAPSED_TIME);
	
	if (_time - timebase > 1000) {
		sprintf(s, "FPS:%4.2f", frame * 1000.0 / (_time-timebase));
		timebase = _time;		
		frame = 0;
	}
	helpers::color("#fff");
	helpers::text(300, 30, s);
}