package synergia.platri

import javax.media.opengl.GL
import com.sun.opengl.util.GLUT
import com.sun.opengl.util.texture.{Texture, TextureIO}
import java.io.File
import scala.util.matching.Regex
import scala.collection.mutable.ListBuffer

trait Helpers {
	// color
	final val RGB 	= """#([a-f0-9])([a-f0-9])([a-f0-9])""".r
	final val RGBA 	= """#([a-f0-9])([a-f0-9])([a-f0-9])([a-f0-9])""".r
	final val RRGGBB 	= """#([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})""".r
	final val RRGGBBAA = """#([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})""".r
	
	private lazy val gl = View.gl
	private lazy val glu = View.glu
	private lazy val glut = View.glut
	
	protected def parseColor(name: String) = {
		def s(s: String) = {
			val x = Integer.parseInt(s, 16)
			(x*16 + x) / 255.0
		}
		def p(s: String) = Integer.parseInt(s, 16) / 255.0

		name match {
			case RGB(r,g,b) => List(r,g,b,"f").map(s)
			case RGBA(r,g,b,a) => List(r,g,b,a).map(s)
			case RRGGBB(r,g,b) => List(r,g,b,"ff").map(p)
			case RRGGBBAA(r,g,b,a) => List(r,g,b,a).map(p)
		}
	}
	
	
	def text(x: Int, y: Int, str: String) { text(x, y, str, true) }
	
	def text(x: Int, y: Int, str: String, center: Boolean) {
		val font = GLUT.BITMAP_8_BY_13
		
		if(center){
			val len = glut.glutBitmapLength(font, str)
			gl.glRasterPos2f(List(List(x-(len/2), len/2).max, Config.WIDTH-(len/2)).min, y)
		} else {
			gl.glRasterPos2f(x, y)
		}

		str.toArray.foreach(glut.glutBitmapCharacter(font, _))
	}
	
	def color(r: Double, g: Double, b: Double) = gl.glColor3f(r.toFloat, g.toFloat, b.toFloat)
	
	def color(name: String) = {
		val rgba = parseColor(name)
		gl.glColor4d(rgba(0), rgba(1), rgba(2), rgba(3))
	}
	
	def clear(name: String) = {
		val rgba = parseColor(name).map(_.toFloat)
		gl.glClearColor(rgba(0), rgba(1), rgba(2), rgba(3))
	}
	
	def alpha(value: Double) = gl.glColor4f(1f, 1f, 1f, value.toFloat)
	
	def translate(x: Int, y: Int) = gl.glTranslated(x, y, 0)
	
	def translateAnd(x: Int, y: Int)(func: => Unit) {
		matrix {
			translate(x, y)
			func
		}
	}
	
	def rotate(angle: Int) = gl.glRotated(angle, 0, 0, 1)
	
	def rotateAnd(angle: Int)(func: => Unit) {
		matrix {
			rotate(angle)
			func
		}
	}
	
	def matrix(func: => Unit) {
		gl.glPushMatrix
		func
		gl.glPopMatrix
	}
	
	def withoutTextures(func: => Unit) {
		disableTextures
		func
		enableTextures
	}
	
	def enableTextures = gl.glEnable(GL.GL_TEXTURE_2D)
	
	def disableTextures = gl.glDisable(GL.GL_TEXTURE_2D)
	
	def texture(path: String): Option[Texture] = {
		val url = getClass.getResource(if(path.startsWith("/")) path else "/" + path)
		
		try {
			val text = TextureIO.newTexture(new File(url.toURI), false)
			text.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR)
			text.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR)
			Debug.info("Loaded texture " + path)
			return Some(text)
		} catch { 
			case e => 
				Debug.error(e.getMessage())
				Debug.error("Error loading texture " + path)
		}
		return None
	}
	
	def texture(path: String, seq: Seq[Any]): Seq[Option[Texture]] = seq.map { i => texture(path.format(i)) }
	
	@deprecated("That sucks, use textures") 
	def circle(r: Int) = withoutTextures { glu.gluDisk(glu.gluNewQuadric, 0, r, 24, 10) }

	def rect(width: Int, height: Int) {
		val w2 = (width/2).toFloat;
		val h2 = (height/2).toFloat;
		gl.glBegin(GL.GL_QUADS)
		gl.glTexCoord2d(0f, 1f); gl.glVertex2f(-w2,  h2);
		gl.glTexCoord2d(1f, 1f); gl.glVertex2f( w2,  h2);
		gl.glTexCoord2d(1f, 0f); gl.glVertex2f( w2, -h2);
		gl.glTexCoord2d(0f, 0f); gl.glVertex2f(-w2, -h2);
		gl.glEnd
	}
	
	def square(size: Int) = rect(size, size)

}
