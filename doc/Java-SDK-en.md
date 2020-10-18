---
id: Java_SDK
title: Java SDK
sidebar_label: Java SDK
---


## Import Development Library

Depending on the build tool, use the following methods to add related dependencies to your project：

- Use requirements above jdk1.8.

### maven

> Project configuration:
```xml
<repository>
	<id>platon-public</id>
	<url>https://sdk.platon.network/nexus/content/groups/public/</url>
</repository>
```

> maven reference:
```xml
<dependency>
    <groupId>com.alaya.client</groupId>
    <artifactId>alaya-core</artifactId>
    <version>0.13.2.1</version>
</dependency>
```

### gradle

> Project configuration:
```
repositories {
	maven { url "https://sdk.platon.network/nexus/content/groups/public/" }
}
```

> gradle way of reference:
```
compile "com.alaya.client:alaya-core:0.13.2.1"
```

## Basic API Usage

### Bech32 Address

* **0x address to bech32 address**
```java
String hex = "0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818";
String bech32Address = Bech32.addressEncode(NetworkParameters.TestNetParams.getHrp(), hex);
assertThat(bech32Address, is("atx1f7wp58h65lvphgw2hurl9sa943w0f7qcdcev89"));

bech32Address = Bech32.addressEncode(NetworkParameters.MainNetParams.getHrp(), hex);
assertThat(bech32Address, is("atp1f7wp58h65lvphgw2hurl9sa943w0f7qc879x50"));
```

* **bech32 address to 0x address**
```java
String bech32Address = "atx1f7wp58h65lvphgw2hurl9sa943w0f7qcdcev89";
String hex =  Bech32.addressDecodeHex(bech32Address);
assertThat(hex, is("0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818"));
```

### NetworkParameters

* **SetCurrentNetworkParameters**

> Because bech 32 format address support is added, in order to be compatible with the old API, the function of setting the network parameters globally is added, and the old API outputs the corresponding format address according to the current network parameters

```java
NetworkParameters.setCurrentNetwork(101L);  // default mainnet 201018L
```

### Wallet Related

* **Generate PlatON StandardWallet n=16384 p=1 r=8**
```java
String fileName = WalletUtils.generatePlatONWalletFile(PASSWORD, tempDir);
```

* **Generate StandardWallet n=262144 p=1 r=8**
```java
String fileName = WalletUtils.generateNewWalletFile(PASSWORD, tempDir);
```

* **Generate LightWallet n=4096 p=6 r=8**
```java
String fileName = WalletUtils.generateLightNewWalletFile(PASSWORD, tempDir);
```

* **LoadWallet**
```java
Credentials credentials = WalletUtils.loadCredentials(PASSWORD, new File(tempDir, fileName));
```

### Credentials Related
* **loadCredentials from keystore**
```java
Credentials credentials = WalletUtils.loadCredentials(PASSWORD, new File(tempDir, fileName));
```

* **loadCredentials from key**
```java
Credentials credentials = Credentials.create("0xXXXXXXXXXXXXXX...");
```

* **getBech32AddressFromChainId**
```java
long chainId = 100L;
String bech32Address = credentials.getAddress(chainId);
```

* **getBech32AddressFromNetworkParameters**
```java
String bech32Address = credentials.getAddress();  // NetworkParameters.CurrentNetwork
```

## Basic RPC Interface

The basic `API` includes network, transaction, query, node information, economic model parameter configuration and other related interfaces. For details, refer to the following` API` instructions.

### web3ClientVersion

> Returns the current client version

- **parameters**

  no

- **return value**

```java
Request<?, Web3ClientVersion>
```

The string in the Web3ClientVersion property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, Web3ClientVersion> request = currentValidWeb3j.web3ClientVersion();
String version = request.send(). GetWeb3ClientVersion();
```

### web3Sha3

> Keccak-256 returning the given data

- **parameters**

  String: The data before encryption

- **return value**

```java
Request<?, Web3Sha3>
```

The string in the Web3Sha3 attribute is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String date = "";
Request <?, Web3Sha3> request = currentValidWeb3j.web3Sha3(date);
String resDate = request.send().getWeb3ClientVersion();
```

### netVersion

> Returns the current network ID

- **parameters**

  no

- **return value**

```java
Request<?, NetVersion>
```

The string in the NetVersion attribute is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, NetVersion> request = currentValidWeb3j.netVersion();
String version = request.send().getNetVersion();
```

### netListening

Return true if the client is actively listening for a network connection

- **parameters**

  no

- **return value**

```java
Request<?, NetListening>
```

The boolean in the NetListening property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, NetListening> request = currentValidWeb3j.netListening();
boolean req = request.send().isListening();
```

### netPeerCount

> Returns the number of peers currently connected to the client

- **parameters**

  None

- **return value**

```java
Request<?, NetPeerCount>
```

The BigInteger in the NetPeerCount property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, NetPeerCount> request = currentValidWeb3j.netPeerCount();
BigInteger req = request.send().getQuantity();
```

### platonProtocolVersion

> Returns the current platon protocol version

- **parameters**

  no

- **return value**

```java
Request<?, PlatonProtocolVersion>
```

The String in the PlatonProtocolVersion property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonProtocolVersion> request = currentValidWeb3j.platonProtocolVersion();
String req = request.send().getProtocolVersion();
```

### PlatonSyncing

Return an object containing data about the synchronization status or false

- **parameters**

  no

- **return value**

```java
Request<?, PlatonSyncing>
```

The String in the PlatonSyncing property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonSyncing> request = currentValidWeb3j.platonSyncing();
boolean req = request.send().isSyncing();
```

### platonGasPrice

> Return current gas price

- **parameters**

  no

- **return value**

```java
Request<?, PlatonGasPrice>
```

The BigInteger in the PlatonGasPrice property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonGasPrice> request = currentValidWeb3j.platonGasPrice();
BigInteger req = request.send().getGasPrice();
```

### platonAccounts

> Return the list of addresses owned by the client

- **parameters**

  no

- **return value**

```java
Request<?, PlatonAccounts>
```

The String array in the PlatonAccounts property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonAccounts> request = currentValidWeb3j.platonAccounts();
List<String> req = request.send().getAccounts();
```

### platonBlockNumber

> Returns the current highest block height

- **parameters**

  no

- **return value**

```java
Request<?, PlatonBlockNumber>
```

The BigInteger in the PlatonBlockNumber property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonBlockNumber> request = currentValidWeb3j.platonBlockNumber();
BigInteger req = request.send().getBlockNumber();
```

### platonGetBalance

> Back to query address balance

- **parameters**
  - String: address The address to query
  - DefaultBlockParameter:
    - DefaultBlockParameterName.LATEST latest block height(default)
    - DefaultBlockParameterName.EARLIEST minimum block height
    - DefaultBlockParameterName.PENDING unpackaged transaction
    - DefaultBlockParameter.valueOf(BigInteger blockNumber)

- **return value**

```java
Request<?, PlatonGetBalance>
```

The BigInteger in the PlatonGetBalance property is the corresponding stored data

- **Example**

```java
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String address = "";
Request<?, PlatonGetBalance> request = web3j.platonGetBalance(address,DefaultBlockParameterName.LATEST);
BigInteger req = request.send().getBalance();
```

### platonGetStorageAt

Return value from storage location of given address

- **parameters**
  - String: address
  - BigInteger: integer for position in position memory
  - DefaultBlockParameter:
    - DefaultBlockParameterName.LATEST latest block height(default)
    - DefaultBlockParameterName.EARLIEST minimum block height
    - DefaultBlockParameterName.PENDING unpackaged transaction
    - DefaultBlockParameter.valueOf(BigInteger blockNumber)
-
 **return value**

```java
Request<?, PlatonGetStorageAt>
```

The String in the PlatonGetStorageAt property is the corresponding storage data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
Request <?, PlatonGetStorageAt> request = currentValidWeb3j.platonGetStorageAt(address ,BigInteger.ZERO,DefaultBlockParameterName.LATEST );
String req = request.send().getData();
```

### platonGetBlockTransactionCountByHash

> Query the number of transactions in a block according to the block hash

- **parameters**
  - String: blockHash block hash

- **return value**

```java
Request<?, PlatonGetBlockTransactionCountByHash>
```

The BigInteger in the PlatonGetBlockTransactionCountByHash property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockhash = "";
Request <?, PlatonGetBlockTransactionCountByHash> request = currentValidWeb3j.platonGetBlockTransactionCountByHash(blockhash);
BigInteger req = request.send().getTransactionCount();
```

### platonGetTransactionCount

> Query the number of transactions sent by the address according to the address

- **parameters**
  - String: address
  - DefaultBlockParameter:
    - DefaultBlockParameterName.LATEST latest block height(default)
    - DefaultBlockParameterName.EARLIEST minimum block height
    - DefaultBlockParameterName.PENDING unpackaged transaction
    - DefaultBlockParameter.valueOf(BigInteger blockNumber)

- **return value**

```java
Request<?, PlatonGetTransactionCount>
```

