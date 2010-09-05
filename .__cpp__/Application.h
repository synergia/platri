/*
 *  Application.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _APPLICATION_H_
#define _APPLICATION_H_

#include "Object.h"
#include "Cursor.h"

#define SYMBOL(tobj) (tobj->getSymbolID())

class Application {
public:
	// Create correct object based on TuioObject
	virtual Object* createObject(TuioObject *tobj);
	
	// Create correct cursor based on TuioCursor
	virtual Cursor* createCursor(TuioCursor *tcur);
	
	// Display mostly static GUI
	virtual void display();
	
	// Hook for starting application
	virtual void start();
	
};


#endif