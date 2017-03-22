@echo off
java -Xmx512M -cp "%~dp0\src\main\java;%~dp0\src\main\resources;%~dp0\target\ktw-2.0\WEB-INF\classes;%~dp0\..\scpview\src\main\java;%~dp0\..\scpview\target\classes;%MVN_REPO%\com\google\gwt\gwt-user\2.4.0\gwt-user-2.4.0.jar;%MVN_REPO%\com\google\gwt\gwt-dev\2.4.0\gwt-dev-2.4.0.jar;%MVN_REPO%\com\smartgwt\smartgwt\3.1\smartgwt-3.1.jar;" com.google.gwt.dev.Compiler -war "%~dp0\war\com.core.scpwms" %* com.core.scpwms.scpwms -style OBF

if ERRORLEVEL 1 goto error
if ERRORLEVEL 0 goto exit
:error
pause
:exit
