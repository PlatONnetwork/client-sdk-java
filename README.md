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
    <groupId>com.platon.sdk</groupId>
    <artifactId>core</artifactId>
    <version>1.3.0.2</version>
</dependency>
```

or

```
compile "com.platon.sdk:core:1.3.0.2"
```

* use in project

1. SDK includes PlatON network already. User can initialize custom networks, the latest is the current network.
```java
NetworkParameters.init(2000L, "ABC");  
```

chain ID 201018 is reserved for Alaya, and chain ID 210425 is reserved for PlatON.

2. user can switch current network if multi-networks have been initialized.
```java
NetworkParameters.selectNetwork(2000L, "ABC");  
```
3. In Alaya, the gas price for submitting a proposal is lower than in PlatON. When initializing a network with the parameter chainId：201018 or hrp:atp, this network will be considered as an Alaya network.
