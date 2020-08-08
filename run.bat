
@echo off
cls

set ERRMSG=
set PRAC_BIN=.\bin
set PRAC_DOCS=.\docs
set PRAC_SRC=.\src

javac -sourcepath %PRAC_SRC% -cp "%PRAC_BIN%;%PRAC_LIB%" -d %PRAC_BIN% %PRAC_SRC%\Main.java

java -cp %PRAC_BIN%;%PRAC_LIB% Main


cd %PRAC_DOCS%
pause