#include <iostream>
#include "View.h"

int main (int argc, char ** argv) {
	View::init(&argc, argv);
	View::start();
    return 0;
}
