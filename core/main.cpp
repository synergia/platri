#include <iostream>
#include "Manager.h"
#include "Application.h"

int main (int argc, char * const argv[]) {
	Application *app = new Application();
    Manager *m = new Manager();
	m->setApp(app);
	sleep(100); // hax
    return 0;
}
