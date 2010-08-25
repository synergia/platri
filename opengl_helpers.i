%module opengl_helpers
%{
	#include "core/OpenGL.h"
	#include "/Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/OpenGL.framework/Headers/gl.h"
	#include "/Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/OpenGL.framework/Headers/glu.h"
	#include "/Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/GLUT.framework/Headers/glut.h"
	
	// ruby 1.9.x fix
	#define STR2CSTR(x) StringValuePtr(x)
%}

%include "/Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/OpenGL.framework/Headers/gl.h"
%include "/Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/OpenGL.framework/Headers/glu.h"
%include "/Developer/SDKs/MacOSX10.6.sdk/System/Library/Frameworks/GLUT.framework/Headers/glut.h"                  

void rect(int width, int height);
