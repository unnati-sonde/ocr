echo off

IF "%1"=="" (
    SET PORT=7020
) ELSE (
    SET PORT=%1
)

call .\set-env

IF "%JAVA_HOME%"=="" GOTO :nojavahome

SET "WAR_FILE=ocr*.war"

REM Introduced loop here to evaluate expression in WAR_FILE so that file name can be promatch-restANY_TEXT.war
FOR %%F IN (..\bin\%WAR_FILE%) DO (
    "%JAVA_HOME%/bin/java" -Xms6114m -Xmx6114m -cp "../*" -Dserver.port=%PORT% -Dspring.config.location=../conf/application.properties -Dlogging.config=../conf/log4j2.xml -jar ../bin/ocr.war
GOTO :end
)

:nojavahome
echo JAVA_HOME not set. Please edit the file set-env.bat and configure the JAVA_HOME property.

:end