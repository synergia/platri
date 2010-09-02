/*
 *  DirectedConnection.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-31.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "Object.h"
#include "helpers.h"

using namespace helpers;

DirectedConnection::DirectedConnection(Object * from, Object * to):from(from), to(to), dist(0.0){
};

void DirectedConnection::display(){
	dist += 0.03;
	if(dist > 1.0) dist = 0.0;
	
	int length = from->distanceTo(to);
	int angle = from->angleTo(to);
		
	pushMatrix();
	translate(from->x(), from->y());
	rotate(angle);
	
	pushMatrix();
	selectTexture(19);
	color("#fff");
	translate(length/2, 0);
	texRect(length-50, 40);
	popMatrix();
		 
	selectTexture(18);
	color("#fff");
	
	translate((length-100)*dist + 50, 0);
	texRect(20, 10);
	
	popMatrix();
};

bool DirectedConnection::check(Object * first, Object * second){
	return (first == from && second == to) || (first == to && second == from);
}

bool DirectedConnection::check(Object * first){
	return (first == from || first == to);
}