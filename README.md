# platri

## Compile
1. install sbt ( http://code.google.com/p/simple-build-tool/wiki/Setup )
2. copy lib/natives/* to ./ (jnlib for mac, dll for win, so for linux)
3. Download and run TUIO Simulator http://github.com/synergia/TUIO_Simulator/downloads
4. $ sbt run

## Package
    $ sbt package && rake release


## Resources

* http://github.com/fajran/tongseng
* http://jogamp.org/deployment/autobuilds/jogl-b164-2010-09-06_19-42-16/build/
* http://jogamp.org/wiki/index.php/Jogl_Tutorial
