require "mkmf"

# Linker flags
$LDFLAGS << " -L./core/build/Debug -lplatri" # link with libplatri.dylib

# Compiler flags
$CFLAGS << " -I./core/TUIO -I./core/oscpack" # add TUIO and osc to path

$objs = ARGV.map {|a| "#{a}_wrap.o"}

create_makefile(ARGV.first || "hello")
