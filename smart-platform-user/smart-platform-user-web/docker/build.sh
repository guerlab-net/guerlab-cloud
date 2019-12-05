#!/bin/bash
cp ../target/smart-platform-user-web-0.1.0-exec.jar app.jar
docker rmi -f registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_user_web
docker build -t registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_user_web .
docker push registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_user_web
