/*
 *  DirectedConnection.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-31.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "Object.h"
#include <GLUT/glut.h>

DirectedConnection::DirectedConnection(Object * from, Object * to):from(from), to(to), dist(0.0){
};

void DirectedConnection::display(){
	dist += 0.03;
	if(dist > 1.0) dist = 0.0;
	pushMatrix();
	
	int length = from->distanceTo(to);
	int angle = from->angleTo(to);
		
	translate(from->x(), from->y());
	rotate(angle);
	translate((length-100)*dist + 50, 0);
	
	selectTexture(0);
	color("#fff");
	
	texRect(20, 20);
	
	popMatrix();
	
};

bool DirectedConnection::check(Object * first, Object * second){
	return (first == from && second == to) || (first == to && second == from);
}

bool DirectedConnection::check(Object * first){
	return (first == from || first == to);
}