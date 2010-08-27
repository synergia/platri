/*
 *  Fader.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-27.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "Fader.h"
#include "helpers.h"

using namespace helpers;

#define E 14

#define INCRESING (_angle + E > 360)
#define DECRESING (_angle - E < 0)

Fader::Fader(TuioObject * tobj):Object(tobj), value(0), _angle(0.0), _angle_diff(0.0), _prev_rotation_speed(0.0){
	graphics.push_back((Graphic<Object> *)new FaderGraphic(this));
};
	


void Fader::onUpdate(){
	
	float rspeed = tobj->getRotationSpeed();
	
	if(rspeed != 0){
		if(rspeed * _prev_rotation_speed < 0){ // change direction
			if(INCRESING || DECRESING) _angle_diff = 360 - angle();
		} else {
			if(rspeed > 0 && INCRESING){
				_angle = 360;
			} else if(rspeed < 0 && DECRESING){
				_angle = 0;
			} else {
				_angle = angle() + _angle_diff;
			}
			
			if (_angle > 360) {
				_angle = _angle % 360;
			}
			value = _angle;
		}
	}
	
	_prev_rotation_speed = tobj->getRotationSpeed();
	
	printf("_angle: %d\n", _angle);
}

	
void FaderGraphic::display(){
	printf("FaderGraphic#display\n");
	pushMatrix();
	translate(parent->x(), parent->y());
	color("#f00");
	arc(40, 60, 0, parent->value);
	popMatrix();
};