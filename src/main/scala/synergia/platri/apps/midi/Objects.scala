// package synergia.platri.apps.midi
//
// import synergia.platri._
// import TUIO.TuioObject
//
// // trait MidiObject {
// //     def call
// // }
//
// class MidiConnection(from: Object, to: Object with MidiObject) extends Connection(from, to){
//
//     def display {
//         val length = from distanceTo to
//         val angle = from angleTo to
//
//         matrix {
//             translate(from.x, from.y)
//             rotate(angle)
//             alpha(1)
//
//             App.Textures.Connection.bind
//             translate(length/2, 0)
//             rect(length-70, 40)
//         }
//     }
//
//     def call = to.call
// }
//
// class Note(tobj: TuioObject, color: OnOffTexture, note: Int) extends Object(tobj) with MidiObject {
//     def call {
//         Debug.info("[midi] Note on (" + note + ")")
//         Midi << MidiNote.on(note, 100)
//     }
//
//     override def display {
//         matrix {
//             alpha(1)
//             color.on
//             translate(x, y)
//             square(Config.BASE_SIZE)
//         }
//     }
// }
//
// class TempoDevider(tobj: TuioObject) extends Fader(tobj, App.Textures.Green) with MidiObject {
//     var called = 0
//
//     def call {
//         called += 1
//         if(called >= division){
//             outgoingConnections foreach { case c: MidiConnection => c.call }
//             called = 0
//         }
//     }
//
//     def division = Math.pow(2, (angle/90).toInt)
//
//     override def onCloseAdded(obj: Object) = obj match {
//         case o: Note => addConnection(new MidiConnection(this, o))
//         case _ =>
//     }
//
//     override def onCloseRemoved(obj: Object)  = removeConnection(obj)
// }
//
// class TempoSource(tobj: TuioObject) extends Fader(tobj, App.Textures.Red) with Loops {
//     graphics += new Graphic(this){
//         def display {
//             matrix {
//                 alpha(1)
//                 translate(parent.x, parent.y)
//                 App.Textures.Red.on
//                 square(Config.BASE_SIZE)
//             }
//         }
//     }
//
//     val tempoLoop = loop(300){
//         outgoingConnections foreach { case c: MidiConnection => c.call }
//     }
//
// //
//     override def onCloseAdded(obj: Object) = obj match {
//         case o: MidiObject => addConnection(new MidiConnection(this, o))
//         case _ =>
//     }
// //
// //     override def onCloseRemoved(obj: Object)  = removeConnection(obj)
// // }
//
// class MidiSwitch(tobj: TuioObject, note: Int, color: OnOffTexture) extends Object(tobj) {
//     var prevValue = 0.0
//
//     def value = (angle * 127 / 360).toInt
//
//     override def onMoved {
//         if(prevValue != value){
//             Midi << MidiNote.control(note, value)
//             prevValue = value
//         }
//     }
//
//     override def onRemoved {
//         super.onRemoved
//         println("dupa kurwa i chuj")
//         Midi << MidiNote.control(note, 62)
//         Midi << MidiNote.control(note, 52)
//         Midi << MidiNote.control(note, 42)
//     }
//
//     override def display {
//         // super.display
//
//         matrix {
//             translate(x, y)
//             color("#fff")
//
//             if(value < 64) color.off
//             else color.on
//
//             square(Config.BASE_SIZE)
//         }
//     }
// }
//
// class MidiFader(tobj: TuioObject, note: Int, color: OnOffTexture) extends Fader(tobj, color) {
//     def value = (angle * 127 / 360).toInt
//
//     override def onRotated {
//         Debug.info("[midi] Note on (%d, %d)".format(note, value))
//         Midi << MidiNote.control(note, value)
//     }
// }