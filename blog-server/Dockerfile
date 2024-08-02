# Java 17 Dockerfile

# 基础镜像
FROM openjdk:17-alpine

LABEL MAINTAINER="未月拾叁"


WORKDIR /root/java


# 设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


# 设置时区
ENV TZ=Asia/Shanghai

# 声明需要使用的端口
EXPOSE 8080

# 声明需要使用的环境变量
ARG JAR_FILE


# 将jar包添加到容器中
ADD ${JAR_FILE} ./bili.jar

#nohup 后面跟的是命令，这里是启动java程序，并把日志输出到nohup.out文件中，以便于查看启动过程及错误信息。
# -Dspring.profiles.active=online 启动时激活的配置文件，这里是online模式
# -Dspring.config.location=/usr/local/java/application.yaml,
# /usr/local/java/application-online.yaml 指定配置文件的路径，这里是application.yaml和application-online.yaml
# -jar /usr/local/java/bili.jar 启动的jar包路径
ENTRYPOINT ["nohup", "java", "-Dspring.profiles.active=online", "-Dspring.config.location=/root/java/application.yaml,/root/java/application-online.yaml", "-jar", "/root/java/bili.jar"]


