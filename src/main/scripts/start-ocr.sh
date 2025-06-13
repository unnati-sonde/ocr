#!/bin/sh

if [ -z $1 ]
then
  PORT=7020
else
  PORT=$1
fi

. ./set-env.sh

# JAVA_HOME must be set in set-env.sh file
if [ -z "$JAVA_HOME" ]
then
  echo "JAVA_HOME not set. Please edit the file set-env.sh and configure the JAVA_HOME property."
  exit 1
fi
"$JAVA_HOME/bin/java" -Xms6114m -Xmx6114m -cp "../*" -Dserver.port="$PORT" -Dspring.config.location="../conf/application.properties" -Dlogging.config=""../conf/log4j2.xml"" -jar ../bin/ocr.war
