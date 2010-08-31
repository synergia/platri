/*
 *  Graphic.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-26.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _GRAPHIC_H_
#define _GRAPHIC_H_

class Object;

class Graphic {
public:
	Graphic(Object * parent):parent(parent){};
	virtual ~Graphic(){};
	virtual void display()=0;
	
protected:
	Object * parent;
};

#endif
