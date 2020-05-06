# sb-cloud
# 目前这个项目啥也不是！
在windows下请先修改 C:\Windows\System32\drivers\etc\hosts
```$xslt
# 增加以下配置
127.0.0.1 node1
127.0.0.1 node2
127.0.0.1 node3
127.0.0.1 node4
```

```text
docker run --name register -p 8761:8761 -d --rm sb-cloud/register

docker run -d --name provider -p 9000:9000 --link register --rm sb-cloud/provider

docker run -d --name consumer -p 8000:8000 --link register --rm sb-cloud/consumer

docker run -d --name gateway -p 8001:8001 --link register --rm sb-cloud/gateway
```

```text
docker run -d --net=host \
  -e MESOS_PORT=5050 \
  -e MESOS_ZK=zk://192.168.56.105:2181/mesos \
  -e MESOS_QUORUM=1 \
  -e MESOS_REGISTRY=in_memory \
  -e MESOS_LOG_DIR=/var/log/mesos \
  -e MESOS_WORK_DIR=/var/tmp/mesos \
  -v "$(pwd)/log/mesos:/var/log/mesos" \
  -v "$(pwd)/tmp/mesos:/var/tmp/mesos" \
  mesosphere/mesos-master
```