# 由于现在下载jdk的话需要使用认证，所以我们在生产环境可以使用这个，上线的时候我们可以自己来制作一个镜像就好了，-alpine  这个是一个极小的linux系统（这样可以减小docker镜像的体积），后面不建议使用这个，因为这个dns好像实现的不一样，在服务发现中可能会有问题，所以最好还是使用centos的镜像为基础来构建
FROM openjdk:8-jdk-alpine
VOLUME /tmp
#(前面一个是打包的jar的名字，后面一个是在容器中使用的名字，这个要和最后的-jar 后面的jar包的名字一致)
ADD register-1.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
#Djava.security.egd  这个是用来防止springboot项目tomcat启动慢的问题（具体可搜索：随机数数与熵池策略）
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
EXPOSE 8761