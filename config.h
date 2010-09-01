/*
 *  config.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-31.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _CONFIG_H_
#define _CONFIG_H_

// Basic configuration
#define FPS 50
#define DEBUG true
#define FULLSCREEN false
#define SCREEN_WIDTH  800
#define SCREEN_HEIGHT 600
#define CLOSE_OBJECT_DISTANCE 300
#define BASE_SIZE 100



// events (do not touch)
#define E_MOVE					0x01
#define E_NEW_CLOSE_OBJECT		0x02
#define E_REMOVE_CLOSE_OBJECT	0x03

#if DEBUG

#include <iostream>

template<typename T> void pp(const T &t) {
	for(typename T::const_iterator i = t.begin(); i != t.end(); i++) std::cout << *i << std::endl;
}

#endif

#endif