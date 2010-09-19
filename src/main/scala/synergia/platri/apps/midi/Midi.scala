package synergia.platri.apps.midi

import synergia.platri.Debug
import javax.sound.midi._

object MidiNote {
	def on(data1: Int, data2: Int) = msg(ShortMessage.NOTE_ON, data1, data2)
	
	def off(data1: Int, data2: Int) = msg(ShortMessage.NOTE_OFF, data1, data2)
	
	def msg(kind: Int, data1: Int, data2: Int) = raw(kind, 9, data1, data2)
	
	def raw(kind: Int, channel: Int, data1: Int, data2: Int) = {
		val m = new ShortMessage
		m.setMessage(kind, channel, data1, data2)
		m
	}
}

object Midi {
	var device: Option[MidiDevice] = None
	var receiver: Option[Receiver] = None
	
	def <<(msg: MidiMessage) = receiver.foreach(_.send(msg, -1))
	
	def start(deviceName: String) {
		findReceiver(deviceName)
		
		// set drums!
		val msg = new ShortMessage
		msg.setMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0)
		this << msg
	}
	
	def findReceiver(deviceName: String) {
		val devices = MidiSystem.getMidiDeviceInfo.toList
		
		devices.filter(_.getName == deviceName).map(MidiSystem.getMidiDevice(_)).foreach { d => 
			try {
				Debug.info("[midi] Opening MIDI device: " + deviceName)
				d.open
				receiver = if(d.getReceiver != null) Some(d.getReceiver) else None
				return
			} catch {
				case _ => 
			}
		}
		
		if(receiver == None) {
			Debug.error("Could not find device with name: " + deviceName)
			Debug.info("Available devices:")
			devices.foreach(e => println(" * " + e.getName))	
			exit(0)
		}
	}
	
	def stop {
		receiver foreach (_.close)
		device foreach (_.close)
	}
	
}