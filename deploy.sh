

TOMCAT_WEBAPPS='/opt/homebrew/Cellar/tomcat/11.0.15/libexec/webapps'
TOMCAT_BIN='/opt/homebrew/Cellar/tomcat/11.0.15/libexec/bin'

mvn clean package

cp -f target/*.war $TOMCAT_WEBAPPS

echo "Deploiement terminé!"

$TOMCAT_BIN/catalina.sh run


