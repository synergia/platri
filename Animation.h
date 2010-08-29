/*
 *  Animation.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-29.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#ifndef _ANIMATION_H_
#define _ANIMATION_H_

#include <stdio.h>
#include <pthread.h>

template <class T> void * animation_thread_callback(void * parent);

template <class T> class Animation {
public:	
	Animation(T * ptr):ptr(ptr){
		printf("Animation()\n");
		pthread_create(&thread, NULL, animation_thread_callback<T>, this);
	};
	
	~Animation(){
		printf("~Animation()\n");
		pthread_kill(thread, 0);
	}
	
	virtual void step(){
		printf("Application#step\n");
	};
	
protected:
	T * ptr;
	pthread_t thread;
};

template <class T> void * animation_thread_callback(void * parent){
	Animation<T> * anim = (Animation<T> * )parent;
	while(true){
		printf("animation_thread_callback()\n");
		if(anim != NULL)
			anim->step();
	}
	return NULL;
}


#endif
