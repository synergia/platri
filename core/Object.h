/*
 *  Object.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _OBJECT_H_
#define _OBJECT_H_

#include "TuioObject.h"

using namespace TUIO;

class Object {
public:
	Object(TuioObject *tobj);
	
	TuioObject *tobj;
};


#endif