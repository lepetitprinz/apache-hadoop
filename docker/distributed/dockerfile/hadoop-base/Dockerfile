FROM centos7

RUN mkdir -p /opt/jdk && \
	mkdir -p /opt/hadoop && \
	mkdir -p /opt/docker && \
	mkdir -p /data/tmp && \
	mkdir -p /tmp

RUN yum install -y net-tools && \
	yum install -y vim-enhanced && \
	yum intall -y wget && \
	yum install -y openssh-server openssh-clients openssh-askpass && \
	yum update -y && \
	yum install java-1.8.0-openjdk-devel.x86_64 -y

RUN ln -s /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.312.b07-2.el8_5.x86_64 /opt/jdk/current

ENV JAVA_HOME=/opt/jdk/current \
	PATH=$PATH:$JAVA_HOME/bin

WORKDIR /opt/hadoop

RUN wget https://archive.apache.org/dist/hadoop/common/hadoop-3.1.3/hadoop-3.1.3.tar.gz
	tar xvzf hadoop-3.1.3.tar.gz
	ln -s /opt/hadoop/hadoop-3.1.3 /opt/hadoop/current

ENV HADOOP_HOME=/opt/hadoop/current \
	PATH=$PATH:$HADOOP_HOME/bin \
	PATH=$PATH:$HADOOP_HOME/sbin

RUN ssh-keygen -f /etc/ssh/ssh_host_rsa_key -t rsa -N "" && \
	ssh-keygen -f /etc/ssh/ssh_host_ecdsa_key -t ecdsa -N "" && \
	ssh-keygen -f /etc/ssh/ssh_host_ed25519_key -t ed25519 -N ""

RUN echo "/usr/sbin/sshd" >> /etc/profile && \
	source /etc/profile

RUN ssh-keygen -t rsa -P ""
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys

COPY config/* /tmp/

RUN mv /tmp/hadoop-env.sh $HADOOP_HOME/etc/hadoop/hadoop-env.sh && \
    mv /tmp/core-site.xml $HADOOP_HOME/etc/hadoop/core-site.xml && \
    mv /tmp/hdfs-site.xml $HADOOP_HOME/etc/hadoop/hdfs-site.xml && \ 
    mv /tmp/yarn-site.xml $HADOOP_HOME/etc/hadoop/yarn-site.xml && \
    mv /tmp/mapred-site.xml $HADOOP_HOME/etc/hadoop/mapred-site.xml

RUN echo "172.17.0.2	master01" >> /etc/hosts && \
	echo "172.17.0.3	master02" >> /etc/hosts && \
	echo "172.17.0.4	slave01" >> /etc/hosts && \
	echo "172.17.0.5	slave02" >> /etc/hosts && \
	echo "172.17.0.6	slave03" >> /etc/hosts