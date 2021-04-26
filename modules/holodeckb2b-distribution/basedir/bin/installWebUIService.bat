set JAVA_HOME=%1
set SERVICE_NAME=%2
set JVM=%JAVA_HOME%\jre\bin\server\jvm.dll

holodeckb2b.exe //IS//%SERVICE_NAME% ^
 --DisplayName=%SERVICE_NAME% ^
 --Startup=auto ^
 --Install=%CD%\holodeckb2b.exe ^
 --LogPrefix %SERVICE_NAME% ^
 --LogLevel Debug ^
 --LogPath %CD%\..\logs ^
 --StdOutput %CD%\..\logs\web-ui-stdout.log ^
 --StdError %CD%\..\logs\web-ui-stdout.log ^
 --JavaHome %JAVA_HOME% ^
 --Jvm=%JVM% ^
 --Classpath=%CD%\..\webui\holodeckb2b-webui.jar ^
 --StartMode=jvm ^
 --StartClass=org.springframework.boot.loader.JarLauncher ^
 --StartMethod=main ^
 --StopMode=jvm ^
 --StopClass=org.springframework.boot.loader.JarLauncher ^
 --StopMethod=main ^
 --StopParams=stop