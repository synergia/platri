/*
 *  DemoApp.cpp
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-24.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <GLUT/glut.h>
#include "DemoApp.h"
#include "DemoObject.h"
#include "TuioObject.h"
#include "Fader.h"

#include "helpers.h"

using namespace helpers;

#define DEG2RAD(x) (x * M_PI / 180)
#define RAD2DEG(x) (x * 180 / M_PI

//  1 x 360
//  2 x 180
//  3 x 120
//  4 x 90
//  5 x 74
//  6 x 60
//  8 x 45
//  9 x 40
// 10 x 36
// 12 x 30
// 15 x 24
// 18 x 20


void DemoApp::display(){
	clear("#2c4776");

	//translate(100, 100);
	pushMatrix();
	
//	enableTextures();
//	selectTexture(0);
//	color("#fff9");
//	
//	glBegin( GL_QUADS );
//	glTexCoord2d(0.0, 0.0); glVertex2d(0.0,   0.0);
//	glTexCoord2d(1.0, 0.0); glVertex2d(200.0, 0.0);
//	glTexCoord2d(1.0, 1.0); glVertex2d(200.0, 200.0);
//	glTexCoord2d(0.0, 1.0); glVertex2d(0.0,   200.0);
//	glEnd();
//	
//	disableTextures();
	
	
	
	
	
//	int ri = 80;
//	int ro = 100;
//	
//	int field_angle = 20;
//	int space_angle = 10;
//	int step_angle = 5;
//	
//	// begin
//	
//	int size = field_angle + space_angle;
//	int count = 360 / size;
//	
//	float field_angle_radians = DEG2RAD(field_angle);
//	float space_angle_radians = DEG2RAD(space_angle);
//	float step_angle_radians = DEG2RAD(step_angle);
//	
//	
//	
//	
//	
//	float angle = 0.0f;
//	for(int j=0; j<count; ++j){
//		glBegin(GL_QUADS);
//		for(float i=0.0f; i<=field_angle_radians; i+=step_angle_radians){
//			vertex(ri*cos(angle+i), ri*sin(angle+i));
//			vertex(ro*cos(angle+i), ro*sin(angle+i));
//			vertex(ro*cos(angle+i+step_angle_radians), ro*sin(angle+i+step_angle_radians));
//			vertex(ri*cos(angle+i+step_angle_radians), ri*sin(angle+i+step_angle_radians));
//		}
//		glEnd();
//		
//		angle += field_angle_radians;		
//		angle += space_angle_radians;
//	}
//	
	
		
	
	popMatrix();
}

Object * DemoApp::createObject(TuioObject * tobj){
	switch(SYMBOL(tobj)){
		case 0:
			return new Fader(tobj);
			break;
			
		default:
			return new DemoObject(tobj);
			break;
	}
}
