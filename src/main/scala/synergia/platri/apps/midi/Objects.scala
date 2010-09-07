package synergia.platri.apps.midi

import synergia.platri._
import TUIO.TuioObject
	
abstract class MidiObject(tobj: TuioObject) extends Object(tobj) {
	def call
}

class MidiConnection(from: Object, to: MidiObject) extends Connection(from, to){
	var progress = 0
	
	def display {
		val length = from distanceTo to
		val angle = from angleTo to
		
		matrix {
			translate(from.x, from.y)
			rotate(angle)
			alpha(1)
			
			// road
			matrix {
				App.Textures.Connection.bind
				translate(length/2, 0)
				rect(length-70, 40)
			}
			
			// bullet
			matrix {
				translate((length-70)*progress/100 + 35, 0)
				App.Textures.Yellow.on
				rect(10, 10)
			}			
		}
	}
	
	def call = to.call
	
	def move(i: Int) {
		progress = i
	}
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

class TempoSource(tobj: TuioObject) extends Fader(tobj, App.Textures.Red) with Loops {
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
	
	def tempo = (angle / 36).toInt + 2
	
	val tempoLoop = loop(0){
		(1 to 100) foreach { i => 
			connections foreach { case c: MidiConnection => c.move(i) }
			Thread.sleep(tempo)
		}
		
		connections foreach { case c: MidiConnection => c.call }
	}
	
	def call = {}

	override def onCloseAdded(obj: Object) = obj match {
		case o: MidiObject => addConnection(new MidiConnection(this, o))
		case _ => 
	}
	
	override def onCloseRemoved(obj: Object)  = removeConnection(obj)
}