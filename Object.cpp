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
	Node<TuioObject>::display();
	displayGraphics();
}

void Object::displayGraphics(){
	for (list<Graphic *>::iterator it = graphics.begin(); it != graphics.end(); ++it){
		(*it)->display();
	}
}

void Object::move(){
	call(E_MOVE);
	updateCloseObjects(true);
}

void Object::updateCloseObjects(bool recursive){
	list<Object *> new_close = findCloseObjects(CLOSE_OBJECT_DISTANCE);
	
	for(list<Object *>::iterator nit = new_close.begin(); nit != new_close.end(); ++nit){
		bool include = false;
		for(list<Object *>::iterator oit = old_close.begin(); oit != old_close.end(); ++oit){
			if(*nit == *oit){
				include = true;
				break;
			}
		}
		
		if(include){
			old_close.remove(*nit);
		} else {
			if (recursive) {
				call(E_NEW_CLOSE_OBJECT, (Node<> *)*nit);
				(*nit)->call(E_NEW_CLOSE_OBJECT, (Node<> *)this);
				(*nit)->updateCloseObjects(false);
			}

		}
	}
	
	if(recursive){
		for(list<Object *>::iterator oit = old_close.begin(); oit != old_close.end(); ++oit){
			call(E_REMOVE_CLOSE_OBJECT, (Node<> *)*oit);
			(*oit)->call(E_REMOVE_CLOSE_OBJECT, (Node<> *)this);
			(*oit)->updateCloseObjects(false);
		}
	}
	
	old_close = new_close;
}

// proxy methods
list<Object *> Object::findCloseObjects(int range){
	return View::manager->findCloseObjects((Node<>*)this, range);
}

list<Cursor *> Object::findCloseCursors(int range){
	return View::manager->findCloseCursors((Node<>*)this, range);
}

void Object::addConnection(Object * from, Object * to){
	View::manager->addConnection(from, to);
}

void Object::removeConnection(Object * from, Object * to){
	View::manager->removeConnection(from, to);
}

list<DirectedConnection *> Object::connections(){
	return View::manager->connectionsForObject(this);
}
