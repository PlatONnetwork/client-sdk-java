# Overview
> Java SDK is a Java development kit for PlatON public chain provided by PlatON for Java developers.

# Build
```
    git clone https://github.com/PlatONnetwork/client-sdk-java.git
    cd client-sdk-java/
    ./gradlew clean jar            //Generate jar package
	./gradlew clean distZip        //Generate code generation skeleton tool
   
``` 

# Use

* config maven repository:  http://sdk.platon.network/content/groups/public/
* config maven or gradle in project

```
<dependency>
    <groupId>com.platon.client</groupId>
    <artifactId>core</artifactId>
    <version>0.3.0</version>
</dependency>
```

or

```
compile "com.platon.client:core:0.3.0"
```

* use in project

```
Web3j web3 = Web3j.build(new HttpService("https://host:port"));
```


# Other
[more reference wiki](https://github.com/PlatONnetwork/wiki/wiki)