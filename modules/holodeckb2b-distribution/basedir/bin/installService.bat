rem Install Script
if "%1"=="" goto help
if "%2"=="" goto help
if "%3"=="" goto help

if "%1"=="rm" goto uninstallAs4

goto installAS4

:help
echo  Usage: installService.bat holodeckb2bhome javahome servicename
echo  Usage: installService.bat rm holodeckb2bhome servicename
goto end

:installAS4
rem install as4 as service
set AXIS2_HOME=%1
set JAVA_HOME=%2
set SERVICE_NAME=%3

set AXIS2_CLASS_PATH=%AXIS2_HOME%;%AXIS2_HOME%\conf;%JAVA_HOME%\lib\tools.jar;%AXIS2_HOME%\lib\*

cd %AXIS2_HOME%\bin
holodeckb2b.exe //IS//%SERVICE_NAME% ^
 --DisplayName %SERVICE_NAME% ^
 --Install %AXIS2_HOME%\bin\holodeckb2b.exe ^
 --LogPrefix %SERVICE_NAME% ^
 --LogLevel Debug ^
 --LogPath %AXIS2_HOME%\logs ^
 --StdOutput %AXIS2_HOME%\logs\stdout.log ^
 --StdError %AXIS2_HOME%\logs\stdout.log ^
 --JavaHome %JAVA_HOME% ^
 --Jvm "%JVM%" ^
 --Classpath "%AXIS2_CLASS_PATH%" ^
 --JvmOptions "-Djava.endorsed.dirs=%AXIS2_HOME%\lib\endorsed;-Djava.endorsed.dirs=%JAVA_HOME%\jre\lib\endorsed;-Djava.endorsed.dirs=%JAVA_HOME%\lib\endorsed" ^
 --JvmMs 512 ^
 --JvmMx 1024 ^
 --StartMode jvm ^
 --StopMode jvm ^
 --StartPath %AXIS2_HOME% ^
 --StopPath %AXIS2_HOME% ^
 --StartClass org.holodeckb2b.core.HolodeckB2BCommonsDaemonBootstrap ^
 --StopClass org.holodeckb2b.core.HolodeckB2BCommonsDaemonBootstrap ^
 --StartParams "start 9090 %AXIS2_HOME%" ^
 --StopParams "stop 9090"

goto end

:uninstallAS4
rem remove as4 service
set AXIS2_HOME=%2
set SERVICE_NAME=%3

cd %AXIS2_HOME%\bin
holodeckb2b.exe //DS//%SERVICE_NAME%

:end