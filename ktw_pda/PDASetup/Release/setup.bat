@call "%VS90COMNTOOLS%vsvars32.bat"
@call "%VSINSTALLDIR%\smartdevices\sdk\sdktools\cabwiz.exe" "%~dp0WMSPDASetup1.inf" /dest "%~dp0" /err CabWiz1.log