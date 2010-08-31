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
	DirectedConnection(Object * from, Object * to):from(from), to(to){};
	
	virtual void display(){
		printf("Connection#display\n");
	};
	
protected:
	Object * from;
	Object * to;
};

#endif
