fuse-jboss-bitmoney-demo
========================

# Welcome to the fuse-jboss-bitmoney-demo wiki!

## Required Software:
1.  jboss fuse version 6.0.0 http://www.jboss.org/products/fuse
2.  jboss fuse IDE http://www.jboss.org/products/fuse
3.  Android Development Toolkit
4.  m2e for android
5.  Maven version 3.+

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
10.  compile mvn -P<region> clean org.apache.felix:maven-bundle-plugin:bundle install 
ex. mvn -Pltam clean org.apache.felix:maven-bundle-plugin:bundle install 
11.  Modify datasource.xml to match the username and password for your mysql instance
12.  run the bitmoney.sql file to create a default database
13.  install your fab bundle:  osgi:install -s fab:mvn:org.zeroglitch.bitmoney/BitmoneyService

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

# Android Configuration
1.  Start an android emulator, or plug in a device
2.  run the following command mvn clean android:deploy
