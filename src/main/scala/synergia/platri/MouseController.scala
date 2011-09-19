// package synergia.platri
//
// import java.awt.AWTException;
// import java.awt.MouseInfo;
// import java.awt.Point;
// import java.awt.PointerInfo;
// import java.awt.Robot;
// import java.awt.event.InputEvent;
//
//
// class MouseController(jump : Int) {
//  def this() = this(10)
//  val robot = new Robot()
//
//  def currentLocation() = {
//      val a = MouseInfo.getPointerInfo();
//      a.getLocation()
//  }
//
//  def moveRight(){
//      val b = currentLocation();
//      robot.mouseMove((b.getX()).toInt + jump, (b.getY()).toInt);
//  }
//
//  def moveLeft(){
//      val b = currentLocation();
//      robot.mouseMove((b.getX()).toInt - jump, (b.getY()).toInt);
//  }
//
//  def moveUp(){
//      val b = currentLocation();
//      robot.mouseMove((b.getX()).toInt , (b.getY()).toInt - jump);
//  }
//
//  def moveDown(){
//      val b = currentLocation();
//      robot.mouseMove( (b.getX()).toInt , (b.getY()).toInt + jump);
//  }
//
//  def leftButtonPress(){
//      robot.mousePress(InputEvent.BUTTON1_MASK);
//  }
//
//  def leftButtonRelease(){
//      robot.mouseRelease(InputEvent.BUTTON1_MASK);
//  }
//
//  def leftButtonClicked(){
//      leftButtonPress();
//      leftButtonRelease();
//  }
//
// }
//
// object MouseController {
//  def main(args:Array[String]) = {
//      val mc = new MouseController(5);
//
//      try{
//          for(i <- 0 until 20){
//              mc.moveRight();
//              Thread.sleep(500);
//          }
//          for(i <- 0 until 20){
//              mc.moveUp();
//              Thread.sleep(500);
//          }
//          for(i <- 0 until 20){
//              mc.moveLeft();
//              Thread.sleep(500);
//          }
//          for(i <- 0 until 20){
//              mc.moveDown();
//              Thread.sleep(500);
//          }
//      } catch {
//          case e : InterruptedException => e.printStackTrace();
//      }
//
//  }
// }
