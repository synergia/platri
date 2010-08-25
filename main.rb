# require "rubygems"
require File.join(File.expand_path(File.dirname(__FILE__)), "lib", "platri")


Platri::View.init
Platri::View.start(Demo.new)
