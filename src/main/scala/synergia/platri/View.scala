package synergia.platri

import java.awt._
import java.awt.event._
import javax.media.opengl._
import javax.media.opengl.GL._
import javax.media.opengl.glu.GLU
import javax.media.opengl.awt.GLCanvas
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import com.jogamp.opengl.util._

object View {
	import Config._
	
	var gl: GL2 = _
	
	class Scene(canvas: GLCanvas) extends Frame with GLEventListener with KeyListener {
		val device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()(0)
		
		setTitle("Fullscreen")
		add(canvas)
		setUndecorated(FULLSCREEN)
		
		addWindowListener(new WindowAdapter(){
			override def windowActivated(e: WindowEvent){
				activated
			}
			
			override def windowClosing(e: WindowEvent){
				View.stop
				exit(0)
			}
		})
		
		def activated {
			if(Config.FULLSCREEN){
				setResizable(false)
				device.setFullScreenWindow(this)
			} else {
				setSize(WIDTH, HEIGHT)
			}
			validate
		}
			
		def display(drawable: GLAutoDrawable) {
			gl = drawable.getGL.getGL2
			gl.glClear(GL_COLOR_BUFFER_BIT)
			gl.glClearColor(1f, 1f, 1f, 1f)
			
			if(DEBUG) Debug.display
	
			gl.glPushMatrix
			gl.glEnable(GL.GL_TEXTURE_2D)
			manager.display
			gl.glDisable(GL.GL_TEXTURE_2D)
			gl.glPopMatrix
			
			gl.glFlush
		}

		def init(drawable: GLAutoDrawable) {
			drawable.getGL.setSwapInterval(1)
			gl = drawable.getGL.getGL2
						
			gl.glClearColor(0f, 0f, 0f, 0f)
			gl.glEnable(GL_BLEND)
	        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
		}

		def dispose(drawable: GLAutoDrawable) {
		    // put your cleanup code here
		}

		def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
			gl = drawable.getGL.getGL2
			gl.glViewport(0, 0, WIDTH, HEIGHT)
			gl.glMatrixMode(GL_PROJECTION)
			gl.glLoadIdentity
			(new GLU).gluOrtho2D(0, WIDTH, HEIGHT, 0)
			gl.glMatrixMode(GL_MODELVIEW)
		}
		
		def keyReleased(event: KeyEvent) {}
		
		def keyPressed(event: KeyEvent) {}
		
		def keyTyped(event: KeyEvent) {
			event.getKeyCode match {
				case KeyEvent.VK_ESCAPE => 
					View.stop
					exit(0)
				case c => Debug.info("[key] " + c)
			}
		}
	}
	
	GLProfile.initSingleton
	
	val glp = GLProfile.getDefault
	val caps = new GLCapabilities(glp)
	val canvas = new GLCanvas(caps)
	val scene = new Scene(canvas)
	canvas.addGLEventListener(scene)
	canvas.addKeyListener(scene)
	scene.setVisible(true)
	
	val manager = new Manager(apps.midi.App)
	val animator = new Animator(canvas)
	// val animator = new FPSAnimator(canvas, FPS)
	animator.add(canvas)
	animator.start
		
	def stop {
		manager.stop
		animator.stop
	}
	
	def width = scene.getWidth()
	
	def height = scene.getHeight()
	
	
	def main(args: Array[String]) = View	
}
