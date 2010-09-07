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
		case 1 => new TempoSource(tobj)
		
		case 2 => new Fader(tobj, Textures.Green)
		case 3 => new Fader(tobj, Textures.Lemon) 
		case 4 => new Fader(tobj, Textures.Purple) 
		case 5 => new Fader(tobj, Textures.Orange)
		case 6 => new Fader(tobj, Textures.Red)
		case 7 => new Fader(tobj, Textures.Yellow)
		
		case 28 => new Fader(tobj, Textures.Green)
		case 29 => new Fader(tobj, Textures.Lemon) 
		case 30 => new Fader(tobj, Textures.Purple) 
		case 31 => new Fader(tobj, Textures.Orange)
		case 32 => new Fader(tobj, Textures.Red)
		case 33 => new Fader(tobj, Textures.Yellow)
				
		case _ => new TempoSource(tobj)
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

abstract class MidiObject(tobj: TuioObject) extends Object(tobj) {
	def call
}

class Note(tobj: TuioObject, color: OnOffTexture) extends MidiObject(tobj) {
	def call = Debug.info("[midi] Note.call")
	
	override def display {
		matrix {
			alpha(1)
			color.on
			translate(x, y)
			square(Config.BASE_SIZE)
		}
	}
}

class TempoSource(tobj: TuioObject) extends Object(tobj) with Loops {
	graphics += new Graphic(this){
		def display {
			matrix {
				alpha(1)
				translate(parent.x, parent.y)
				App.Textures.Red.on
				square(Config.BASE_SIZE)
			}
		}
	}
	
	val tempo = loop(300) {
		connections foreach { case c: MidiConnection => c.call }
	}
	
	def call = {}

	override def onCloseAdded(obj: Object) = obj match {
		case o: MidiObject => addConnection(new MidiConnection(this, o))
		case _ => 
	}
	
	override def onCloseRemoved(obj: Object)  = removeConnection(obj)
}

class MidiConnection(from: Object, to: MidiObject) extends Connection(from, to){
	def display {
		val length = from distanceTo to
		val angle = from angleTo to
		
		matrix {
			translate(from.x, from.y)
			rotate(angle)
			
			color("#fff")
			App.Textures.Connection.bind
			translate(length/2, 0)
			rect(length-50, 40)
		}
	}
	
	def call = to.call
}


// class DemoCursor(c: TuioCursor) extends Cursor(c) {
// 	
// 	override def display {
// 		matrix {
// 			translate(x, y)
// 			color("#fff")
// 			circle(20)
// 		}
// 	}
// }
