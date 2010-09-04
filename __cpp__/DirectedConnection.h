/*
 *  Connection.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-31.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _DIRECTED_CONNECTION_H_
#define _DIRECTED_CONNECTION_H_

class DirectedConnection {
public:
	DirectedConnection(Object * from, Object * to);
	
	virtual void display();
	
	bool check(Object * first, Object * second);
	bool check(Object * first);
	
//protected:
	Object * from;
	Object * to;
	
	float dist;
};

#endif
