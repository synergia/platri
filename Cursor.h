/*
 *  Cursor.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _CURSOR_H_
#define _CURSOR_H_

#include "TuioCursor.h"
#include "Node.h"

using namespace TUIO;

class Cursor : public Node<TuioCursor> {
public:
	Cursor(TuioCursor *tcur):Node<TuioCursor>(tcur){};
	void display();
};

#endif