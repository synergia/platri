/*
 *  DemoApp.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-24.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <GLUT/glut.h>
#include "DemoApp.h"
#include "DemoObject.h"
#include "TuioObject.h"
#include "Fader.h"

#include "helpers.h"

using namespace helpers;


void DemoApp::start(){
	// load textures	
	
	loadTexture(0, "/Users/teamon/code/app/platri/Resources/bg.png");
	
	char s[100];
	for(int i=1; i<=16; ++i){
		sprintf(s, "/Users/teamon/code/app/platri/Resources/fader/%d.png", i);
		loadTexture(i, s);
	}
	
	loadTexture(17, "/Users/teamon/code/app/platri/Resources/circle200blur.png");
	
}

void DemoApp::display(){
	clear("#fff");
}

Object * DemoApp::createObject(TuioObject * tobj){
	switch(SYMBOL(tobj)){
		case 0:
			return new Fader(tobj);
			break;
			
		default:
			return new DemoObject(tobj);
			break;
	}
}
