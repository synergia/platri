package synergia.platri.apps.midi

import synergia.platri._
import TUIO._

object App extends Application {
	object Textures {
		val Green 	= new OnOffTexture("green.png",  "off.png")
		val Lemon 	= new OnOffTexture("lemon.png",  "off.png")
		val Purple 	= new OnOffTexture("purple.png", "off.png")
		val Orange 	= new OnOffTexture("orange.png", "off2.png")
		val Red 	= new OnOffTexture("red.png",	 "off2.png")
		val Yellow 	= new OnOffTexture("yellow.png", "off2.png")
		val Connection = new Texture("connection.png")
		val Background = new Texture("bg.png")
	}

	
	override def createObject(tobj: TuioObject) = sym(tobj) match {
		case 0 => new TempoSource(tobj)
		case 1 => new Note(tobj, Textures.Green, 48)
		case 2 => new Note(tobj, Textures.Orange, 35)
		case 3 => new Note(tobj, Textures.Yellow, 38)
		case 4 => new TempoDevider(tobj)
		case 5 => new TempoDevider(tobj)
		case 6 => new TempoDevider(tobj)
		
		case _ => new TempoSource(tobj)
	}
	
	override def start {
		Debug.info("Application started")
		Midi.start(Properties("apps.midi.device"))
	}
	
	override def stop {
		Debug.info("Application stopped")
		Midi.stop
	}
	
	override def display {
		clear("#000")
		Textures.Background.bind
		matrix {
			alpha(1)
			rect(0, 0, Config.WIDTH, Config.HEIGHT)
		}		
	}
}


