package synergia.platri

import scala.collection.mutable.Map

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

object Properties {
    final val configFileName = "config.ini"

    val data = loadFile(configFileName)

    def apply(key: String) = data.map(_(key)) getOrElse ""

    def update(key: String, value: String) {
        data.foreach(_(key) = value)
    }

    protected def loadFile(fname: String) : Option[Map[String,String]] = {
        try {
            val props = new java.util.Properties
            props.load(new java.io.FileInputStream(fname))
            import scala.collection.JavaConversions._

            val m: scala.collection.mutable.Map[String, String] = props
            Debug.info("Loaded properties file")
            Some(Map.empty ++ m.toMap.withDefaultValue(""))
        }
        catch {
            case e: Exception =>
                Debug.info("Properties.loadFile: " + e)
                None
        }
    }

    def save {
        try {
            val jprops = new java.util.Properties
            data.get.foreach(a => jprops.put(a._1, a._2))
            val file = new java.io.FileOutputStream(configFileName)
            jprops.store(file, "Scala Properties: " + configFileName)
            file.close
            Debug.info("Properties saved")
        } catch {
            case e: Exception =>
                Debug.error("Properties.saveFile: " + e)
        }
    }

}
