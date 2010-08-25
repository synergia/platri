class Demo < Platri::Application
  def start
    puts "Starting up Demo application"
  end
  
  def display
    puts "Display!!"
    # circle(50)
    # circle(50)
    
  end
  
  def createObject(tobj)
    case symbol(tobj)
    when 0
      DemoObject.new(tobj)
    else
      DemoObject.new(tobj)
    end
  end
end

class DemoObject < Platri::Object
  def display
    color "#f00" 
      translate x, y do
    end
  end
end
