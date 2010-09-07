package synergia.platri.apps.midi

import synergia.platri._
import TUIO.TuioObject
	
abstract class MidiObject(tobj: TuioObject) extends Object(tobj) {
	def call
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

class Note(tobj: TuioObject, color: OnOffTexture, note: Int) extends MidiObject(tobj) {
	def call {
		Debug.info("[midi] Note.call")
		Midi << MidiNote.on(note, 100)
	}
	
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