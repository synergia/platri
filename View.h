/*
 *  View.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _VIEW_H_
#define _VIEW_H_

#include "Manager.h"

class View {
public:
	static void init();
	static void init(int width, int height, bool fullscreen);
	static void init(int width, int height, bool fullscreen, int * argc, char ** argv);
	static void keyboard(unsigned char key, int x, int y);
	static void display();
	static void idle();
	static void reshape(int width, int height);	
	static void start(Application * app);
	
	static int windowWidth;
	static int windowHeight;
	static Manager * manager;
};

#endif