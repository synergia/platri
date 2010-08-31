#include <iostream>
#include "View.h"
#include "DemoApp.h"

int main (int argc, char * const argv[]) {
	View::init();
	View::start(new DemoApp());
    return 0;
}
