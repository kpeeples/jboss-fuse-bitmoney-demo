jboss-fuse-bitmoney-demo
========================

#  Jboss Fuse, Camel, Android, Mqtt
This demonstration will show how to use Jboss Fuse and Mqtt to communicate from android to a fuse instance.  Messages are routed between fuse instances using mqtt topics, and beans, utlizing spring and camel.

## Required Software: 
Download the software necessary to run the application.  Installation instructions are below.
1.  jboss fuse version 6.0.0 http://www.jboss.org/products/fuse
2.  jboss fuse IDE http://www.jboss.org/products/fuse
3.  Android Development Toolkit (https://developer.android.com/tools/sdk/eclipse-adt.html)
5.  Maven version 3.+ (sudo yum install maven or manually at http://maven.apache.org/download.cgi) 

## Installing Jboss Fuse IDE
1.  Open a terminal
2.  mkdir bitmoney-demo
3.  mv <dowload diretory>/FuseID-6.0.0.129-linux.gtk.x86.zip ~/bitmoney-demo
4.  cd ~/bitmoney-demo
5.  unzip FuseID-6.0.0.129-linux.gtk.x86.zip
6.  rm FuseID-6.0.0.129-linux.gtk.x86.zip

## Install the Android SDK
1.  Open a terminal
2.  cd bitmoney-demo
3.  tar xvfz <download directory>/android-sdk_r22.0.1-linuz.tgz

## running the IDE
1.  Open a terman
2.  cd bitmoney-demo
3.  ./FuseIDE

**Creating an emulator**
1. Open a terminal
2. cd to <android_sdk_location>/tools (bitmoney-demo/android-sdk-linux)
3. run ./android
4. Select Tools, Android 4.2.2 (API 17)
5. Click Install
6. Click Accept License
7. Click Install
8. After the installation is complete, Select the Tools, Manage SDKs menu item
9. Click New
10. type nexus4 for the name
11. Select Galaxy Nexus as the device
12. Select Android 4.2.2 - API Level 17 as the device
13. Click OK
14. Click OK
15. Hit Control-C in the terminal window

**Installing m2e**
1.  Run the FuseIDE if it is not open
You can install the Android Connector for Maven via the Eclipse Marketplace. 
2.  Select Help -> "Install New Software..."
3.  If Android SDK is not there "Add" other wise skip to 
4.  Enter Name: Eclipse Software Location: http://download.eclipse.org/releases/juno
5.  Repeat for the following repositories
Name: M2E Maven Archiver Connector - 
Location: http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-mavenarchiver/0.15.0/N/0.15.0.201207090125/
.
Name: org.sonatype.tycho.update 
Location: http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-tycho/0.6.0/N/0.6.0.201207302152/
.
Name: Maven Integration for Android Development Tools Dependency Sites
Location: http://rgladwell.github.com/m2e-android/updates/
6. search for "m2e".
7. Select android configurator for m2e
8. Click Next
9. Click Next
10. Accept License
11. Click Finish
12.  Click ok on the security warning
13.  Click restart now
14.  On restart configure the sdk
15.  Click use existing android sdk
16.  browse to sdk directory bitmoney-demo/android-linux-sdk
17.  Click ok

**Download the project**
1.  Open a Terminal
2.  cd bitmoney-demo
3.  If git is not installed type sudo yum install git and install
4.  type git clone http://github.com/kpeeples/jboss-fuse-bitmoney-demo


**Import project**
1.  In Eclipse select Import
2.  Select existing maven project
3.  Navigate to the jboss-fuse-bitmoney-demo directory
4.  Click "OK"
5.  Select the BitMoneyService and bitmoney projects
6.  Select OK
7.  Click Finish
8.  Click OK on the error that pops up
9.  Click Finish on the install
10.  Clock OK on the warning
11.  Click OK to restart
12.  

**Project Setup**
*Fix SDK location issues
1.  Open a terminal
2.  cd bitmoney/android-sdk-linux/platform-tools
3.  run ln -s ../build-tools/17.0.0/aapt aapt
4.  run ln -s ../build-tools/17.0.0/lib/dx.jar dx.jar
5.  cd ~/bitmoney-demo/jboss-fuse-bitmoney-demo
6.  mvn android:generate-sources
7.  Inside of the Fuse IDE
8.  Right click on the bitmoney project and select properties
9.  Select android 
10.  Check Android 4.2.2
11.  Click OK
12.  Right Click on bitmoney
13.  Select Maven-->Update Project
14.  select bitmoney
15.  Click OK

## Compile Fuse Services
cd /home/fedora/bitmoney-demo/jboss-fuse-bitmoney-demo/BitmoneyService
mvn initialize org.apache.felix:maven-bundle-plugin:bundle install -Pna
mvn initialize org.apache.felix:maven-bundle-plugin:bundle install -Pemea
mvn initialize org.apache.felix:maven-bundle-plugin:bundle install -Pltam
mvn initialize org.apache.felix:maven-bundle-plugin:bundle install -Papac


## Running the android application
1.  Right click on bitmoney
2.  Select Run As --> Android Application

Fuse Step by Step
 mkdir fuse
 cd fuse/
 mkdir na
mkdir emea
 mkdir apac
 mkdir ltam
cd na/
 unzip /home/fedora/Downloads/jboss-fuse-full-6.0.0.redhat-024.zip 
cd ..
 ls
 cd apac/
 unzip /home/fedora/Downloads/jboss-fuse-full-6.0.0.redhat-024.zip 
 cd ..
 ls
 cd emea/
 unzip /home/fedora/Downloads/jboss-fuse-full-6.0.0.redhat-024.zip 
  cd ../ltam/
unzip /home/fedora/Downloads/jboss-fuse-full-6.0.0.redhat-024.zip 
download the mysql connector (http://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.25.zip/from/http://cdn.mysql.com/)
download http://repo1.maven.org/maven2/org/apache/servicemix/bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3/org.apache.servicemix.bundles.commons-dbcp-1.4_3.jar
cd ~/Downloads/
unzip mysql-connector-java-5.1.25.zip
mvn install:install-file -Dfile=org.apache.servicemix.bundles.commons-dbcp-1.4_3.jar -DgroupId=org.apache.servicemix.bundles -DartifactId=org.apache.servicemix.bundles.commons-dbcp -Dversion=1.4_3 -Dpackaging=jar -DlocalRepositoryParth=~/.m2/repository


* Installation steps for each instance
Note there will be an error for mqtt-client 1.5 when installing BitcoinService, this will not happen when server is started.  Ignore the error during install
* NA Install
cd ~/bitmoney-demo/fuse/na/jboss-fuse-6.0.0.redhat-024/deploy/
cp ~/Downloads/mysql-connector-java-5.1.25/mysql-connector-java-5.1.25-bin.jar .
cd ../etc
edit jre.properties
make the modifications described in "Modify jre.properties"
edit  org.apache.karaf.management.cfg 
change rmiRegistryPort to 1093
change rmieServerPort to 44443
edit system.properties
change org.osgi.service.http.port to 8183
change activemq.port activemq.port to 61613
change activemq.jmx.url = 1093
edit jetty.xml
change jetty.port to 8183
org.apache.karaf.features.cfg and edit the featuresBoot line
 featuresBoot=jasypt-encryption,config,management,fabric-boot-commands,fabric-bundle,fabric-maven-proxy,patch,activemq,mq-fabric,camel-xmlbeans,camel,camel-cxf,camel-jms,activemq-xbeans,activemq-camel,camel-blueprint,camel-csv,camel-ftp,camel-bindy,camel-jdbc,camel-exec,camel-jasypt,camel-saxon,camel-snmp,camel-ognl,camel-routebox,camel-script,camel-spring-javaconfig,camel-jaxb,camel-jetty,camel-jmx,camel-mail,camel-paxlogging,camel-rmi,camel-sql,war,camel-xmlbeans,camel-xmljson,camel-xmlsecurity

cd ../bin
start the fuse application ./fuse
type features:install wrapper
wrapper install -n na -d na-service -D na-service
* Optional *
  To install the service:
    $ ln -s /home/fedora/bitmoney-demo/fuse/na/jboss-fuse-6.0.0.redhat-024/bin/na-service /etc/init.d/
    $ chkconfig na-service --add

  To start the service when the machine is rebooted:
    $ chkconfig na-service on
* End Optional *

install dbcp (osgi:install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3)
install the BitmoneyService (install -s fab:file:/home/fedora/bitmoney-demo/jboss-fuse-bitmoney-demo/BitmoneyService/target/BitmoneyService-na-0.0.1-SNAPSHOT.jar)
type exit 

* Apac install
cd ~/bitmoney-demo/fuse/apac/jboss-fuse-6.0.0.redhat-024/deploy/
cp ~/Downloads/mysql-connector-java-5.1.25/mysql-connector-java-5.1.25-bin.jar .
cd ../etc
edit jre.properties
make the modifications described in "Modify jre.properties"
edit  org.apache.karaf.management.cfg 
change rmiRegistryPort to 1096
change rmieServerPort to 44446
edit system.properties
change org.osgi.service.http.port to 8186
change activemq.port activemq.port to 61616
change activemq.jmx.url = 1096
edit jetty.xml
change jetty.port to 8186
org.apache.karaf.features.cfg and edit the featuresBoot line
 featuresBoot=jasypt-encryption,config,management,fabric-boot-commands,fabric-bundle,fabric-maven-proxy,patch,activemq,mq-fabric,camel-xmlbeans,camel,camel-cxf,camel-jms,activemq-xbeans,activemq-camel,camel-blueprint,camel-csv,camel-ftp,camel-bindy,camel-jdbc,camel-exec,camel-jasypt,camel-saxon,camel-snmp,camel-ognl,camel-routebox,camel-script,camel-spring-javaconfig,camel-jaxb,camel-jetty,camel-jmx,camel-mail,camel-paxlogging,camel-rmi,camel-sql,war,camel-xmlbeans,camel-xmljson,camel-xmlsecurity

cd ../bin
start the fuse application ./fuse
type features:install wrapper
wrapper install -n apac -d apac-service -D apac-service
* Optional *
  To install the service:
    $ ln -s /home/fedora/bitmoney-demo/fuse/apac/jboss-fuse-6.0.0.redhat-024/bin/apac-service /etc/init.d/
    $ chkconfig apac-service --add

  To start the service when the machine is rebooted:
    $ chkconfig apac-service on
* End Optional *


cd ../bin
start the fuse application ./fuse
install dbcp (osgi:install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3)
install the BitmoneyService (install -s fab:file:/home/fedora/bitmoney-demo/jboss-fuse-bitmoney-demo/BitmoneyService/target/BitmoneyService-apac-0.0.1-SNAPSHOT.jar)
type exit 

* EMEA install
cd ~/bitmoney-demo/fuse/emea/jboss-fuse-6.0.0.redhat-024/deploy/
cp ~/Downloads/mysql-connector-java-5.1.25/mysql-connector-java-5.1.25-bin.jar .
cd ../etc
edit jre.properties
make the modifications described in "Modify jre.properties"
edit  org.apache.karaf.management.cfg 
change rmiRegistryPort to 1095
change rmieServerPort to 44445
edit system.properties
change org.osgi.service.http.port to 8185
change activemq.port activemq.port to 61615
change activemq.jmx.url = 1095
edit jetty.xml
change jetty.port to 8185
cd ../bin
start the fuse application ./fuse
install dbcp (osgi:install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3)
install the BitmoneyService (install -s fab:file:/home/fedora/bitmoney-demo/jboss-fuse-bitmoney-demo/BitmoneyService/target/BitmoneyService-emea-0.0.1-SNAPSHOT.jar)
org.apache.karaf.features.cfg and edit the featuresBoot line
 featuresBoot=jasypt-encryption,config,management,fabric-boot-commands,fabric-bundle,fabric-maven-proxy,patch,activemq,mq-fabric,camel-xmlbeans,camel,camel-cxf,camel-jms,activemq-xbeans,activemq-camel,camel-blueprint,camel-csv,camel-ftp,camel-bindy,camel-jdbc,camel-exec,camel-jasypt,camel-saxon,camel-snmp,camel-ognl,camel-routebox,camel-script,camel-spring-javaconfig,camel-jaxb,camel-jetty,camel-jmx,camel-mail,camel-paxlogging,camel-rmi,camel-sql,war,camel-xmlbeans,camel-xmljson,camel-xmlsecurity

cd ../bin
start the fuse application ./fuse
type features:install wrapper
wrapper install -n emea -d emea-service -D emea-service
* Optional *
  To install the service:
    $ ln -s /home/fedora/bitmoney-demo/fuse/emea/jboss-fuse-6.0.0.redhat-024/bin/emea-service /etc/init.d/
    $ chkconfig emea-service --add

  To start the service when the machine is rebooted:
    $ chkconfig emea-service on
* End Optional *
type exit 

* LTAM install
cd ~/bitmoney-demo/fuse/ltam/jboss-fuse-6.0.0.redhat-024/deploy/
cp ~/Downloads/mysql-connector-java-5.1.25/mysql-connector-java-5.1.25-bin.jar .
cd ../etc
edit jre.properties
make the modifications described in "Modify jre.properties"
edit  org.apache.karaf.management.cfg 
change rmiRegistryPort to 1094
change rmieServerPort to 44444
edit system.properties
change org.osgi.service.http.port to 8184
change activemq.port activemq.port to 61614
change activemq.jmx.url = 1094
edit jetty.xml
change jetty.port to 8184
cd ../bin
start the fuse application ./fuse
install dbcp (osgi:install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3)
install the BitmoneyService (install -s fab:file:/home/fedora/bitmoney-demo/jboss-fuse-bitmoney-demo/BitmoneyService/target/BitmoneyService-ltam-0.0.1-SNAPSHOT.jar)
org.apache.karaf.features.cfg and edit the featuresBoot line
 featuresBoot=jasypt-encryption,config,management,fabric-boot-commands,fabric-bundle,fabric-maven-proxy,patch,activemq,mq-fabric,camel-xmlbeans,camel,camel-cxf,camel-jms,activemq-xbeans,activemq-camel,camel-blueprint,camel-csv,camel-ftp,camel-bindy,camel-jdbc,camel-exec,camel-jasypt,camel-saxon,camel-snmp,camel-ognl,camel-routebox,camel-script,camel-spring-javaconfig,camel-jaxb,camel-jetty,camel-jmx,camel-mail,camel-paxlogging,camel-rmi,camel-sql,war,camel-xmlbeans,camel-xmljson,camel-xmlsecurity

cd ../bin
start the fuse application ./fuse
type features:install wrapper
wrapper install -n ltam -d ltam-service -D ltam-service
* Optional *
  To install the service:
    $ ln -s /home/fedora/bitmoney-demo/fuse/ltam/jboss-fuse-6.0.0.redhat-024/bin/ltam-service /etc/init.d/
    $ chkconfig ltam-service --add

  To start the service when the machine is rebooted:
    $ chkconfig ltam-service on
* End Optional *
type exit 


**Modify jre.properties**
Add to the beginning of the jre-1.6 section:
javax.ejb, \
 javax.jmdns, \
 javax.swing, \
 javax.validation, \
 javax.validation.constraints, \
 javax.validation.groups, \
 javax.validation.metadata, \
 javax.persistence.spi;version="1.0.1.Final", \
  javax.persistence;version="1.0.1.Final";-split-package:=merge-first, \
  javax.security.*;-split-package:=merge-first, \
  javax.persistence.criteria;version="1.0.1.Final";-split-package:=merge-last, \
  javax.persistence.metamodel;version="1.0.1.Final";-split-package:=merge-last, \
  javax.security.jacc, \
 javax.activation;version="1.1", \
 javax.activity, \
 javax.annotation;version="1.1", \
 javax.annotation.processing;version="1.1", \
 javax.ejb;version="3.0", \
 javax.ejb.spi;version="3.0", \
 javax.interceptor;version="3.0", \

**Database Setup**
If MySQL is not installed
1.  sudo yum install mysql
2.  sudo yum install mysql-server
3.  sudo systemctl start mysqld.service
4.  sudo enable mysqld.service
5.  cd /home/fedora/bitmoney-demo/jboss-fuse-bitmoney-demo/BitmoneyService/sql
6.  mysql -u root < bitmoney.sql

**Start/Stop Bitcoin Services**
1. cd /home/fedora/bitmoney-demo/jboss-fuse-bitmoney-demo/BitmoneyService
start services
bash startService.sh
stop service
bash stopService.sh
