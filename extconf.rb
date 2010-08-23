require "mkmf"

# Linker flags
$LDFLAGS << " -L./core/build/Debug -lplatri" # link with libplatri.dylib

# Compiler flags
$CFLAGS << " -I./core/TUIO -I./core/oscpack" # add TUIO and osc to path

create_makefile("platri")
