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
			
		case 1:
			return new Base(tobj);
			break;
		
		case 2:
		case 3:
			return new Child(tobj);
			break;
			
		default:
			return new Fader(tobj);
			break;
	}
}

void DemoGraphics::display(){
	color("#fff");
	angle += multiply;
	if(angle >= 360) angle = 0;
	
	pushMatrix();
	translate(parent->x(), parent->y());
	rotate(angle);
	
	selectTexture(17);
	texSquare(BASE_SIZE);
	
	
	popMatrix();
}


Base::Base(TuioObject *tobj):Object(tobj){
	graphics.push_back(new DemoGraphics(this, 1));
}

Child::Child(TuioObject *tobj):Object(tobj){
	graphics.push_back(new DemoGraphics(this, 10));
}

void Base::onEvent(Event event){
	switch(event.type){
		case E_MOVE:
			//printf("[EVENT | base] E_MOVE\n");
			break;
			
		case E_NEW_CLOSE_OBJECT:
			printf("[EVENT | base] E_NEW_CLOSE_OBJECT\n");
			
			addConnection(this, (Object *)event.node);
			
			break;
			
		case E_REMOVE_CLOSE_OBJECT:
			printf("[EVENT | base] E_REMOVE_CLOSE_OBJECT\n");
			
			removeConnection(this, (Object *)event.node);
			break;
	}
	
}

void Child::onEvent(Event event){
	switch(event.type){
		case E_MOVE:
			//printf("[EVENT | child] E_MOVE\n");
			break;
			
		case E_NEW_CLOSE_OBJECT:
			printf("[EVENT | child] E_NEW_CLOSE_OBJECT\n");
			break;
			
		case E_REMOVE_CLOSE_OBJECT:
			printf("[EVENT | child] E_REMOVE_CLOSE_OBJECT\n");
			break;
	}
	
}

