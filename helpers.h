/*
 *  helpers.h
 *  platri
 *
 *  Created by Tymon Tobolski on 10-08-26.
 *  Copyright 2010 Politechnika Wrocławska. All rights reserved.
 *
 */


namespace helpers {
	void color(const char * name);
	void clear(const char * name);
	void pushMatrix();
	void popMatrix();
	void vertex(int x, int y);
	void translate(int x, int y);
	void rotate(int angle);
	void rect(int width, int height);
	void square(int width);
	void circle(int r);
	void ellipse(int rx, int ry);
	void ring(int ri, int ro);	
	void arc(int ri, int ro, int start, int angle);
	void text(int x, int y, const char * str);
	
	// textures
	void enableTextures();
	void disableTextures();
	void loadTexture(int index, const char * filename);
	void selectTexture(int index);
}

