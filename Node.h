/*
 *  Node.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-31.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _NODE_H_
#define _NODE_H_

#include "config.h"

#define DEG2RAD(x) (x * M_PI / 180)
#define RAD2DEG(x) (x * 180 / M_PI)

using namespace TUIO;
using namespace std;

template <class T = TuioContainer> class Node;

typedef unsigned int E_TYPE;

struct Event {
	E_TYPE type;
	Node <> * node;
};



#if DEBUG
#include "helpers.h"
using namespace helpers;
#endif

template <class T> class Node {
public:
	Node(T *source):source(source){};
	virtual ~Node(){};
	
	// properties accessors
	int x(){
		return source->getX() * SCREEN_WIDTH; 
	};
	
	int y(){
		return source->getY() * SCREEN_HEIGHT;
	};
	
	long sid(){
		return source->getSessionID();
	};
	
	// Events
	virtual void onEvent(Event event){};
	
	void call(E_TYPE type){
		call(type, NULL);
	};
	
	void call(E_TYPE type, Node <> * node){
		Event ev;
		ev.type = type;
		ev.node = node;
		onEvent(ev);
	};
	
	virtual void display(){
		
#if DEBUG
		// display session id
		disableTextures();
		char ss[10];
		sprintf(ss, "%ld", sid());
		color("#000");
		text(x(), y(), ss);
		enableTextures();
#endif
	};
	
	// tools
	bool checkSource(T * src){
		return (source == src);
	};
	
	float distanceTo(Node * that){
		return sqrt(pow(this->x() - that->x(), 2) + pow(this->y() - that->y(), 2));
	};
	
	float angleTo(Node * that){
		int side = this->x() - that->x();
		int height = this->y() - that->y();
		float angle = (float)(asin(side/height) + M_PI_2);
		if(height < 0) angle = 2*M_PI - angle;
		
		return RAD2DEG(angle);
	};
	
protected:	
	T * source;
};

#endif
