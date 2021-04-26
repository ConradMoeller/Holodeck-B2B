@echo off

rem ---------------------------------------------------------------------------
rem Startup script for the local Holobeck B2B command line monitoring tool
rem
rem Environment Variable 
rem
rem   JAVA_HOME       MUST point at your Java Development Kit installation.
rem ---------------------------------------------------------------------------

"%JAVA_HOME%/bin/java.exe" -jar ../webui/holodeckb2b-webui.jar --server.port=8088
