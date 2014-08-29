cd .. && git pull
tomcat_home=/usr/tomcat-news
mvn package -Dmaven.test.skip=true
mv assembly/target/news-1.0-SNAPSHOT.war ROOT.war
rm -rf ${tomcat_home}/webapps/ROOT
rm -rf ${tomcat_home}/webapps/ROOT.war
cp ROOT.war ${tomcat_home}/webapps/

