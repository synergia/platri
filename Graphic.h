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

template <class T> class Graphic {
public:
	Graphic(T * obj):parent(obj){};
	virtual ~Graphic(){};
	virtual void display()=0;
	
protected:
	T * parent;
};

#endif
