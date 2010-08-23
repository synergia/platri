#include <iostream>
#include "Manager.h"
#include "Application.h"
#include "View.h"

int main (int argc, char ** argv) {
	View::init(&argc, argv);
	View::start();
    return 0;
}
