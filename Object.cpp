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
#include "helpers.h"

using namespace helpers;

Object::~Object(){
	for (list<Graphic *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		Graphic * gfx = *it;
		delete gfx;
	}
}

// properties accessors

int Object::symbolID() { 
	return source->getSymbolID();
}

int Object::angle(){
	return source->getAngleDegrees();	
}

float Object::rotationSpeed(){
	return source->getRotationSpeed();
}

void Object::display(){
	displayGraphics();
}

void Object::displayGraphics(){
	for (list<Graphic *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		(*it)->display();
	}
}

list<Object *> Object::findCloseObjects(int range, const std::type_info &type){
	return View::manager->findCloseObjects((Node<>*)this, range, type);
}