The BigInteger in the PlatonGetTransactionCount property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
Request<?, PlatonGetTransactionCount> request = currentValidWeb3j.platonGetTransactionCount(address, DefaultBlockParameterName.LATEST);
BigInteger req = request.send(). GetTransactionCount();
```

### platonGetBlockTransactionCountByNumber

> Returns the total number of transactions in block high school based on block height

- **parameters**
  - DefaultBlockParameter:
    - DefaultBlockParameterName.LATEST latest block height(default)
    - DefaultBlockParameterName.EARLIEST minimum block height
    - DefaultBlockParameterName.PENDING unpackaged transaction
    - DefaultBlockParameter.valueOf(BigInteger blockNumber)

- **return value**

```java
Request<?, PlatonGetBlockTransactionCountByNumber>
```

The BigInteger in the PlatonGetBlockTransactionCountByNumber property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonGetBlockTransactionCountByNumber> request = currentValidWeb3j.platonGetBlockTransactionCountByNumber(DefaultBlockParameterName.LATEST);
BigInteger req = request.send().getTransactionCount();
```

### platonGetCode

> Return code for given address

- **parameters**
  - String: address

  - DefaultBlockParameter:
    - DefaultBlockParameterName.LATEST latest block height(default)
    - DefaultBlockParameterName.EARLIEST minimum block height
    - DefaultBlockParameterName.PENDING unpackaged transaction
    - DefaultBlockParameter.valueOf(BigInteger blockNumber)

- **return value**

```java
Request<?, PlatonGetCode>
```

The String in the PlatonGetCode property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
Request <?, PlatonGetCode> request = currentValidWeb3j.platonGetCode(address,DefaultBlockParameterName.LATEST);
String req = request.send().getCode();
```

### platonSign

> Data Signature

- **parameters**
  - String: address
  - String: sha3HashOfDataToSign data to be signed

- **return value**

```java
Request<?, PlatonSign>
```

The String in the PlatonSign property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String address = "";
String sha3HashOfDataToSign   = "";
Request <?, PlatonSign> request = currentValidWeb3j.platonSign(address,DefaultBlockParameterName.LATEST);
String req = request.send().getSignature();
```

Note: The address must be unlocked in advance

### platonSendTransaction

> Send service signing transaction

- **parameters**
  - Transaction: Transaction: transaction structure
    - String: from: transaction sending address
    - String: to: address of transaction receiver
    - BigInteger: gas: maximum gas usage for this transaction
    - BigInteger: gasPrice: gas price
    - BigInteger: value: transfer amount
    - String: data: data on the chain
    - BigInteger: nonce: transaction unique identifier
      - Call platonGetTransactionCount, get the from address as a parameter, and get the total number of sent transactions to that address
      - Each use of the address nonce +1

- **return value**

```java
Request<?, PlatonSendTransaction>
```

The String in the PlatonSendTransaction property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Transaction transaction = new Transaction("from","to",BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO,"data ",BigInteger.ONE);
Request <?, PlatonSendTransaction> request = currentValidWeb3j.platonSendTransaction(transaction);
String req = request.send().getTransactionHash();
```

### platonSendRawTransaction

> Send transaction

- **parameters**
  - String: data: wallet signed data

- **return value**

```java
Request<?, PlatonSendTransaction>
```

The String in the PlatonSendTransaction property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String  data = "";
Request <?, PlatonSendTransaction> request = currentValidWeb3j.platonSendRawTransaction(data);
String req = request.send().getTransactionHash();
```

### platonCall

> Execute a message call transaction, the message call transaction is executed directly in the node 旳 VM without the need to execute through blockchain mining

- **parameters**
  - Transaction: Transaction: transaction structure
    - String: from: transaction sending address
    - String: to: address of transaction receiver
    - BigInteger: gas: maximum gas usage for this transaction
    - BigInteger: gasPrice: gas price
    - BigInteger: value: transfer amount
    - String: data: data on the chain
    - BigInteger: nonce: transaction unique identifier
      - Call platonGetTransactionCount, get the from address as a parameter, and get the total number of sent transactions to that address
      - Each use of the address nonce +1

- **return value**

```java
Request<?, PlatonCall>
```

The String in the PlatonCall property is the corresponding stored data

- **Example**

```javas
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Transaction transaction = new Transaction("from","to",BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO,"data ",BigInteger.ONE);
Request <?, PlatonSendTransaction> request = currentValidWeb3j.platonCall(transaction);
String req = request.send().getValue();
```

### platonEstimateGas

> Estimating contract method gas usage

- **parameters**
  - Transaction: Transaction: transaction structure
    - String: from: transaction sending address
    - String: to: address of transaction receiver
    - BigInteger: gas: maximum gas usage for this transaction
    - BigInteger: gasPrice: gas price
    - BigInteger: value: transfer amount
    - String: data: data on the chain
    - BigInteger: nonce: transaction unique identifier
      - Call platonGetTransactionCount, get the from address as a parameter, and get the total number of sent transactions to that address
      - Each use of the address nonce +1

- **return value**

```java
Request<?, PlatonEstimateGas>
```

The BigInteger in the PlatonEstimateGas property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Transaction transaction = new Transaction("from","to",BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO,"data ",BigInteger.ONE);
Request <?, PlatonEstimateGas> request = currentValidWeb3j.platonEstimateGas(transaction);
BigInteger req = request.send().getAmountUsed();
```

### platonGetBlockByHash

> Query block information based on block hash

- **parameters**
  - String: blockHash block hash
  - boolean:
    - true: complete transaction list in block
    - false: only transaction hash list in block

- **return value**

```java
Request<?, PlatonBlock>
```

The block in the PlatonBlock property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash  = "";

Request <?, PlatonBlock> request = currentValidWeb3j.platonGetBlockByHash(blockHash,true);
Block req = request.send().getBlock();
```

### platonGetBlockByNumber

> Query block information based on block height

- **parameters**
  - DefaultBlockParameter:
    - DefaultBlockParameterName.LATEST latest block height(default)
    - DefaultBlockParameterName.EARLIEST minimum block height
    - DefaultBlockParameterName.PENDING unpackaged transaction
    - DefaultBlockParameter.valueOf(BigInteger blockNumber)
  - boolean:
    - true: complete transaction list in block
    - false: only transaction hash list in block

- **return value**

```java
Request<?, PlatonBlock>
```

The block in the PlatonBlock property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonBlock> request = currentValidWeb3j.platonGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.ZERO) ,true);
Block req = request.send().getBlock();
```

### platonGetTransactionByBlockHashAndIndex

> Query the transaction with the specified serial number in the block according to the block hash

- **parameters**
  - String: blockHash block hash
  - BigInteger: transactionIndex number of the transaction in the block

- **return value**

```java
Request<?, PlatonTransaction>
```

The transaction in the PlatonTransaction property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash    = "";
Request <?, PlatonTransaction> request = currentValidWeb3j.platonGetTransactionByHash(blockHash,BigInteger.ZERO);
Optional<Transaction> req = request.send().getTransaction();
```

### platonGetTransactionByBlockNumberAndIndex

> Query the transaction with the specified serial number in the block according to the block height

- **parameters**
  - DefaultBlockParameter:
    - DefaultBlockParameterName.LATEST latest block height(default)
    - DefaultBlockParameterName.EARLIEST minimum block height
    - DefaultBlockParameterName.PENDING unpackaged transaction
    - DefaultBlockParameter.valueOf(BigInteger blockNumber)
  - BigInteger: transactionIndex number of the transaction in the block

- **return value**

```java
Request<?, PlatonTransaction>
```

The transaction in the PlatonTransaction property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash = "";
Request<?, PlatonTransaction> request = currentValidWeb3j.platonGetTransactionByHash(DefaultBlockParameter.valueOf(BigInteger.ZERO), BigInteger.ZERO);
Optional<Transaction> req = request.send(). GetTransaction();
```

### platonGetTransactionReceipt

> Query transaction receipt based on transaction hash

- **parameters**
  - String: transactionHash

- **return value**

```java
Request<?, PlatonGetTransactionReceipt>
```

The transaction in the PlatonGetTransactionReceipt property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String blockHash = "";
Request<?, PlatonGetTransactionReceipt> request = currentValidWeb3j.platonGetTransactionReceipt(DefaultBlockParameter.valueOf(BigInteger.ZERO), BigInteger.ZERO);
Optional<TransactionReceipt> req = request.send(). GetTransactionReceipt();
```

### platonNewFilter

Create a filter to notify when the client receives a matching whisper message

- **parameters**
  - PlatonFilter: PlatonFilter:
    - SingleTopic:

- **return value**

```java
Request<?, PlatonFilter>
```

The BigInteger in the PlatonFilter property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
PlatonFilter filter = new PlatonFilter();
filter.addSingleTopic("");
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewFilter(filter);
BigInteger req = request.send().getFilterId();
```

### platonNewBlockFilter

> Create a filter in the node to be notified when new blocks are generated. To check if the status changes

- **parameters**

  no

- **return value**

```java
Request<?, PlatonFilter>
```

The BigInteger in the PlatonFilter property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewBlockFilter();
BigInteger req = request.send().getFilterId();
```

### platonNewPendingTransactionFilter

> Create a filter in the node to be notified when a pending transaction occurs. To check if the status has changed

- **parameters**

  no

- **return value**

```java
Request<?, PlatonFilter>
```

