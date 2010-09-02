/*
 *  View.h
 *  core
 *
 *  Created by Tymon Tobolski on 2010-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

// @see http://dewitters.koonsolo.com/gameloop.html

#ifndef _VIEW_H_
#define _VIEW_H_

#include "Manager.h"


/**
 * @class View
 * 
 * Main singleton object. Controlls OpenGL events and game loop.
 * 
**/
class View {
public:
	/**
	 * First called methods
	 * @see init
	**/
	static void init();
	
	/**
	 * First called method. Initializes OpenGL and everything else
	**/
	static void init(int * argc, char ** argv);
	
	/**
	 * OpenGL keyboard callback
	**/
	static void keyboard(unsigned char key, int x, int y);
	
	/**
	 * OpenGL display callback
	**/
	static void display();
	
	/**
	 * OpenGL idle callback
	**/
	static void idle();
	
	/**
	 * OpenGL animate callback
	**/
	static void animate(int value);
	
	/**
	 * OpenGL reshape callback
	**/
	static void reshape(int width, int height);	
	
	/**
	 * Starts given application
	 * 
	 * @param app - Application
	**/
	static void start(Application * app);
	static void displayFPS();
	
	static Manager * manager;
	
	static int frame;
	static int _time;
	static int timebase;
	static char s[30];
};

#endif
