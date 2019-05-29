#spring-boot-starter-soapui

### Requirements
------------
**[SoapUI](https://github.com/SmartBear/soapui) must be installed on your Maven Repository.**

### Maven Dependency

``` xml
<dependency>
	<groupId>com.github.vindell</groupId>
	<artifactId>spring-boot-starter-soapui</artifactId>
	<version>${project.version}</version>
</dependency>
```

### Usage

####Step 1 ：Download soapui source

> git clone https://github.com/SmartBear/soapui.git

####Step 2 ：Modify soapui\soapui\pom.xml and replace XML Beans

```xml
<!-- https://mvnrepository.com/artifact/org.apache.xmlbeans/xmlbeans -->
<dependency>
	<groupId>org.apache.xmlbeans</groupId>
	<artifactId>xmlbeans</artifactId>
	<version>2.6.0</version>
</dependency>
<dependency>
	<groupId>org.apache.xmlbeans</groupId>
	<artifactId>xmlbeans-xpath</artifactId>
	<version>2.6.0</version>
</dependency>
<dependency>
	<groupId>org.apache.xmlbeans</groupId>
	<artifactId>xmlbeans-xmlpublic</artifactId>
	<version>2.6.0</version>
</dependency>
```

####Step 3 ：Install To Maven Repository

> mvn clean install

####Step 4 ：Deploy To Nexus Maven Repository

> mvn deploy:deploy-file -DgroupId=com.eviware -DartifactId=soapui -Dversion=5.4.0 -Dpackaging=jar -Dfile=D:\soapui-5.4.0.jar -Durl=${repositoryUrl} -DrepositoryId=${repositoryId}

com.eviware.soapui.impl.WsdlInterfaceFactory
com.eviware.soapui.impl.wsdl.support.wsdl.CachedWsdlLoader
com.eviware.soapui.impl.wsdl.support.wsdl.UrlWsdlLoader
com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter
com.eviware.soapui.impl.wsdl.support.wsdl.WsdlUtils
com.eviware.soapui.impl.wsdl.support.soap.SoapUtils

com.eviware.soapui.support.xml.XmlUtils
com.eviware.soapui.support.JsonUtil
com.eviware.soapui.support.SecurityScanUtil

com.eviware.soapui.support.scripting.SoapUIScriptEngineRegistry

