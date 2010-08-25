#include <iostream>
//#include "View.h"
//#include "TestApp.h"

//int main (int argc, char ** argv) {
//	View::init();
//	View::start(new TestApp());
//    return 0;
//}

#include <Ruby/ruby.h>

int main(int argc, char *argv[])
{
	ruby_init();
	ruby_init_loadpath();
	rb_load_file("../../../main.rb");
	//ruby_exec();
	ruby_run();
	ruby_finalize();
	return 0;
}

