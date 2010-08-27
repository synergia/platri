/*
 *  Object.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <stdio.h>
#include <GLUT/glut.h>
#include "Object.h"
#include "View.h"
#include "TuioObject.h"

Object::Object(TuioObject *tobj):tobj(tobj){
	
}

Object::~Object(){
	for (list<Graphic<Object> *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		Graphic<Object> * gfx = *it;
		//graphics.remove(gfx);
		delete gfx;
	}
}

// properties accessors

int Object::symbolID() { 
	return tobj->getSymbolID();
};

int Object::x(){ 
	return tobj->getX() * View::windowWidth; 
}

int Object::y(){ 
	return tobj->getY() * View::windowHeight;
}

int Object::angle(){
	return tobj->getAngleDegrees();	
}


void Object::display(){
	displayGraphics();
}

void Object::displayGraphics(){
	for (list<Graphic<Object> *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		(*it)->display();
	}
}

bool Object::checkTuioObject(TuioObject * obj){
	return (tobj == obj);
}