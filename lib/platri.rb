require File.join(File.expand_path(File.dirname(__FILE__)), "..", "platri.bundle")
require File.join(File.expand_path(File.dirname(__FILE__)), "..", "opengl_helpers.bundle")

GL = Opengl_helpers

require "ffi-opengl-dsl/font"
require "ffi-opengl-dsl/helpers"


module Platri
  class Application
    include OpenGL::Helpers
    
    def symbol(tobj)
      tobj.getSymbolID
    end
    
    
    class << self
      attr_accessor :applications
      
      def inherited(base)
        (@applications ||= []) << base
      end
      
    end
  end
  
  class Object
    include OpenGL::Helpers
  end
  
  class << self
    def applications
      Application.applications
    end
  end
end



Dir[File.join(File.expand_path(File.dirname(__FILE__)), "..", "apps", "*/app.rb")].each do |app|
  require app
end
