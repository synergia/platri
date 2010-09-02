//
//  main.m
//  platri
//
//  Created by Tymon Tobolski on 10-09-02.
//  Copyright 2010 Politechnika Wrocławska. All rights reserved.
//

#include "View.h"
#include "DemoApp.h"

int main(int argc, char *argv[])
{
	View::init();
	View::start(new DemoApp());
	return 0;
}
