/*
 *  Fader.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-27.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "Object.h"

class Fader : public Object {
public:
	Fader(TuioObject * tobj);
	virtual void display();
	void onEvent(Event event);
	
protected:
	float _prev_rotation_speed;
	int _angle;
	int _angle_diff;
	int value;
};