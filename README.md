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
6. search for "android m2e".
Click the Install button next to the Android Connector for Maven that appears and follow the path through the wizard dialog to install the plug-in and its dependencies (including the Android Development Toolkit and the Maven for Eclipse m2e plug-in). Accept the terms-and-conditions and click Finish.
Once you restart your Eclipse workspace you should be ready to start using the Android Connector.

**Import project**
1.  In Eclipse select Import
2.  Select existing maven project
3.  Navigate to the jboss-fuse-bitmoney-demo directory
4.  Import the BitMoneyService and bitmoney projects
5.  In eclipse right click on the bitmoney project
6.  Select run as android application
7.  select your emulator
8.  click ok


## Installation of Jboss Fuse
1.  create four directories apac, na, emea, ltma
2.  unzip a copy of jboss fuse in each directory
3.  start each instance by running bin/fuse
4.  Install dbcp bundle (osgi:install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.commons-dbcp/1.4_3)
5.  Quit fuse by hitting ^d or running osgi:shutdown
6.  copy mysql-connector-java-5.1.13-bin.jar to the deploy directory
7.  Modify etc/jre.properties with information below for the appropriate JRE.  java -version will give you this
8.  Modify org.apache.karaf.features.cfg and edit the featuresBoot line
 featuresBoot=jasypt-encryption,config,management,fabric-boot-commands,fabric-bundle,fabric-maven-proxy,patch,activemq,mq-fabric,camel-xmlbeans,camel,camel-cxf,camel-jms,activemq-xbeans,activemq-camel,camel-blueprint,camel-csv,camel-ftp,camel-bindy,camel-jdbc,camel-exec,camel-jasypt,camel-saxon,camel-snmp,camel-ognl,camel-routebox,camel-script,camel-spring-javaconfig,camel-jaxb,camel-jetty,camel-jmx,camel-mail,camel-paxlogging,camel-rmi,camel-sql,war,camel-xmlbeans,camel-xmljson,camel-xmlsecurity
9.  Repeat these steps for each instance.  Make sure to modify the ports in each instance to match the ports in the region file in token/filters/<region>
10.  mvn -P<env> initialize org.apache.felix:maven-bundle-plugin:bundle install 
ex. mvn -Pltam clean org.apache.felix:maven-bundle-plugin:bundle install 
11.  Modify datasource.xml to match the username and password for your mysql instance
12.  run the bitmoney.sql file to create a default database
13.  install your fab bundle:  install -s fab:file:/home/jamie/BitmoneyService-apac-0.0.1-SNAPSHOT.jar




**Setting up each instance as a service**
https://access.redhat.com/site/documentation/en-US/JBoss_Fuse/6.0/html-single/Configuring_and_Running_JBoss_Fuse/index.html#AMQAdminServiceGenTbl010

**Modify jre.properties**
Add:
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


**configure multiple instances of fuse.**
1. edit org.apache.karaf.management.cfg 

2.  Assign new rmiRegistryPort and rmiServerPort

3.  edit system.properties change org.osgi.service.http.port and activemq.port

4.  edit jetty.xml


**Install Eclipse Pluging**
1. Start Eclipse, then select Help > Install New Software.
2. Click Add, in the top-right corner.
3. In the Add Repository dialog that appears, enter "ADT Plugin" for the Name and the following URL for the Location:
https://dl-ssl.google.com/android/eclipse/
4. Click OK.
5. If you have trouble acquiring the plugin, try using "http" in the Location URL, instead of "https" (https is preferred for security reasons).
6. In the Available Software dialog, select the checkbox next to Developer Tools and click Next.
7. In the next window, you'll see a list of the tools to be downloaded. Click Next.
8. Read and accept the license agreements, then click Finish.
9. If you get a security warning saying that the authenticity or validity of the software can't be established, click OK.
10. When the installation completes, restart Eclipse.
11. Configure the ADT Plugin
12. Once Eclipse restarts, you must specify the location of your Android SDK directory:

13. In the "Welcome to Android Development" window that appears, select Use existing SDKs.
14. Browse and select the location of the Android SDK directory you recently downloaded and unpacked.
15. Click Next.



# Android Configuration
1.  Start an android emulator, or plug in a device
2.  run the following command mvn clean android:deploy
