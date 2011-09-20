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

class CondLoop(var timeout: Int, cond: () => Boolean, f: => Unit) extends Thread {
    start

    override def run {
        Debug.debug("CondLoop run")
        while(cond()){
            Debug.debug("CondLoop loop")
            f
            Thread.sleep(timeout)
        }
    }
}

class FuncLoop(timeout: Int, func: () => Unit) extends Loop(timeout) {
    def tick = func()
}

trait Loops {
    def loop(timeout: Int)(f: => Unit) = new FuncLoop(timeout, f _)
    def condLoop(timeout: Int, cond: () => Boolean)(f: => Unit) = new CondLoop(timeout, cond, f _)

    def timeout(time: Int)(f: => Unit){
        val thread = new Thread {
            override def run {
                Thread.sleep(time)
                f
            }
        }
        thread.start
    }
}

