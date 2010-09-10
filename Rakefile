desc "Create packages"
task :release do
  jars = (Dir["lib/*.jar"] + %w[
    project/boot/scala-2.8.0/lib/scala-library.jar
    target/scala_2.8.0/platri_2.8.0-0.1.0.jar
  ]).map{|f| File.join(Dir.pwd, f) }
  
  natives = Dir["lib/natives/*"].inject({}){|h, f|
    h[File.basename(f)] = Dir[File.join(f, "*")].map {|e| File.join(Dir.pwd, e) }
    h
  }
      
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
      
      natives.each_pair do |name, libs|
        name = name.to_s
        FileUtils.rm_rf(name) if File.exist?(name)
        Dir.mkdir(name)
        Dir.chdir(name) do
          FileUtils.cp("../packed.jar", "platri-0.1.0.jar")
          libs.each {|n| FileUtils.cp(n, File.basename(n)) }
        end
        
        sh "zip -r platri-#{Time.now.strftime("%Y%m%d")}-#{name}.zip #{name}"
        
        FileUtils.rm_rf(name) if File.exist?(name)
      end
      
      %w[classes manifest.mf packed.jar].each do |f|
        FileUtils.rm_rf(f) if File.exist?(f)
      end
      
    end
        
  end
end