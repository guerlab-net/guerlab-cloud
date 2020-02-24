#!/bin/bash
cp ../target/smart-platform-basic-swagger-exec.jar app.jar
docker rmi -f registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_swagger
docker build -t registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_swagger .
docker push registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_swagger
