FROM ubuntu:16.04
WORKDIR /root
RUN set -ex \
    && apt-get update \
    && apt-get -y install software-properties-common \
    && add-apt-repository ppa:platonnetwork/platon
ADD config .
RUN set -ex \
    && apt-get update \
    && apt-get -y install platon \
    && platon --datadir ./data init platon.json
ENTRYPOINT ["/usr/bin/platon","--identity","platon","--datadir","./data","--port","16789","--rpcaddr","0.0.0.0","--rpcport","6789","--rpcapi","db,eth,net,web3,admin,personal","--rpc","--nodiscover","--nodekey","./data/platon/nodekey"]
