#include <iostream>
#include "Manager.h"
#include "Application.h"
#include "View.h"

int main (int argc, char ** argv) {
	View::init(&argc, argv);
	View::start();
	
	
	
	
	//Application *app = new Application();
    //Manager *m = new Manager();
	//m->setApp(app);
	//sleep(100); // hax
    return 0;
}
