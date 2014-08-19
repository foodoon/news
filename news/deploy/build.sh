cd .. && svn up
tomcat_home=/usr/tomcat-sms
sh ${tomcat_home}/bin/shutdown.sh
mvn package -Dmaven.test.skip=true
mv assembly/target/news-1.0-SNAPSHOT.war ROOT.war
cp ROOT.war ${tomcat_home}/webapps/

rm -rf ${tomcat_home}/webapps/ROOT
sh ${tomcat_home}/bin/startup.sh
