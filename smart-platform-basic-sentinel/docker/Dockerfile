# 设置依赖镜像
FROM adoptopenjdk:11-jre-openj9

# 设置时区为 UTF+8
ENV TZ=Asia/Shanghai

# 设置应用启动sh文件
ENTRYPOINT ["/app.sh"]

ADD run.sh /app.sh
RUN chmod 777 /app.sh
ADD app.jar /app.jar
