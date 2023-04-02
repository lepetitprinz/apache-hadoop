# Hadoop distributed cluster

## Configuration
Master01 Node - namenode
Master02 Node - secondary namenode
Slave01 Node  - datanode
Slave02 Node  - datanode
Slave03 Node  - datanode


## Initial Setting

- Insert all node IP information in hosts file 
$ etc/hosts

- command on all nodes
$ source /etc/profile

- start dfs 
# $HADOOP_HOME/sbin/start-dfs.sh

- start yarn
# $HADOOP_HOME/sbin/start-yarn.sh