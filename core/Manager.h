/*
 *  Manager.h
 *  core
 *
 *  Created by Tymon Tobolski on 10-08-23.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "TuioListener.h"
#include "TuioClient.h"

using namespace TUIO;

class Manager : public TuioListener {
public:
	Manager();
	~Manager(){
		tuioClient->disconnect();
		delete tuioClient;
	}
	
	// TUIO callbacks
	void addTuioObject(TuioObject *tobj);
	void updateTuioObject(TuioObject *tobj);
	void removeTuioObject(TuioObject *tobj);
	
	void addTuioCursor(TuioCursor *tcur);
	void updateTuioCursor(TuioCursor *tcur);
	void removeTuioCursor(TuioCursor *tcur);
	
	void refresh(TuioTime frameTime);
	
	TuioClient *tuioClient;
};
