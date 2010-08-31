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

Object::Object(TuioObject *tobj):tobj(tobj){
	
}

Object::~Object(){
	for (list<Graphic<Object> *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		Graphic<Object> * gfx = *it;
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

long Object::sid(){
	return tobj->getSessionID();
}


void Object::display(){
	displayGraphics();
	
	// display session id
	disableTextures();
	char ss[10];
	sprintf(ss, "%ld", tobj->getSessionID());
	color("#000");
	text(x(), y(), ss);
	enableTextures();
}

void Object::displayGraphics(){
	for (list<Graphic<Object> *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		(*it)->display();
	}
}

bool Object::checkTuioObject(TuioObject * obj){
	return (tobj == obj);
}

list<Object *> Object::findCloseObjects(int range, const std::type_info &type){
	return View::manager->findCloseObjects(this, range, type);
}

float Object::distanceTo(Object * that){
	return sqrt(pow(this->x() - that->x(), 2) + pow(this->y() - that->y(), 2));
}

float Object::angleTo(Object * that){
	int side = this->x() - that->x();
	int height = this->y() - that->y();
	float angle = (float)(asin(side/height) + M_PI_2);
	if(height < 0) angle = 2*M_PI - angle;
	
	return RAD2DEG(angle);
};
