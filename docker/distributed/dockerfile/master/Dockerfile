FROM centos:7

RUN mkdir -p /opt/jdk && \
	mkdir -p /opt/hadoop && \
	mkdir -p /opt/docker && \
	mkdir -p /data/tmp && \
	mkdir -p /tmp/hadoop/master

RUN yum install -y net-tools && \
	yum install -y vim-enhanced && \
	yum install -y wget && \
	yum install -y openssh-server openssh-clients openssh-askpass && \
	yum update -y && \
	yum install java-1.8.0-openjdk-devel -y

RUN ln -s /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.352.b08-2.el7_9.aarch64 /opt/jdk/current

ENV JAVA_HOME=/opt/jdk/current
ENV	PATH=$PATH:$JAVA_HOME/bin

COPY file/* /opt/hadoop

WORKDIR /opt/hadoop

RUN tar xvzf hadoop-3.1.3.tar.gz && \
	rm -rf hadoop-3.1.3.tar.gz

RUN	ln -s /opt/hadoop/hadoop-3.1.3 /opt/hadoop/current

ENV HADOOP_HOME=/opt/hadoop/current
ENV	PATH=$PATH:$HADOOP_HOME/bin
ENV	PATH=$PATH:$HADOOP_HOME/sbin

RUN ssh-keygen -f /etc/ssh/ssh_host_rsa_key -t rsa -N "" && \
	ssh-keygen -f /etc/ssh/ssh_host_ecdsa_key -t ecdsa -N "" && \
	ssh-keygen -f /etc/ssh/ssh_host_ed25519_key -t ed25519 -N ""

RUN echo "/usr/sbin/sshd" >> /etc/profile && \
	source /etc/profile

RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
RUN cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys

COPY config/* /tmp/hadoop/master

RUN mv /tmp/hadoop/master/hadoop-env.sh $HADOOP_HOME/etc/hadoop/hadoop-env.sh && \
    mv /tmp/hadoop/master/core-site.xml $HADOOP_HOME/etc/hadoop/core-site.xml && \
    mv /tmp/hadoop/master/hdfs-site.xml $HADOOP_HOME/etc/hadoop/hdfs-site.xml && \ 
    mv /tmp/hadoop/master/yarn-site.xml $HADOOP_HOME/etc/hadoop/yarn-site.xml && \
    mv /tmp/hadoop/master/mapred-site.xml $HADOOP_HOME/etc/hadoop/mapred-site.xml && \
    mv /tmp/hadoop/master/workers $HADOOP_HOME/etc/hadoop/workers

RUN cat /tmp/hadoop/master/hosts >> /etc/hosts

VOLUME ["/opt/docker/conf", "/opt/docker/data", "/opt/docker/jar"]