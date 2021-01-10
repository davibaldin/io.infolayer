# Setup Infolayer

Using this 10 minutes guide to up and running you first deployment.

## Custom Install

### 1. Prepare environment

```bash
yum install epel-release -y
yum install java-11-openjdk.x86_64 nginx -y
yum update -y

sed -i s/^SELINUX=.*$/SELINUX=permissive/ /etc/selinux/config
setenforce 0
```

Minimum steps for demonstration porpuses. We encourage you to keep Selinux enabled by configuring it acoordingly.

### 2. Setup Apache Kafka

Copy/Paste to install Kafka. Tested on EL (RedHat/CentOS) 7 and 8.

```bash
sudo useradd kafka -m
sudo usermod -aG wheel kafka
curl "https://downloads.apache.org/kafka/2.7.0/kafka_2.13-2.7.0.tgz" -o /home/kafka/kafka_2.13-2.7.0.tgz
cd /home/kafka
tar -xzf kafka_2.13-2.7.0.tgz
mv kafka_2.13-2.7.0 2.7.0
chown -R kafka.kafka 2.7.0 

cat <<EOF >> /home/kafka/2.7.0/config/server.properties
delete.topic.enable = true
EOF

cat <<EOF >> /etc/systemd/system/zookeeper.service
[Unit]
Requires=network.target remote-fs.target
After=network.target remote-fs.target

[Service]
Type=simple
User=kafka
ExecStart=/home/kafka/2.7.0/bin/zookeeper-server-start.sh /home/kafka/2.7.0/config/zookeeper.properties
ExecStop=/home/kafka/2.7.0/bin/zookeeper-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
EOF

cat <<EOF >> /etc/systemd/system/kafka.service

[Unit]
Requires=zookeeper.service
After=zookeeper.service

[Service]
Type=simple
User=kafka
ExecStart=/bin/sh -c '/home/kafka/2.7.0/bin/kafka-server-start.sh /home/kafka/2.7.0/config/server.properties > /home/kafka/2.7.0/logs/kafka.log 2>&1'
ExecStop=/home/kafka/2.7.0/bin/kafka-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
EOF

systemctl daemon-reload
systemctl enable kafka
systemctl enable zookeeper
systemctl start zookeeper
systemctl start kafka
firewall-cmd --add-port 9092/tcp
firewall-cmd --reload
```

Steps based on [DigitalOcean's How to install Kafka](https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-centos-7)

### 3. Deploy API Gateway

- Setup: Linux (systemv) and Windows (winsw) services. Kubernets is comming...

- Running:

```shell
java -jar gateway.jar --server.address=kafka.fqdn:5099 --bind=0.0.0.0:8080
```

### 4. Deploy Scheduler

- Setup: Linux (systemv) and Windows (winsw) services. Kubernets is comming...

- Running:

```shell
java -jar scheduler.jar --server.address=kafka.fqdn:5099
```

### 5. Deploy Executor Services

- Setup: Linux (systemv) and Windows (winsw) services. Kubernets is comming...

- Running:

```shell
java -jar executor.jar --server.address=kafka.fqdn:5099
```

### 6. Deploy Remote (optional)

- Setup: Linux (systemv) and Windows (winsw) services. Kubernets is comming...

- Running:

```shell
java -jar remote.jar --server.address=kafka.fqdn:5099
```

## Setup on Kubernetes

In case you already have Kafka running, skeep step 1.

TBD
