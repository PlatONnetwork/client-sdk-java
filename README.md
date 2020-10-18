# Overview
> Java SDK is a Java development kit for PlatON public chain provided by PlatON for Java developers.

# Build
```
    git clone https://github.com/PlatONnetwork/client-sdk-java.git
    cd client-sdk-java/
    ./gradlew clean jar            //Generate jar package
	./gradlew clean distZip        //Generate code generation skeleton tool
    ./gradlew -Pintegration-tests=true :integration-tests:test    //To run the integration tests:
   
``` 

# Use

* config maven repository:  https://sdk.platon.network/nexus/content/groups/public/
* config maven or gradle in project

```
<dependency>
    <groupId>com.alaya.client</groupId>
    <artifactId>alaya-core</artifactId>
    <version>0.13.2.1</version>
</dependency>
```

or

```
compile "com.alaya.client:alaya-core:0.13.2.1"
```

* use in project

```
Web3j web3 = Web3j.build(new HttpService("https://host:port"));
```