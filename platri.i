// see http://www.swig.org/Doc2.0/SWIGDocumentation.html for reference

%module(directors="1") platri
%{
	#include "core/View.h"
%}

%feature("director");
class View {
public:
	static void init(int width, int height, bool fullscreen);
	static void init();
	static void start(Application * app);
};

class Application {
public:
	virtual Object* createObject(TuioObject *tobj);
	virtual Cursor* createCursor(TuioCursor *tcur);
	virtual void display();
	virtual void start();
};


class Object {
public:
	Object(TuioObject *tobj);
	
	int x();
	int y();
	int symbolID();
	
	virtual void display();
};


%nodefaultctor TuioObject;
class TuioObject {
public:
	int getSymbolID();
};
