/*
 *  helpers.cpp
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-26.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */

#include <string.h>
#include <math.h>
#include <GLUT/glut.h>
#include "stb_image.h"
#include "helpers.h"

#define HEXCHAR2INT(x, y)  ((x >= 'a' ? x-87 : x-48) * 16 + (y >= 'a' ? y-87 : y-48))

float * _parse_color(const char * str);

GLuint textures[10];


void helpers::color(const char * name){
	float * res = _parse_color(name);
	glColor4f(res[0], res[1], res[2], res[3]);
	delete[] res;
}

void helpers::clear(const char * name){
	float * res = _parse_color(name);
	glClearColor(res[0], res[1], res[2], res[3]);
	delete[] res;
}

void helpers::pushMatrix(){
	glPushMatrix();
}

void helpers::popMatrix(){
	glPopMatrix();
}

void helpers::vertex(int x, int y){
	glVertex2f(x, y);
}

void helpers::translate(int x, int y){
	glTranslatef(x, y, 0);
}

void helpers::rotate(int angle){
	glRotatef(angle, 0.0, 0.0, 1.0);
}

void helpers::rect(int width, int height){
	int w2 = width/2;
	int h2 = height/2;
	glBegin(GL_QUADS);
	glVertex2f(-w2,  h2);
	glVertex2f( w2,  h2);
	glVertex2f( w2, -h2);
	glVertex2f(-w2, -h2);
	glEnd();
}

void helpers::texRect(int width, int height){
	int w2 = width/2;
	int h2 = height/2;
	glBegin(GL_QUADS);
	glTexCoord2d(0.0, 1.0); glVertex2f(-w2,  h2);
	glTexCoord2d(1.0, 1.0); glVertex2f( w2,  h2);
	glTexCoord2d(1.0, 0.0); glVertex2f( w2, -h2);
	glTexCoord2d(0.0, 0.0); glVertex2f(-w2, -h2);
	glEnd();
}

void helpers::square(int width){
	rect(width, width);
}

void helpers::circle(int r){
	gluDisk(gluNewQuadric(), 0, r, 24, 10);
}

void helpers::ellipse(int rx, int ry){
	glBegin(GL_POLYGON);
	for(float i=0; i<2*M_PI; i+=0.1){
		glVertex2f(rx*cos(i), ry*sin(i));
	}
	glEnd();
}

void helpers::ring(int ri, int ro){
	gluDisk(gluNewQuadric(), ri, ro, 24, 10);
}

void helpers::arc(int ri, int ro, int start, int angle){
	gluPartialDisk(gluNewQuadric(), ri, ro, 24, 10, start, angle);
}

void helpers::text(int x, int y, const char * str){
	glRasterPos2f(x, y);
	for(char * c = (char *)str; *c != '\0'; ++c){
		glutBitmapCharacter(GLUT_BITMAP_8_BY_13, *c);
	}
}

void helpers::enableTextures(){
	glEnable(GL_TEXTURE_2D);
}

void helpers::disableTextures(){
	glDisable(GL_TEXTURE_2D);
}

void helpers::loadTexture(int index, const char * filename){
	glGenTextures(1, &textures[index]);
	glBindTexture(GL_TEXTURE_2D, textures[index]);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
	int width, height, n;
	unsigned char * data = stbi_load(filename, &width, &height, &n, 0);
	
	glTexImage2D(GL_TEXTURE_2D, 0, 4, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
	delete data;
}

void helpers::selectTexture(int index){
	glBindTexture(GL_TEXTURE_2D, textures[index]);
}

float * _parse_color(const char * str){
    int size = strlen(str);
	float * ret = new float[4];
	
	switch(size){
		case 4: // #rgb
			ret[0] = HEXCHAR2INT(str[1], str[1]) / 255.0;
			ret[1] = HEXCHAR2INT(str[2], str[2]) / 255.0;
			ret[2] = HEXCHAR2INT(str[3], str[3]) / 255.0;
			ret[3] = 1.0;
			break;
			
		case 5: // #rgba
			ret[0] = HEXCHAR2INT(str[1], str[1]) / 255.0;
			ret[1] = HEXCHAR2INT(str[2], str[2]) / 255.0;
			ret[2] = HEXCHAR2INT(str[3], str[3]) / 255.0;
			ret[3] = HEXCHAR2INT(str[4], str[4]) / 255.0;
			break;
			
		case 7: // #rrggbb
			ret[0] = HEXCHAR2INT(str[1], str[2]) / 255.0;
			ret[1] = HEXCHAR2INT(str[3], str[4]) / 255.0;
			ret[2] = HEXCHAR2INT(str[5], str[6]) / 255.0;
			ret[3] = 1.0;
			break;
			
		case 9:	// #rrggbbaa
			ret[0] = HEXCHAR2INT(str[1], str[2]) / 255.0;
			ret[1] = HEXCHAR2INT(str[3], str[4]) / 255.0;
			ret[2] = HEXCHAR2INT(str[5], str[6]) / 255.0;
			ret[3] = HEXCHAR2INT(str[7], str[8]) / 255.0;
			break;
			
	}
    return ret;
}



