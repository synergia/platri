package synergia.platri

object Debug extends Helpers {
	final val INFO = 1
	final val WARN = 2
	final val ERROR = 3
	final val EVENT = 4
	
	private var frameCount = 0
	private var timebase = 0L
	private var fpsStr = ""
	
	def info(msg: String) = log(INFO, "[info] " + msg, Console.RESET)
	def warn(msg: String) = log(WARN, "[warn] " + msg, Console.YELLOW)
	def error(msg: String) = log(ERROR, "[error] " + msg, Console.RED)
	def event(msg: String) = log(EVENT, "[event] " + msg, Console.BLUE)
	
	def log(kind: Int, msg: String, color: String) {
		System.out.print(color)
		System.out.println(msg)
		System.out.print(Console.RESET)
	}
	
	def display {
		frameCount += 1
		val time = System.currentTimeMillis

		if(time - timebase > 1000) {
			fpsStr = "FPS:%4.2f".format(frameCount * 1000.0 / (time - timebase))
			timebase = time
			frameCount = 0
		}
		
		color("#fff")
		text(Config.WIDTH-100, 60, fpsStr)
	}
	
}
