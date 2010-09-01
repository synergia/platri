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
	
void Fader::onEvent(Event event){
	switch(event.type){
		case E_MOVE:
			float rspeed = rotationSpeed();
			
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
			
			_prev_rotation_speed = rotationSpeed();
			break;
	}

}

void Fader::display(){
	Object::display();
	pushMatrix();
	translate(x(), y());
	color("#fff");
	
	int n = floor(value/22.5);
	float a = ((value - (n * 22.5)) * 0.9 / 22.5) + 0.1;
	
	for(int i = 1; i<=16; ++i){
		selectTexture(i);
		
		if(i == n+1) alpha(a);
		else if(i > n+1) alpha(0.1);
		
		texSquare(BASE_SIZE);
	}
	alpha(1.0);
	popMatrix();
};