The BigInteger in the PlatonFilter property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewPendingTransactionFilter();
BigInteger req = request.send().getFilterId();
```

### platonNewPendingTransactionFilter

> Write in filter with specified number. This call is always needed when listening is no longer needed

- **parameters**

  no

- **return value**

```java
Request<?, PlatonFilter>
```

The BigInteger in the PlatonFilter property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonFilter> request = currentValidWeb3j.platonNewPendingTransactionFilter();
BigInteger req = request.send().getFilterId();
```

### platonUninstallFilter

> Write in filter with specified number. This call is always needed when listening is no longer needed

- **parameters**
  - BigInteger: filterId: filter ID

- **return value**

```java
Request<?, PlatonUninstallFilter>
```

The boolean in the PlatonUninstallFilter attribute is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonUninstallFilter> request = currentValidWeb3j.platonNewPendingTransactionFilter(BigInteger.ZERO);
boolean req = request.send().isUninstalled();
```

### platonGetFilterChanges

> Polling the specified filter and returning a newly generated log array since the last poll

- **parameters**
  - BigInteger: filterId: filter ID

- **return value**

```java
Request<?, PlatonLog>
```

The LogResult array in the PlatonLog property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonLog> request = currentValidWeb3j.platonGetFilterChanges(BigInteger.ZERO);
List<PlatonLog.LogResult> req = request.send().getLogs();
```

### platonGetFilterLogs

> Polling the specified filter and returning a newly generated log array since the last poll.

- **parameters**
  - BigInteger: filterId: filter ID

- **return value**

```java
Request<?, PlatonLog>
```

The LogResult array in the PlatonLog property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request <?, PlatonLog> request = currentValidWeb3j.platonGetFilterLogs(BigInteger.ZERO);
List<PlatonLog.LogResult> req = request.send().getLogs();
```

### platonGetLogs

> Return all logs in the specified filter

- **parameters**
  - PlatonFilter: PlatonFilter:
    - SingleTopic:

- **return value**

```java
Request<?, PlatonLog>
```

The BigInteger in the PlatonLog property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
PlatonFilter filter = new PlatonFilter();
filter.addSingleTopic("");
Request<?, PlatonLog> request = currentValidWeb3j.platonGetLogs(filter);
List<LogResult> = request.send(). GetLogs();
```

### platonPendingTransactions
> Query pending transactions

- **parameters**

  no

- **return value**

```java
Request<?, PlatonPendingTransactions>
```

The transactions in the PlatonPendingTransactions property are the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, PlatonPendingTransactions> req = currentValidWeb3j.platonPendingTx();
EthPendingTransactions res = req.send();
List<Transaction> transactions = res.getTransactions();
```

### dbPutString

> Store strings in local database.

- **parameters**
  - String: databaseName: database name
  - String: keyName: key name
  - String: stringToStore: the string to be stored

- **return value**

```java
Request<?, DbPutString>
```

The boolean in the DbPutString property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
String stringToStore;
Request <?, DbPutString> request = currentValidWeb3j.dbPutString(databaseName,keyName,stringToStore);
List<DbPutString> = request.send().valueStored();
```

### dbGetString

Read string from local database

- **parameters**
  - String: databaseName: database name
  - String: keyName: key name

- **return value**

```java
Request<?, DbGetString>
```

The String in the DbGetString property is the corresponding stored data

**Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
Request <?, DbGetString> request = currentValidWeb3j.dbGetString(databaseName,keyName);
String req  = request.send().getStoredValue();
```

### dbPutHex

> Write binary data to local database

- **parameters**
  - String: databaseName: database name
  - String: keyName: key name
  - String: dataToStore: binary data to be stored

- **return value**

```java
Request<?, DbPutHex>
```

The boolean in the DbPutHex attribute is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
String dataToStore;
Request<?, DbPutHex> request = currentValidWeb3j.dbPutHex(databaseName, keyName, dataToStore);
boolean req = request.send(). valueStored();
```

### dbGetHex

> Read binary data from local database

- **parameters**
  - String: databaseName: database name
  - String: keyName: key name

- **return value**

```java
Request<?, DbGetHex>
```

