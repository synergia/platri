/*
 *  midi_console.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-09-03.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include "midi.h"
#include <stdio.h>
#include <ncurses.h>

int main(int argc, const char ** argv){
	if(argc < 2){
		printf("Usage: ./midiconsole ADDRESS\n");
		return(1);
	} 
	
	midi::init(argv[1], 4444);
	
	char note;
	initscr();
	cbreak();
	while(true){
		note = getch();
		if(note != -1) midi::note_on(note-'a' + 30);
	}
	
	return 0;
}