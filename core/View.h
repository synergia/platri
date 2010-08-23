/*
 *  View.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

class View {
public:
	static void init(int * argc, char ** argv);
	static void keyboard(unsigned char key, int x, int y);
	static void display();
	static void idle();
	static void reshape(int width, int height);	
	static void start();
};
