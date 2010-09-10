package synergia.platri

abstract class Loop(var timeout: Int) extends Thread {
	var keep = true
	
	start
	
	def stopIt { keep = false }
	
	override def run {
		while(keep){
			tick
			Thread.sleep(timeout)
		}
	}
	
	def tick
}

class FuncLoop(timeout: Int, func: () => Unit) extends Loop(timeout) {
	def tick = func()
}

trait Loops {
	def loop(timeout: Int)(f: => Unit) = new FuncLoop(timeout, f _)
}

class Texture(path: String) extends Helpers {
	val tx = texture(path)
	def bind { tx.foreach(_.bind) }
}

class OnOffTexture(onPath: String, offPath: String) extends Helpers {
	val onTexture = texture(onPath)
	val offTexture = texture(offPath)
	
	def on { onTexture.foreach(_.bind) }
	def off { offTexture.foreach(_.bind) }
}
