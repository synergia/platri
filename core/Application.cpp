/*
 *  Application.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "Application.h"

Object* Application::createObject(TuioObject *tobj){
	return new Object(tobj);
}

Cursor* Application::createCursor(TuioCursor *tcur){
	return new Cursor(tcur);
}

void Application::display(){
	
}