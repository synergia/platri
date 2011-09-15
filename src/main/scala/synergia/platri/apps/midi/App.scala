package synergia.platri.apps.midi

import synergia.platri._
import TUIO._

object App extends Application {
    object Textures {
        val Green     = new OnOffTexture("green.png",  "off.png")
        val Lemon     = new OnOffTexture("lemon.png",  "off.png")
        val Purple     = new OnOffTexture("purple.png", "off.png")
        val Orange     = new OnOffTexture("orange.png", "off2.png")
        val Red     = new OnOffTexture("red.png",     "off2.png")
        val Yellow     = new OnOffTexture("yellow.png", "off2.png")
        val Connection = new Texture("connection.png")
        val Background = new Texture("metal-texture.jpg")
    }

    
    override def createObject(tobj: TuioObject) = sym(tobj) match {
        // case 0 => new MidiFader(tobj,  21, Textures.Green)
        // case 1 => new MidiFader(tobj,  22, Textures.Red)
        // case 2 => new MidiSwitch(tobj, 23, Textures.Orange)
        // case 3 => new MidiSwitch(tobj, 24, Textures.Yellow)
        
        case 110 => new MidiSwitch(tobj, 21, Textures.Green)
        case 111 => new MidiSwitch(tobj, 22, Textures.Lemon)
        case 112 => new MidiSwitch(tobj, 23, Textures.Purple)
        case 113 => new MidiSwitch(tobj, 24, Textures.Orange)
        case 114 => new MidiFader(tobj, 25, Textures.Red)
        case 115 => new MidiSwitch(tobj, 26, Textures.Yellow)
        case 116 => new MidiSwitch(tobj, 27, Textures.Red)
        
        // case 4 => new TempoDevider(tobj)
        // case 5 => new TempoDevider(tobj)
        // case 6 => new TempoDevider(tobj)
        
        case _ => new Fader(tobj, Textures.Green)
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
        Textures
        clear("#eee")
        // Textures.Background.bind
        // matrix {
            // alpha(1)
            // rect(0, 0, Calibration.width, Calibration.height)
        // }        
    }
}


