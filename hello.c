#include "ruby.h"

static VALUE rb_mHello;
static VALUE rb_cWorld;

struct foo {
	int x;
};

static VALUE print_string(VALUE self, VALUE string){
	Check_Type(string, T_STRING);
	printf("%s", RSTRING(string)->ptr);
	e
	struct foo * p = NULL;
	printf("%d", *p); //bad access
	
	return Qnil;
}

void Init_hello(){
	rb_mHello = rb_define_module("Hello");
	rb_cWorld = rb_define_class_under(rb_mHello, "World", rb_cObject);
	rb_define_method(rb_cWorld, "print_string", print_string, 1);
}
