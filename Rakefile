desc "Create packages"
task :release do
  jars = %w[
    project/boot/scala-2.8.0/lib/scala-library.jar
    lib/gluegen.jar
    lib/jogl.jar
    lib/libTUIO.jar
    target/scala_2.8.0/platri_2.8.0-0.1.0.jar
  ].map{|f| File.join(Dir.pwd, f) }
  
  Dir.chdir("target/scala_2.8.0") do
    FileUtils.rm_rf("release") if File.exist?("release")
    Dir.mkdir("release")
    Dir.chdir("release") do 
      Dir.mkdir("classes")
      Dir.chdir("classes") do
        jars.each {|jar| sh "unzip -o #{jar}" }
        FileUtils.rm_rf("META-INF") if File.exist?("META-INF")
      end


      File.open("manifest.mf", "w") {|f| f.write <<-MANIFEST }
Manifest-Version: 1.0
Main-Class: synergia.platri.View
      MANIFEST

      sh "jar cm manifest.mf -C classes/ . > packed.jar"
    end
    

      
  end
end