package synergia.platri

object Config {
	final val DEBUG = true
	final val FULLSCREEN = false
	final val FPS = 60
	final val width = 800
	final val height = 600
	
	lazy val WIDTH = if(FULLSCREEN) View.width else width
	lazy val HEIGHT = if(FULLSCREEN) View.height else height
	lazy val BASE_SIZE = WIDTH / 8
	lazy val CLOSE_OBJECT_DISTANCE = BASE_SIZE * 3
}