The String in the DbGetHex property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
String databaseName;
String keyName;
Request<?, DbGetHex> request = currentValidWeb3j.dbGetHex(databaseName, keyName);
String req = request.send(). GetStoredValue();
```

### platonEvidences

> Return double sign report data

- **parameters**

  no

- **Export parameters**

|Parameters | Type | Description |
|:------- |:------ |:---------- |
| jsonrpc | string | rpc version number |
| id | int | id serial number |
| result | string | Evidence String |

result is the evidence string, which contains 3 types of evidence: duplicatePrepare, duplicateVote, duplicateViewchange
Each type contains multiple evidences, so it is an array structure, and you need to pay attention when parsing.

- **duplicatePrepare**

```text
{
  "prepareA": {
    "epoch": 0,            //epoch value of consensus round
    "viewNumber": 0,       //View value of consensus round
    "blockHash": "0x06abdbaf7a0a5cb1deddf69de5b23d6bc3506fdadbdcfc32333a1220da1361ba",    //block hash
    "blockNumber": 16013,       //block number
    "blockIndex": 0,        //The index value of the block in a round view
    "blockData": "0xe1a507a57c1e9d8cade361fefa725d7a271869aea7fd923165c872e7c0c2b3f2",     //Block rlp encoding value
    "validateNode": {            
      "index": 0,     //The index value of the validator in a round of epoch
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",    //Verifier address
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",    //Verifier nodeID
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"    //Validator bls public key
    },
    "signature": "0x1afdf43596e07d0f5b59ae8f45d30d21a9c5ac793071bfb6382ae151081a901fd3215e0b9645040c9071d0be08eb200900000000000000000000000000000000"     //message signing
  },
  "prepareB": {
    "epoch": 0,
    "viewNumber": 0,
    "blockHash": "0x74e3744545e95f4defc82d731504a39994b8013575491f83f7520cf796347b8f",
    "blockNumber": 16013,
    "blockIndex": 0,
    "blockData": "0xb11be0a3634e29281403d690c1a0bc38e96ea34b3aea0b0da2883800f610c3b7",
    "validateNode": {
      "index": 0,
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0x16795379ca8e28953e74b23d1c384dda760579ad70c5e490225403664a8d4490cabb1dc64a2e0967b5f0c1e9dbd6578c00000000000000000000000000000000"
  }
}
```

- **duplicateVote**

```text 
{
  "voteA": {
    "epoch": 0,   //epoch value of consensus round
    "viewNumber": 0,    //View value of consensus round
    "blockHash": "0x58b5976a471f86c4bd198984827bd594dce6ac861ef15bbbb1555e7b2edc2fc9",   //block hash
    "blockNumber": 16013,   //block number
    "blockIndex": 0,    //The index value of the block in a round view
    "validateNode": { 
      "index": 0,    //The index value of the validator in a round of epoch
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",  //Verifier address
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",   //Verifier nodeID
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"    //Validator bls public key
    },
    "signature": "0x071350aed09f226e218715357ffb7523ba41271dd1d82d4dded451ee6509cd71f6888263b0b14bdfb33f88c04f76790d00000000000000000000000000000000"    //message signing
  },
  "voteB": {
    "epoch": 0,
    "viewNumber": 0,
    "blockHash": "0x422515ca50b9aa01c46dffee53f3bef0ef29884bfd014c3b6170c05d5cf67696",
    "blockNumber": 16013,
    "blockIndex": 0,
    "validateNode": {
      "index": 0,
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0x9bf6c01643058c0c828c35dc3277666edd087cb439c5f6a78ba065d619f812fb42c5ee881400a7a42dd8366bc0c5c88100000000000000000000000000000000"
  }
}
```

- **duplicateViewchange**

```text
{
  "viewA": {
    "epoch": 0,  
    "viewNumber": 0, 
    "blockHash": "0xb84a40bb954e579716e7a6b9021618f6b25cdb0e0dd3d8c2c0419fe835640f36",  //区块hash
    "blockNumber": 16013, 
    "validateNode": {
      "index": 0,  
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4", 
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0x9c8ba2654c6b8334b1b94d3b421c5901242973afcb9d87c4ab6d82c2aee8e212a08f2ae000c9203f05f414ca578cda9000000000000000000000000000000000",
    "blockEpoch": 0,
    "blockView": 0
  },
  "viewB": {
    "epoch": 0,
    "viewNumber": 0,
    "blockHash": "0x2a60ed6f04ccb9e468fbbfdda98b535653c42a16f1d7ccdfbd5d73ae1a2f4bf1",
    "blockNumber": 16013,
    "validateNode": {
      "index": 0,
      "address": "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4",
      "nodeId": "19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365",
      "blsPubKey": "f93a2381b4cbb719a83d80a4feb93663c7aa026c99f64704d6cc464ae1239d3486d0cf6e0b257ac02d5dd3f5b4389907e9d1d5b434d784bfd7b89e0822148c7f5b8e1d90057a5bbf4a0abf88bbb12902b32c94ca390a2e16eea8132bf8c2ed8f"
    },
    "signature": "0xed69663fb943ce0e0dd90df1b65e96514051e82df48b3867516cc7e505234b9ca707fe43651870d9141354a7a993e09000000000000000000000000000000000",
    "blockEpoch": 0,
    "blockView": 0
  }
}
```

- **return value**

```java
Request<?, PlatonEvidences>
```

The Evidences object in the PlatonEvidences property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, PlatonEvidences> req = currentValidWeb3j.platonEvidences();
Evidences evidences = req.send(). GetEvidences();
```

### getProgramVersion

> Get code version

- **parameters**

  no

- **return value**

```java
Request<?, AdminProgramVersion>
```

The ProgramVersion object in the AdminProgramVersion property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, AdminProgramVersion> req = currentValidWeb3j.getProgramVersion();
ProgramVersion programVersion = req.send(). GetAdminProgramVersion();
```

- **ProgramVersion Object Parsing**
  - BigInteger: version: code version
  - String: sign: code version signature

### getSchnorrNIZKProve

> Get proof of bls

- **parameters**

  no

- **return value**

```java
Request<?, AdminSchnorrNIZKProve>
```

The String in the AdminSchnorrNIZKProve property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, AdminProgramVersion> req = currentValidWeb3j.getSchnorrNIZKProve();
String res = req.send(). GetAdminSchnorrNIZKProve();
```

### getEconomicConfig

> Get PlatON parameter configuration

- **parameters**

  no

- **return value**

```java
Request<?, DebugEconomicConfig>
```

The String in the DebugEconomicConfig property is the corresponding stored data

- **Example**

```java
Web3j platonWeb3j = Web3j.build(new HttpService("http://127.0.0.1:6789"));
Request<?, DebugEconomicConfig> req = currentValidWeb3j.getEconomicConfig();
String debugEconomicConfig = req.send().getEconomicConfigStr();
```

## System Contract Call

System contracts mainly include economic model and governance related contracts：
* staking contract
* delegate contract
* reward contract
* node contract
* proposal contract
* slash contract
* restrictingPlan contract

For the introduction and use of the above system contract, please refer to the following contract interface description.


### Pledge Related Interface

> Interfaces related to pledge contracts in the PlatON economic model.

#### Loading Pledge Contract

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
String chainId = "100";
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
StakingContract contract = StakingContract.load(web3j, credentials, chainId);
```

#### Interface Description

##### **staking**

> Node candidate applies for pledge

- **Introduction**

  - String: nodeId node id, hexadecimal format
  - BigInteger: amount of von pledged, the pledged amount must be greater than or equal to 1,000,000 LAT
  - StakingAmountType: stakingAmountType, enumeration, FREE_AMOUNT_TYPE means use the free amount of the account, RESTRICTING_AMOUNT_TYPE means use the amount of the lock to make a pledge
  - String: benefitAddress revenue account
  - String: nodeName The name of the node being pledged
  - String: externalId External Id(the length of the Id described by the third-party pull node), currently the keybase account public key
  - String: The third-party homepage of the webSite node(the length is limited, indicating the homepage of the node)
  - String: description of the details node(there is a length limitation, indicating the description of the node)
  - ProgramVersion: the real version of the processVersion program, governing rpc acquisition
  - String: blsPubKey bls public key
  - String: Proof of blsProof bls
  - BigInteger：rewardPer   delegate of reward，1=0.01%   10000=100%

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
BigDecimal stakingAmount = Convert.toVon("1000000", Convert.Unit.ATP);
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
String benifitAddress = "atp1qtp5fqtmudzge9aqt9rnzgdxv729pdq560vrat";
String externalId = "";
String nodeName = "integration-node1";
String webSite = "https://www.platon.network/#/";
String details = "integration-node1-details";
String blsPubKey = "5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811";
BigInteger rewardPer = BigInteger.valueOf(1000L);

PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
        .setNodeId(nodeId)
        .setAmount(stakingAmount.toBigInteger())
        .setStakingAmountType(stakingAmountType)
        .setBenifitAddress(benifitAddress)
        .setExternalId(externalId)
        .setNodeName(nodeName)
        .setWebSite(webSite)
        .setDetails(details)
        .setBlsPubKey(blsPubKey)
        .setProcessVersion(web3j.getProgramVersion().send().getAdminProgramVersion())
        .setBlsProof(web3j.getSchnorrNIZKProve().send().getAdminSchnorrNIZKProve())
        .setRewardPer(rewardPer)
        .build()).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **unStaking**

> Node revocation pledge(initiate all revocations at one time, multiple accounts)

- **Introduction**

  - String: nodeId node id, hexadecimal format

- **return value**

```java
TransactionResponse
```

- BaseResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";

PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **updateStaking**

> Modify pledge information

- **Introduction**

  - String: nodeId node id, hexadecimal format, starting with 0x
  - String: externalId External Id(with a length limit, the ID described by the third-party pull node), currently the keybase account public key
  - String: benefitAddress revenue account
  - String: nodeName The name of the node being pledged
  - String: the third-party homepage of the webSite node
  - String: description of the details node(there is a length limitation, indicating the description of the node)
  - BigInteger：rewardPer   delegate of reward，1=0.01%   10000=100%

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
String benifitAddress = "atp1qtp5fqtmudzge9aqt9rnzgdxv729pdq560vrat";
String externalId = "";
String nodeName = "integration-node1-u";
String webSite = "https://www.platon.network/#/";
String details = "integration-node1-details-u";
BigInteger rewardPer = BigInteger.valueOf(1000L);

PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(new UpdateStakingParam.Builder()
        .setBenifitAddress(benifitAddress)
        .setExternalId(externalId)
        .setNodeId(nodeId)
        .setNodeName(nodeName)
        .setWebSite(webSite)
        .setDetails(details)
        .setRewardPer(rewardPer)
        .build()).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **addStaking**

> Increase pledge and increase pledged deposits of pledged nodes

- **Introduction**

  - String: nodeId node id, hexadecimal format, starting with 0x
  - StakingAmountType: stakingAmountType, enumeration, FREE_AMOUNT_TYPE means use the free amount of the account, RESTRICTING_AMOUNT_TYPE means use the amount of the lock to make a pledge
  - BigInteger: addStakingAmount

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - String: Data response data

* **Contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
BigDecimal addStakingAmount = Convert.toVon("4000000", Convert.Unit.ATP);

PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, stakingAmountType, addStakingAmount.toBigInteger()).send();
TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getStakingInfo**

> Query the pledge information of the current node

* **Introduction**

  - String: nodeId node id, hexadecimal format, starting with 0x

* **return value**

```java
CallResponse<Node> baseRespons
```

- CallResponse<Node> description
  -  int: Code result flag, 0 is success
  -  Node: data Node object data
  -  String: ErrMsg error message, exists on failure

- **Node**: object that holds the current node pledge information

  - String: BenefitAddress is used to accept the block reward and pledged reward income account

  - String: Details The description of the Details node(the length is limited, indicating the description of the node)

  - String: NodeId The node Id of the pledge(also called the candidate's node Id)

  - String: NodeName The name of the node being pledged(the length is limited, indicating the name of the node)

  - BigInteger: ProgramVersion The real version number of the PlatON process of the pledged node(the interface for obtaining the version number is provided by the governance)

  - BigInteger: Released von who initiated a free amount locked period pledged account

  - BigInteger: ReleasedHes initiated the free amount of the hesitation period of the pledged account

  - BigInteger: RestrictingPlan initiates the lock-up period of the locked account amount of the pledged account.

  - BigInteger: RestrictingPlanHes initiated the hedging period of the locked amount of the pledged account

  - BigInteger: Shares the current candidate's total pledge plus the number of entrusted VON

  - String: StakingAddress The account used when initiating the pledge(when the pledge is cancelled, von will be returned to the account or the account's lock information)

  - BigInteger: block height when StakingBlockNum initiated pledge

  - BigInteger: StakingEpoch's current settlement cycle when the pledge amount is changed

  - BigInteger: StakingTxIndex transaction index when pledge is initiated

  - BigInteger: Status of the status candidate, 0: node is available, 1: node is unavailable, 2: node block rate is low but the removal condition is not met,4: The node's von is insufficient to the minimum pledge threshold(only the penultimate bit is 1), 8: the node is reported to be double signed, 16: the node block rate is low and the removal condition is reached(the penultimate bit is 1); : Node initiates cancellation

  - BigInteger: ValidatorTerm

  - String: Website The third-party homepage of the Website node(the length of the node is the homepage of the node)

  - BigInteger：delegateEpoch  The node's last delegate settlement cycle
  
  - BigInteger：delegateTotal  The total number of delegate nodes
  
  - BigInteger：delegateTotalHes  Total number of inactive nodes delegate
  
  - BigInteger：delegateRewardTotal  Total delegated rewards currently issued by the candidate
  
  - BigInteger：nextRewardPer Proportion of reward share in the next settlement cycle
  
  - BigInteger：rewardPer Proportion of reward share in current settlement cycle

- **Java SDK contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
CallResponse<Node> baseResponse = stakingContract.getStakingInfo(nodeId).send();
```

##### **getPackageReward**

> Query the block reward of the current settlement cycle

* **Introduction**

  no

* **return value**

```
CallResponse<BigInteger> baseResponse
```

- CallResponse<BigInteger> description
  -  int: Code result flag, 0 is success
  -  BigInteger：reward   Block rewards for the current settlement cycle
  -  String: ErrMsg error message, exists on failure

* **Java SDK contract use**

```java
CallResponse<BigInteger> response = stakingContract.getPackageReward().send();
```

##### **getStakingReward**

> Query the staking reward of the current settlement cycle

* **Introduction**

  no

* **return value**

```java
CallResponse<BigInteger> baseResponse
```

- CallResponse<BigInteger> description
  -  int: Code result flag, 0 is success
  -  BigInteger：reward   staking rewards for the current settlement cycle
  -  String: ErrMsg error message, exists on failure

* **Java SDK contract use**

```java
CallResponse<BigInteger> response = stakingContract.getStakingReward().send();
```

##### **getAvgPackTime**

> Query the average time of packed blocks

* **Introduction**

  no

* **return value**

```java
CallResponse<BigInteger> baseResponse
```

- CallResponse<BigInteger> description
  -  int: Code result flag, 0 is success
  -  BigInteger：data   average time of packed blocks
  -  String: ErrMsg error message, exists on failure

* **Java SDK contract use**

```java
CallResponse<BigInteger> response = stakingContract.getAvgPackTime().send();
```

### Delegation Related Interface

> Principal related contract interface in PlatON economic model

#### Load Delegate Contract

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
DelegateContract delegateContract = DelegateContract.load(web3j, credentials, chainId);
```

#### Interface Description

##### **delegate**

> Initiate a commission, commission a node that has been pledged, and commission a node to increase the weight of the node to obtain revenue

- **Introduction**

  - String: nodeId node id, hexadecimal format, starting with 0x
  - StakingAmountType: stakingAmountType, enumeration, FREE_AMOUNT_TYPE means use the free amount of the account, RESTRICTING_AMOUNT_TYPE means use the amount of the lock to make a pledge
  - BigInteger: amount of amount commissioned(based on the smallest unit, 1LAT = 10**18 VON)

- **return value**

``` java
TransactionResponse
```

- TransactionResponse: General Response Packet
	- int: Code result identification, 0 is success
	- String: ErrMsg error message, exists on failure
	- TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
BigDecimal amount = Convert.toVon("500000", Convert.Unit.ATP);

PlatonSendTransaction platonSendTransaction = delegateContract.delegateReturnTransaction(nodeId, stakingAmountType, amount.toBigInteger()).send();
TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getRelatedListByDelAddr**

> Query the NodeID and Pledged Id of the node entrusted by the current account address

* **Introduction**

  - String: address Account address of the principal

* **return value**

```java
CallResponse<List<DelegationIdInfo>> baseRespons
```

- CallResponse<List<DelegationIdInfo>>
  - int: Code result identification, 0 is success
  - List<DelegationIdInfo>: List of Data DelegationIdInfo objects
  - String: ErrMsg error message, exists on failure

- **DelegationIdInfo**: An object that stores the NodeID and the height of the pledged block of the node commissioned by the current account address
  - String: address Account address of the principal
  - String: NodeId Node Id of the validator
  - BigInteger: block height when StakingBlockNum initiated pledge

- **Java SDK contract use**

```java
CallResponse<List<DelegationIdInfo>> baseResponse = delegateContract.getRelatedListByDelAddr(delegateCredentials.getAddress()).send();
```

##### **getDelegateInfo**

> Query current single commission information

- **Introduction**

  - String: address Account address of the principal
  - String: nodeId node id, in hexadecimal format, starting with 0x
  - BigInteger: block height when stakingBlockNum initiated pledge

- **return value**

```java
CallResponse<Delegation>
```

- CallResponse<Delegation>
  - int: Code result identification, 0 is success
  - Delegation: Data delegation object data
  - String: ErrMsg error message, exists on failure

- **Delegation**: the object to save the delegation information of the current delegation account
  - String: delegateAddress The account address of the principal
  - String: nodeId Node Id of the validator
  - BigInteger: stakingBlockNum height when StakingBlockNum initiated pledge
  - BigInteger: delegateEpoch delegateEpoch's settlement cycle at the time of the most recent delegation to this candidate
  - BigInteger: delegateReleased to initiate a free amount lock-in period of the commissioned account
  - BigInteger: delegateReleasedHes initiated the free amount of the hesitation period commissioned by the commissioned account von
  - BigInteger: delegateLocked initiates a lock-in period of the entrusted account
  - BigInteger: delegateLockedHes initiated the hedging period of the locked account of the entrusted account
  - BigInteger：cumulativeIncome  Delegate income to be received

- **Java SDK contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
String address = "atp1qtp5fqtmudzge9aqt9rnzgdxv729pdq560vrat";
BigInteger stakingBlockNum = new BigInteger("10888");

CallResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, address, stakingBlockNum).send();
```

##### **unDelegate**

> Reduction / revocation of commission(all reductions are revocation)

- **Introduction**

  - String: nodeId node id, hexadecimal format, starting with 0x
  - BigInteger: The stakingBlockNum entrusted node has a high pledge block, which represents a unique sign of a pledge of a node
  - BigInteger: the commission amount of stakingAmount reduction(based on the smallest unit, 1LAT = 10**18 von)

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

* **Decode transaction receipt**

   - BigInteger：reward   Obtain the delegate income drawn when the commission is cancelled

- **Contract use**

```java
String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
BigDecimal stakingAmount = Convert.toVon("500000", Convert.Unit.ATP);
BigInteger stakingBlockNum = new BigInteger("12134");

PlatonSendTransaction platonSendTransaction = delegateContract.unDelegateReturnTransaction(nodeId, stakingBlockNum, stakingAmount.toBigInteger()).send();
TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();

if(baseResponse.isStatusOk()){ 
    BigInteger reward = delegateContract.decodeUnDelegateLog(baseResponse.getTransactionReceipt());
}
```

### Reward Related Interface

> Contract-related contract interfaces in the PlatON economic model

#### Load Reward Contract

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
RewardContract rewardContract = RewardContract.load(web3j, deleteCredentials, chainId);
```

#### Interface Description

##### **withdrawDelegateReward**

> Withdraw all currently available commissioned rewards on the account 

* **Introduction**

  no

* **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

* **Decode transaction receipt**
   - String：nodeId    node id
   - BigInteger：stakingNum  node staking block number
   - BigInteger：reward  received benefits

* **Contract use**

```java
PlatonSendTransaction platonSendTransaction = rewardContract.withdrawDelegateRewardReturnTransaction().send();
TransactionResponse baseResponse = rewardContract.getTransactionResponse(platonSendTransaction).send();
if(baseResponse.isStatusOk()){
    List<Reward> rewardList = rewardContract.decodeWithdrawDelegateRewardLog(baseResponse.getTransactionReceipt());
}
```

##### **getDelegateReward**

> Check the current account to get the reward details

* **Introduction**
  - String: check the address of your account
  - List<String>: nodeList Node list, if all is checked

* **return value**

```java
CallResponse<List<Reward>> baseRespons
```

- CallResponse<List<Reward>>description
	- int：code   result identification, 0 is success
	- List<Reward>：data   rewardList object data
	- String：errMsg   Error message, exists on failure

* **Reward**：Reward details
   - String：nodeId   
   - BigInteger：stakingNum  Node pledge block is high
   - BigInteger：reward  received benefits

* **Java SDK contract use**

```java
List<String> nodeList = new ArrayList<>();
nodeList.add(nodeId);
CallResponse<List<Reward>> baseResponse = rewardContract.getDelegateReward(delegateAddress, nodeList).send();
```

### Node-related Contracts

> Principal related contract interface in PlatON economic model

#### Load Node Contract

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
NodeContract nodeContract = NodeContract.load(web3j, credentials, chainId);
```

#### Interface Description

##### **GetVerifierList**

> Query the queue of validators in the current settlement cycle

* **Introduction**

  no

* **return value**

```java
CallResponse<List<Node>> baseResponse
```

- CallResponse<List<Node >>
  - int: Code result identification, 0 is success
  - List<Node>: data nodeList object data
  - String: ErrMsg error message, exists on failure

* **Node**: object for saving node information for a single current settlement cycle

  - String: nodeId The node Id of the pledge(also called the candidate's node Id)

  - String: stakingAddress The account used when initiating the pledge(when the pledge is cancelled, von will be returned to the account or the account's lock information)

  - String: benefitAddress is used to accept the block reward and pledged reward income account

  - BigInteger：rewardPer Proportion of reward share in current settlement cycle

  - BigInteger：nextRewardPer Proportion of reward share in the next settlement cycle

  - BigInteger: stakingTxIndex transaction index when pledge is initiated

  - BigInteger: programVersion The real version number of the PlatON process of the pledged node(the interface for obtaining the version number is provided by the governance)

  - BigInteger: stakingBlockNum block height when StakingBlockNum initiated pledge

  - BigInteger: shares the current candidate's total pledge plus the number of entrusted vons

  - String：externalId   External Id (with a length limit, the ID described by the third party to pull the node) is currently the public key of the keybase account, and the node icon is obtained through the public key.

  - String: nodeName The name of the node being pledged(the length is limited, indicating the name of the node)

  - String: website The third-party homepage of the Website node(the length of the node is the homepage of the node)

  - String: details The description of the Details node(the length is limited, indicating the description of the node)

  - BigInteger: validatorTerm
  
  - BigInteger：delegateTotal  The total number of commissioned nodes

  - BigInteger：delegateRewardTotal  Total delegated rewards currently issued by the candidate

* **Java SDK contract use**

```java
CallResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
```

##### **getValidatorList**
> Query the list of validators in the current consensus cycle

- **Introduction**

  no

- **return value**

```java
CallResponse<List<Node>> baseResponse
```

- CallResponse<List<Node >>
  - int: Code result identification, 0 is success
  - List<Node>: Data nodeList object data
  - String: ErrMsg error message, exists on failure

- **Node**: object that saves the information of a single current consensus cycle verification node

  - String: nodeId The node Id of the pledge(also called the candidate's node Id)

  - String: stakingAddress The account used when initiating the pledge(when the pledge is cancelled, von will be returned to the account or the account's lock information)

  - String: benefitAddress is used to accept the block reward and pledged reward income account

  - BigInteger：rewardPer Proportion of reward share in current settlement cycle

  - BigInteger：nextRewardPer Proportion of reward share in the next settlement cycle

  - BigInteger: stakingTxIndex transaction index when pledge is initiated

  - BigInteger: programVersion The real version number of the PlatON process of the pledged node(the interface for obtaining the version number is provided by the governance)

  - BigInteger: stakingBlockNum block height when StakingBlockNum initiated pledge

  - BigInteger: shares the current candidate's total pledge plus the number of entrusted vons

  - String：externalId   External Id (with a length limit, the ID described by the third party to pull the node) is currently the public key of the keybase account, and the node icon is obtained through the public key.

  - String: nodeName The name of the node being pledged(the length is limited, indicating the name of the node)

  - String：website   The third-party homepage of the node (there is a length limit, indicating the homepage of the node)

  - String：details   The description of the node (there is a length limit, indicating the description of the node)

  - BigInteger：validatorTerm   Validator's tenure
  
  - BigInteger：delegateTotal  The total number of nodes that are entrusted to take effect

  - BigInteger：delegateRewardTotal  The total entrusted reward currently issued by the candidate

  - BigInteger：nextRewardPer Reward share ratio in the next settlement cycle

  - BigInteger：rewardPer Reward share ratio in the current billing cycle

- **Java SDK contract use**

```java
CallResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
```

##### **GetCandidateList**

> Query all real-time candidate lists

- **Introduction**

  no

- **return value**

```java
CallResponse<List<Node>> baseResponse
```

- CallResponse<List<Node >>
  - int: Code result identification, 0 is success
  - List<Node>: Data nodeList object data
  - String: ErrMsg error message, exists on failure

- **Node**: holds a single candidate node information object

  - String: nodeId The node Id of the pledge(also called the candidate's node Id)

  - String: stakingAddress The account used when initiating the pledge(when the pledge is cancelled, von will be returned to the account or the account's lock information)

  - String: benefitAddress is used to accept the block reward and pledged reward income account

  - BigInteger：rewardPer Proportion of reward share in current settlement cycle

  - BigInteger：nextRewardPer Proportion of reward share in the next settlement cycle

  - BigInteger: stakingTxIndex transaction index when pledge is initiated

  - BigInteger: programVersion The real version number of the PlatON process of the pledged node(the interface for obtaining the version number is provided by the governance)

  - BigInteger：status   Candidate status，0: node available，1: node is unavailable ，2:Nodes that have a low block yield but do not meet the removal conditions，4:The node's VON is less than the minimum pledge threshold. 8: The node is reported to have double sign.，16:Node block rate is low and removal conditions are met, 32: node initiates cancellation

  - BigInteger：stakingEpoch   settlement cycle when current pledge amount is changed

  - BigInteger: stakingBlockNum block height when StakingBlockNum initiated pledge

  - BigInteger: shares the current candidate's total pledge plus the number of entrusted vons

  - BigInteger：released   VON that initiates a free amount locked period pledged account

  - BigInteger：releasedHes   VON to initiate a free amount of pledged account

  - BigInteger：restrictingPlan   VON that initiated the lock-up amount of the pledged account's lock-up period

  - BigInteger：restrictingPlanHes   VON that initiates the hesitation period of the locked amount of the pledged account

  - String：externalId   External Id (with a length limit, the ID described by the third party to pull the node) is currently the public key of the keybase account, and the node icon is obtained through the public key.

  - String: nodeName The name of the node being pledged(the length is limited, indicating the name of the node)

  - String: website The third-party homepage of the Website node(the length of the node is the homepage of the node)

  - String: details The description of the Details node(the length is limited, indicating the description of the node)

  - BigInteger：delegateEpoch The node's last commissioned settlement cycle
  
  - BigInteger：delegateTotal  The total number of commissioned nodes
  
  - BigInteger：delegateTotalHes  Total number of inactive nodes commissioned

  - BigInteger：delegateRewardTotal  Total delegated rewards currently issued by the candidate

- **Java SDK contract use**

```java
CallResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
```

### Governance Related Contracts

> Contract interface related to PlatON governance

#### Load governance contract

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
ProposalContract proposalContract = ProposalContract.load(web3j, credentials, chainId);
```

#### Interface Description

##### **submitProposal**

> Submit a Proposal

- **Introduction**

  - Proposal：proposal

* **TextProposal Proposal.createSubmitTextProposalParam()**
  - String：verifier Submit verifier
  - String：pIDID  PIPID

* **VersionProposal Proposal.createSubmitVersionProposalParam()**
  - String：verifier Submit verifier
  - String：pIDID  PIPID
  - BigInteger：newVersion  updated version
  - BigInteger：endVotingRounds   Number of voting consensus rounds. Explanation: Suppose that the transaction that submitted the proposal is round1 when the consensus round number is packed into the block, the proposal voting deadline block is high, which is round1 + endVotingRounds, the 230th block height of the consensus round (assuming a consensus round produces block 250, ppos Unveiled 20 blocks high in advance, 250 and 20 are configurable), where 0 <endVotingRounds <= 4840 (about 2 weeks, actual discussion can be calculated based on configuration), and is an integer)

* **ParamProposal Proposal.createSubmitParamProposalParam()**
  - String：verifier Submit verifier
  - String：pIDID  PIPID
  - String：module  parameter module
  - String：name  parameter name
  - String：newValue parameter newValue
  
* **CancelProposal Proposal.createSubmitCancelProposalParam()**
  - String：verifier Submit verifier
  - String：pIDID  PIPID
  - BigInteger：endVotingRounds  Number of voting consensus rounds. Refer to the description of submitting an upgrade proposal. At the same time, the value of this parameter in this interface cannot be greater than the corresponding
  - String：tobeCanceledProposalID  Proposal ID to be cancelled

* **return value**

```java
TransactionResponse
```

- BaseResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: Data response data
  - String: ErrMsg error message, exists on failure

- **Contract use**

```java
Proposal proposal = Proposal.createSubmitTextProposalParam(proposalNodeId,"1");

PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
TransactionResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
```

##### **vote**

> Vote on proposals

- **Introduction**

  - ProgramVersion: the real version of the ProgramVersion program, managed by the rpc interface admin_getProgramVersion
  - VoteOption: voteOption voting type, YEAS in favor, NAYS against, ABSTENTIONS abstaining
  - String: proposalID proposal ID
  - String: verifier declared node, can only be validator / candidate

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**:

```java
ProgramVersion programVersion = web3j.getProgramVersion().send().getAdminProgramVersion();
VoteOption voteOption =  VoteOption.YEAS;
String proposalID = "";
String verifier = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";

PlatonSendTransaction platonSendTransaction = proposalContract.voteReturnTransaction(programVersion, voteOption, proposalID, verifier).send();
TransactionResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getProposal**

> Query Proposal

* **Introduction**

  - String: proposalID proposal id

* **return value**

```java
CallResponse<Proposal>
```

- CallResponse<Proposal>
  - int: Code result identification, 0 is success
  - Proposal: Data Proposal object data
  - String: ErrMsg error message, exists on failure

- **Proposal**: Objects that hold information about a single proposal
  - String: proposalId
  - String: proposer ID of the proposal node
  - int: proposalType proposal type, 0x01: text proposal; 0x02: upgrade proposal; 0x03 parameter proposal
  - String: piPid proposal PIPID
  - BigInteger: submitBlock
  - BigInteger: endVotingBlock block height
  - BigInteger: newVersion
  - BigInteger: ID of the promotion proposal to be canceled by the toBeCanceled proposal
  - BigInteger: activeBlock(if the vote passes) the effective block height(endVotingBlock + 20 + 4 * 250<effective block height<= endVotingBlock + 20 + 10 * 250)
  - String: verifier

- **Contract use**

```java
// Proposal id
String proposalID = "";
CallResponse<Proposal> baseResponse = proposalContract.getProposal(proposalID).send();
```

##### **getTallyResult**

> Query Proposal Results

- **Introduction**

  - String: proposalID proposal ID

- **return value**

```java
CallResponse<TallyResult>
```

- CallResponse<TallyResult>
  - int: Code result identification, 0 is success
  - TallyResult: Data TallyResult object data
  - String: ErrMsg error message, exists on failure

- **TallyResult**: Object that holds the results of a single proposal
  - String: proposalID
  - BigInteger: yeas votes
  - BigInteger: nays
  - BigInteger: abstentions
  - BigInteger: accuVerifiers Total number of validators who have qualified to vote throughout the voting period
  - int: status proposal status

* **status**
  - Voting：0x01
  - Pass：0x02
  - Failed：0x03
  - PreActive：0x04
  - Active：0x05
  - Canceled：0x06

- **Contract use**

```java
// Proposal id
String proposalID ="";
CallResponse<TallyResult> baseResponse = proposalContract.getTallyResult(proposalID).send();
```

##### **getProposalList**

> Query proposal list

- **Introduction**

  no

- **return value**

```java
CallResponse<List<Proposal>>
```

- CallResponse<List<Proposal >>
  - int: Code result identification, 0 is success
  - List<Proposal>: Data ProposalList object data
  - String: ErrMsg error message, exists on failure

- **Proposal**: object for saving a single proposal
  - String: proposalId
  - String: proposer ID of the proposal node
  - int: proposalType proposal type, 0x01: text proposal; 0x02: upgrade proposal; 0x03 parameter proposal
  - String: piPid proposal PIPID
  - BigInteger: submitBlock
  - BigInteger: endVotingBlock block height
  - BigInteger: newVersion
  - String: ID of the promotion proposal to be canceled by the toBeCanceled proposal
  - BigInteger: activeBlock(if the vote passes) the effective block height(endVotingBlock + 20 + 4 * 250<effective block height<= endVotingBlock + 20 + 10 * 250)
  - String: verifier

- **Contract use**

```java
CallResponse<List<Proposal>> baseResponse = proposalContract.getProposalList().send();
```

##### **declareVersion**

> Release statement

- **Introduction**

  - ProgramVersion: the real version of the ProgramVersion program, managed by the rpc interface admin_getProgramVersion
  - String: verifier declared node, can only be validator / candidate

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**

```java
ProgramVersion programVersion = web3j.getProgramVersion().send().getAdminProgramVersion();
String verifier = "";

PlatonSendTransaction platonSendTransaction = proposalContract.declareVersionReturnTransaction(programVersion,verifier).send();
TransactionResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
```

##### **getActiveVersion**

> Query node chain effective version

- **Introduction**

  no

- **return value**

```java
CallResponse
```

- CallResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: Data response data
  - String: ErrMsg error message, exists on failure

- **Contract use**

```java
CallResponse<BigInteger> baseResponse = proposalContract.getActiveVersion().send();
ProposalUtils.versionInterToStr(baseResponse.getData());
```

### Double Sign Report Related Interface

> PlatON report contract related punishment interface

#### Load Report Contract

```
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
SlashContract contract = SlashContract.load(web3j, credentials, chainId);
```

#### Interface Description

##### **ReportDoubleSignReturnTransaction**

> Submit a Proposal

- **Introduction**

  - DuplicateSignType: DuplicateSignType enumeration, representing double sign types: prepareBlock, EprepareVote, viewChange
  - String: data json value of a single evidence, format refer to [RPC interface Evidences](# evidences_interface)

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**

```java
String data = ""; // Report evidence
PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_BLOCK, data).send();

TransactionResponse baseResponse = slashContract.getTransactionResponse(platonSendTransaction).send();
```

##### **CheckDoubleSign**

> Query whether a node has been reported as oversigned

- **Introduction**

  - DuplicateSignType: DuplicateSignType enumeration, representing double sign types: prepareBlock, EprepareVote, viewChange
  - String: address of the node reported by address
  - BigInteger: blockNumber multi-sign block height

- **return value**

```java
CallResponse
```

- CallResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: Data response data
  - String: ErrMsg error message, exists on failure

- **Contract use**

```java
CallResponse<String> baseResponse = slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK, "0x4F8eb0B21eb8F16C80A9B7D728EA473b8676Cbb3", BigInteger.valueOf(500L)).send();
```

### Lock Related Interface

> PlatON report contract related punishment interface

#### Loading The Hedging Contract

```java
//Java 8
Web3j web3j = Web3j.build(new HttpService("http://localhost:6789"));
long chainId = 201018;
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
RestrictingPlanContract contract = RestrictingPlanContract.load(web3j, credentials, chainId);
```

#### Interface Description

##### **CreateRestrictingPlan**

> Create Lockup Plan

- **Introduction**

  - String: address lock position is released to the account
  - List<RestrictingPlan>: plan Locked plan list(array)
    - epoch: indicates a multiple of the settlement cycle. The product of the number of blocks produced per settlement cycle indicates the release of locked funds at the height of the target block. If account is the incentive pool address, the period value is a multiple of 120(that is, 30 * 4). In addition, period, the number of blocks per cycle must be at least greater than the highest irreversible block height.
    - amount: indicates the amount to be released on the target block.

- **return value**

```java
TransactionResponse
```

- TransactionResponse: General Response Packet
  - int: Code result identification, 0 is success
  - String: ErrMsg error message, exists on failure
  - TransactionReceipt：transactionReceipt  Receipt of the transaction

- **Contract use**

```java
List<RestrictingPlan> restrictingPlans = new ArrayList<>();
restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(100), new BigInteger("100000000000000000000")));
restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(200), new BigInteger("200000000000000000000")));

PlatonSendTransaction platonSendTransaction = restrictingPlanContract.createRestrictingPlanReturnTransaction(restrictingRecvCredentials.getAddress(), restrictingPlans).send();
TransactionResponse baseResponse = restrictingPlanContract.getTransactionResponse(platonSendTransaction).send();
```

##### **GetRestrictingInfo**

> Get Locked Up Plan

- **Introduction**

  - String: address lock position is released to the account

- **return value**

```java
CallResponse<RestrictingItem> baseResponse
```

- CallResponse<RestrictingItem> description
  - int: Code result identification, 0 is success
  - RestrictingItem: Data RestrictingItem object data
  - String: ErrMsg error message, exists on failure

- **RestrictingItem**: save lock information object
  - BigInteger: balance
  - BigInteger: pledge pledge / mortgage amount
  - BigInteger: debt release amount due
  - List<RestrictingInfo>: info lock entry information
- **RestrictingInfo**: Object that saves information of a single lock entry
  - BigInteger: blockNumber releases block height
  - BigInteger: amount released

- **Contract use**

```java
CallResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(restrictingRecvCredentials.getAddress()).send();
```

## Solidity Contract Call

When deploying a Solidity smart contract on the blockchain, it must first be compiled into a bytecode format and then sent as part of the transaction. The Java SDK will help you generate a Java wrapper class for Solidity smart contracts, which can easily deploy Solidity smart contracts and call transaction methods, events, and constant methods in Solidity smart contracts.

### Compile Solidity Source Code

* Compile solidity source code with `solc` compiler, please download the corresponding solc compiler version according to the compiler version declared in the contract([solc download](https://github.com/PlatONnetwork/solidity/releases))：

```shell
$ solc <contract>.sol --bin --abi --optimize -o <output-dir>/
```

`bin`，Output a hex-encoded solidity binary file to provide transaction requests.
`abi`，Output a solidity application binary interface (`ABI`) file, which details all publicly accessible contract methods and their related parameters. The `abi` file is also used to generate the Java wrapper class corresponding to the solidity smart contract.

* Compile solidity source code with `platon-truffle`([platon-truffle development tool installation reference](https://platon-truffle.readthedocs.io/en/v0.13.2/getting-started/installation.html#)|[platon-truffle Development tool manual](https://platon-truffle.readthedocs.io/en/v0.13.2/))：

> **step1.** Initialize the project with platon-truffle

```
Initialize a project on the server where platon-truffle is installed。
mkdir HelloWorld
cd HelloWorld
truffle init
Prompt indicates success：

  ✔ Preparing to download
  ✔ Downloading
  ✔ Cleaning up temporary files
  ✔ Setting up box

  Unbox successful. Sweet!

  Commands:

    Compile:        truffle compile
    Migrate:        truffle migrate
    Test contracts: truffle test
```

> **step2.** Put HelloWorld.sol in `HelloWorld/contracts` directory

```
guest@guest:~/HelloWorld/contracts$ ls
HelloWorld.sol  Migrations.sol
```

> **step3.** Modify truffle-config.js file and change the compiler version to "^ 0.5.13"

```
compilers: {
    solc: {
       version: "^0.5.13",    // Fetch exact version from solc-bin (default: truffle's version)
      // docker: true,        // Use "0.5.1" you've installed locally with docker (default: false)
      // settings: {          // See the solidity docs for advice about optimization and evmVersion
      //  optimizer: {
      //    enabled: false,
      //    runs: 200
      //  },
      //  evmVersion: "byzantium"
       }
    }
}
```

> **step4.** Execute truffle compile to compile the contract

```
guest@guest:~/HelloWorld$ truffle compile

Compiling your contracts...

 Compiling ./contracts/HelloWorld.sol
 Compiling ./contracts/Migrations.sol

 compilation warnings encountered:

Warning: This is a pre-release compiler version, please do not use it in production.

 Artifacts written to /home/guest/hudenian/HelloWorld/build/contracts
 Compiled successfully using:
    solc: 0.5.13-develop.2020.1.2+commit.9ff23752.mod.Emscripten.clang
```

> **step5.** extract `abi` and `bin`file

```
Put the abi attribute in ./build/contracts/HelloWorld.json into the HelloWorld.abi file
Put the bytecode attribute in ./build/contracts/HelloWorld.json into the HelloWorld.bin file (need to remove the beginning of 0x)

```

### Solidity Smart Contract Java Packaging Class

The Java SDK supports automatic generation of Java wrapper classes for Solidity smart contracts from an `abi` file.

* Generate Java wrapper classes via command line tools（[platon-web3j download](http://download.alaya.network/alaya/sdk/0.13.2/alaya-web3j-0.13.2.1.zip)）:

```shell
$ alaya-web3j solidity generate [--javaTypes|--solidityTypes] /path/to/<smart-contract>.bin /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name
```

* Directly call the tool class in the Java SDK to generate a Java wrapper class:

```java
// Import console module via maven or gradle
compile "com.platon.client:console:{version}"

String args[] = {"generate", "/path/to/<smart-contract>.bin", "/path/to/<smart-contract>.abi", "-o", "/path/to/src/main/java", "-p" , "com.your.organisation.name"};
SolidityFunctionWrapperGenerator.run(args);
```

The `bin` and` abi` files are generated after compiling the solidity source code.

The main functions supported by the Java wrapper class corresponding to the Solidity smart contract:
- Build and deploy
- Determine contract validity
- Invoking transactions and events
- Call constant method

#### Building And Deploying Smart Contracts

The construction and deployment of smart contracts use the deploy method in the wrapper class：

```java
YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <transactionManager>, contractGasProvider, chainId
        [<initialValue>,] <param1>, ..., <paramN>).send();

or

YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <Credentials>, contractGasProvider, chainId
        [<initialValue>,] <param1>, ..., <paramN>).send();
```

This method will deploy smart contracts on the blockchain. After successful deployment, it will return a wrapper class instance of the smart contract, which contains the address of the smart contract.

If your smart contract accepts LAT transfers on the structure, you need to initialize the parameter value <initialValue>.

You can also create an instance of the Java wrapper class corresponding to the smart contract by using the address of the smart contract:

```java
YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, transactionManager, contractGasProvider, chainId);

or

YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, credentials, contractGasProvider, chainId);
```

#### Smart Contract Validity

Using this method, the validity of the smart contract can be verified. `True` will only be returned if the bytecode deployed in the contract address matches the bytecode in the smart contract package.

```java
contract.isValid();  // returns false if the contract bytecode does not match what's deployed
                     // at the provided address
```

#### TransactionManager
The Java SDK provides a transaction manager `TransactionManager` to control how you connect to the PlatON client. `RawTransactionManager` is used by default.
`RawTransactionManager` needs to specify the chain ID. Prevent transactions on one chain from being rebroadcasted to another chain:

```java
TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, 100L);
```

In addition to `RawTransactionManager`, the Java SDK also provides a client transaction manager` ClientTransactionManager`, which will hand over your transaction signing work to the PlatON client you are connecting to.
In addition, there is a `ReadonlyTransactionManager`, which is used to query data from the smart contract only and not to trade with it.

#### GasProvider
The handling fee of the contract is set through `GasProvider`, because the gas consumption of the contract is dynamic and related to the logic of the contract. It is recommended to use a larger value for the first deployment call, such as 999999. Later adjust according to the actual situation.

```java
BigInteger GAS_LIMIT = BigInteger.valueOf(999999);
BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

GasProvider gasProvider  = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
```

#### Invoking Transactions And Events
For all transactions methods, only the transaction receipt associated with the transaction is returned.

```java
TransactionReceipt transactionReceipt = contract.someMethod(<param1>, ...).send();
```

With transaction receipts, you can extract indexed and non-indexed event parameters.

```java
List<SomeEventResponse> events = contract.getSomeEvents(transactionReceipt);
```

Alternatively, you can use Observable filters to listen to events associated with smart contracts:

```java
contract.someEventObservable(startBlock, endBlock).subscribe(event -> ...);
```

#### Call Constant Method

Constant methods only do queries without changing the state of the smart contract.

```java
Type result = contract.someMethod(<param1>, ...).send();
```

## WASM Contract Call

When deploying a WASM smart contract on the blockchain, it must first be compiled into a bytecode format and then sent as part of the transaction. The Java SDK will help you generate a Java wrapper class for WASM smart contracts, which can easily deploy WASM smart contracts and call transaction methods, events, and constant methods in WASM smart contracts.

### Compile WASM Source Code

* Compile WASM contract source code with `CDT` compiler([CDT download](https://github.com/PlatONnetwork/PlatON-CDT/releases))：

After the CDT installation is successful, you can compile the WASM contract source code with the following command:

```shell
$ platon-cpp <contract>.cpp 
```

After successful compilation, `<contract> .wasm` and` <contract> .abi.json` files will be generated.

`wasm`，Output binary file of WASM contract to provide transaction request.
`abi.json`，Which details all publicly accessible contract methods and their related parameters. The `abi` file is also used to generate the Java wrapper class corresponding to the WASM smart contract.

* Compile WASM source code with `platon-truffle`([platon-truffle development tool installation reference](https://platon-truffle.readthedocs.io/en/v0.13.2/getting-started/installation.html#)|[platon-truffle Development tool manual](https://platon-truffle.readthedocs.io/en/v0.13.2/))

### WASM Smart Contract Java Packaging Class

The Java SDK supports automatic generation of Java wrapper classes for WASM smart contracts from an `abi` file.

* Generate Java wrapper classes via command line tools:

```shell
$ alaya-web3j wasm generate /path/to/<smart-contract>.wasm /path/to/<smart-contract>.abi.json -o /path/to/src/main/java -p com.your.organisation.name
```

* Directly call the tool class in the Java SDK to generate a Java wrapper class:

```java
String args[] = {"generate", "/path/to/<smart-contract>.wasm", "/path/to/<smart-contract>.abi.json", "-o", "/path/to/src/main/java", "-p" , "com.your.organisation.name"};
WasmFunctionWrapperGenerator.run(args);
```

The `wasm` and` abi.json` files are generated after compiling the WASM contract source code.

The main functions supported by the Java wrapper class corresponding to the WASM smart contract:
- Build and deploy
- Determine contract validity
- Invoking transactions and events
- Call constant method

#### Building And Deploying Smart Contracts

The construction and deployment of smart contracts use the deploy method in the wrapper class：

```java
YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <transactionManager>, contractGasProvider, chainId,
        [<initialValue>,] <param1>, ..., <paramN>).send();

or

YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <Credentials>, contractGasProvider, chainId,
        [<initialValue>,] <param1>, ..., <paramN>).send();
```

This method will deploy smart contracts on the blockchain. After successful deployment, it will return a wrapper class instance of the smart contract, which contains the address of the smart contract.

If your smart contract accepts LAT transfers on the structure, you need to initialize the parameter value <initialValue>.

You can also create an instance of the Java wrapper class corresponding to the smart contract by using the address of the smart contract:

```java
YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, transactionManager, contractGasProvider,chainId);

or

YourSmartContract contract = YourSmartContract.load(
        "<bech32Address>", web3j, credentials, contractGasProvider,chainId);
```

#### Smart Contract Validity

Using this method, the validity of the smart contract can be verified. `True` will only be returned if the bytecode deployed in the contract address matches the bytecode in the smart contract package.

```java
contract.isValid();  // returns false if the contract bytecode does not match what's deployed
                     // at the provided address
```

#### TransactionManager
The Java SDK provides a transaction manager `TransactionManager` to control how you connect to the PlatON client. `RawTransactionManager` is used by default.
`RawTransactionManager` needs to specify the chain ID. Prevent transactions on one chain from being rebroadcasted to another chain:

```java
TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, 100L);
```

In addition to `RawTransactionManager`, the Java SDK also provides a client transaction manager` ClientTransactionManager`, which will hand over your transaction signing work to the PlatON client you are connecting to.
In addition, there is a `ReadonlyTransactionManager`, which is used to query data from the smart contract only and not to trade with it.

#### GasProvider
The handling fee of the contract is set through `GasProvider`, because the gas consumption of the contract is dynamic and related to the logic of the contract. It is recommended to use a larger value for the first deployment call, such as 999999. Later adjust according to the actual situation.

```java
BigInteger GAS_LIMIT = BigInteger.valueOf(999999);
BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

GasProvider gasProvider  = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
```

#### Invoking Transactions And Events
For all transactions methods, only the transaction receipt associated with the transaction is returned.

```java
TransactionReceipt transactionReceipt = contract.someMethod(<param1>, ...).send();
```

With transaction receipts, you can extract indexed and non-indexed event parameters.

```java
List<SomeEventResponse> events = contract.getSomeEvents(transactionReceipt);
```

Alternatively, you can use Observable filters to listen to events associated with smart contracts:

```java
contract.someEventObservable(startBlock, endBlock).subscribe(event -> ...);
```

#### Call Constant Method

Constant methods only do queries without changing the state of the smart contract.

```java
Type result = contract.someMethod(<param1>, ...).send();
```