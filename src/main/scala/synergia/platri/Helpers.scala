// package synergia.platri
//
// // import javax.media.opengl.{GL, GL2}
// // import javax.media.opengl.glu.GLU
// // import com.jogamp.opengl.util.gl2.GLUT
// // import com.jogamp.opengl.util.gl2.GLUT._
// // import com.jogamp.opengl.util.texture.{Texture => GLTexture, TextureIO => GLTextureIO}
// import java.io.File
// import scala.util.matching.Regex
// import scala.collection.mutable.ListBuffer
//
// trait Helpers {
//     // color
//     final val RGB = """#([a-f0-9])([a-f0-9])([a-f0-9])""".r
//     final val RGBA = """#([a-f0-9])([a-f0-9])([a-f0-9])([a-f0-9])""".r
//     final val RRGGBB = """#([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})""".r
//     final val RRGGBBAA = """#([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})([a-f0-9]{2})""".r
//
//     // private def gl = View.gl
//     // private lazy val glu = new GLU
//     // private lazy val glut = new GLUT
//
//     protected def parseColor(name: String) = {
//         def s(s: String) = {
//             val x = Integer.parseInt(s, 16)
//             (x*16 + x) / 255.0
//         }
//         def p(s: String) = Integer.parseInt(s, 16) / 255.0
//
//         name match {
//             case RGB(r,g,b) => List(r,g,b,"f").map(s)
//             case RGBA(r,g,b,a) => List(r,g,b,a).map(s)
//             case RRGGBB(r,g,b) => List(r,g,b,"ff").map(p)
//             case RRGGBBAA(r,g,b,a) => List(r,g,b,a).map(p)
//         }
//     }
//
//
//     def text(x: Int, y: Int, str: String) { text(x, y, str, true) }
//
//     def text(x: Int, y: Int, str: String, center: Boolean) {
//         // val font = BITMAP_8_BY_13
//         //
//         // if(center){
//         //     val len = glut.glutBitmapLength(font, str)
//         //     gl.glRasterPos2f(List(List(x-(len/2), len/2).max, Config.WIDTH-(len/2)).min, y)
//         // } else {
//         //     gl.glRasterPos2f(x, y)
//         // }
//         //
//         // str.toArray.foreach(glut.glutBitmapCharacter(font, _))
//     }
//
//     // def color(r: Double, g: Double, b: Double) = gl.glColor3f(r.toFloat, g.toFloat, b.toFloat)
//
//     // def color(name: String) = {
//     //     val rgba = parseColor(name)
//     //     gl.glColor4d(rgba(0), rgba(1), rgba(2), rgba(3))
//     // }
//
//     def clear(name: String) = {
//         // val rgba = parseColor(name).map(_.toFloat)
//         // gl.glClearColor(rgba(0), rgba(1), rgba(2), rgba(3))
//     }
//
//     // def alpha(value: Double) = gl.glColor4d(1.0, 1.0, 1.0, value)
//
//     def translate(x: Double, y: Double) {} // gl.glTranslated(x, y, 0)
//
//     def translateAnd(x: Double, y: Double)(func: => Unit) {
//         // matrix {
//         //     translate(x, y)
//         //     func
//         // }
//     }
//
//     def rotate(angle: Double) {}// gl.glRotated(angle, 0, 0, 1)
//
//     def rotateAnd(angle: Double)(func: => Unit) {
//         // matrix {
//         //     rotate(angle)
//         //     func
//         // }
//     }
//
//     def matrix(func: => Unit) {
//         // gl.glPushMatrix
//         // func
//         // gl.glPopMatrix
//     }
//
//     def quad(func: => Unit) {
//         // gl.glBegin(GL2.GL_QUADS)
//         // func
//         // gl.glEnd
//     }
//
//     def withoutTextures(func: => Unit) {
//         // disableTextures
//         // func
//         // enableTextures
//     }
//
//     // def enableTextures = gl.glEnable(GL.GL_TEXTURE_2D)
//
//     // def disableTextures = gl.glDisable(GL.GL_TEXTURE_2D)
//
//     def texture(path: String): Option[{def bind }] = {
//         // val ext = path.split('.').last
//         // val stream = getClass.getResourceAsStream(if(path.startsWith("/")) path else "/" + path)
//         //
//         // try {
//         //     val text = GLTextureIO.newTexture(stream, false, ext)
//         //     text.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR)
//         //     text.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR)
//         //     Debug.info("Loaded texture " + path)
//         //     return Some(text)
//         // } catch {
//         //     case e =>
//         //         Debug.error(e.getMessage())
//         //         Debug.error("Error loading texture " + path)
//         // }
//         return None
//     }
//
//     // def texture(path: String, seq: Seq[Any]): Seq[Option[GLTexture]] = seq.map { i => texture(path.format(i)) }
//     //
//     // @deprecated("That sucks, use textures")
//     // def circle(r: Int) = withoutTextures { glu.gluDisk(glu.gluNewQuadric, 0, r, 24, 10) }
//     //
//     // def rect(width: Double, height: Double) {
//     //     val w2 = (width/2).toFloat;
//     //     val h2 = (height/2).toFloat;
//     //     gl.glBegin(GL2.GL_QUADS)
//     //     gl.glTexCoord2d(0f, 1f); gl.glVertex2d(-w2,  h2);
//     //     gl.glTexCoord2d(1f, 1f); gl.glVertex2d( w2,  h2);
//     //     gl.glTexCoord2d(1f, 0f); gl.glVertex2d( w2, -h2);
//     //     gl.glTexCoord2d(0f, 0f); gl.glVertex2d(-w2, -h2);
//     //     gl.glEnd
//     // }
//     //
//     // def rect(x: Double, y: Double, width: Double, height: Double) {
//     //     gl.glBegin(GL2.GL_QUADS)
//     //     gl.glTexCoord2d(0f, 1f); gl.glVertex2d(x,       y+height);
//     //     gl.glTexCoord2d(1f, 1f); gl.glVertex2d(x+width, y+height);
//     //     gl.glTexCoord2d(1f, 0f); gl.glVertex2d(x+width, y);
//     //     gl.glTexCoord2d(0f, 0f); gl.glVertex2d(x,       y);
//     //     gl.glEnd
//     // }
//
//     // def square(size: Double) = rect(size, size)
//
//     // def vec(x: Double, y: Double) = gl.glVertex2d(x, y)
// }
