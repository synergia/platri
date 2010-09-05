package synergia.platri

import scala.collection.mutable.ListBuffer

object Debug extends Helpers {
	final val INFO = 1
	final val WARN = 2
	final val ERROR = 3
	final val EVENT = 4
	
	private var frameCount = 0
	private var timebase = 0L
	private var fpsStr = ""
	
	val messages = new ListBuffer[(String, Int)]()
	
	def info(msg: String) = log(INFO, "[info] " + msg)
	def warn(msg: String) = log(WARN, "[warn] " + msg)
	def error(msg: String) = log(ERROR, "[error] " + msg)
	def event(msg: String) = log(EVENT, "[event] " + msg)
	
	def log(kind: Int, msg: String) {
		println(msg)
		messages += ((msg, kind))
	}
	
	def display {
		frameCount += 1;
		val time = System.currentTimeMillis

		if(time - timebase > 1000) {
			fpsStr = "FPS:%4.2f".format(frameCount * 1000.0 / (time - timebase))
			timebase = time
			frameCount = 0
		}
		
		color("#000")
		text(Config.WIDTH-100, 60, fpsStr)
		
		// displayLog
	}
	
	def displayLog {
		messages.takeRight(35).zipWithIndex.foreach { case((msg, kind), i) => 
			kind match {
				case INFO => color("#777")
				case WARN => color("#f90")
				case ERROR => color("#f00")
				case EVENT => color("#00f")
			} 
			text(10, 20 + i*15, msg, false)
		}
	}
	
}
