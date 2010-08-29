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
#include <GLUT/glut.h>

using namespace helpers;

#define E 14

#define INCRESING (_angle + E > 360)
#define DECRESING (_angle - E < 0)

Fader::Fader(TuioObject * tobj):Object(tobj), value(0), _angle(0.0), _angle_diff(0.0), _prev_rotation_speed(0.0){};
	
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
}
	
void Fader::display(){
	pushMatrix();
	translate(x(), y());
	selectTexture(0);
	
	int i = 0;
	for(; i < 12; ++i){
		rotate(30);
		glBegin(GL_QUADS);
		tex(0.0, 1.0); vertex(-20, 100);
		tex(1.0, 1.0); vertex( 20, 100);
		tex(1.0, 0.0); vertex( 20,  80);
		tex(0.0, 0.0); vertex(-20,  80);
		glEnd();
	}
	
	selectTexture(1);
	
	for(; i < 12; ++i){
		rotate(30);
		glBegin(GL_QUADS);
		tex(0.0, 1.0); vertex(-20, 100);
		tex(1.0, 1.0); vertex( 20, 100);
		tex(1.0, 0.0); vertex( 20,  80);
		tex(0.0, 0.0); vertex(-20,  80);
		glEnd();
	}
	
	popMatrix();
};