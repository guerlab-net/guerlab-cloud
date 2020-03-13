#!/bin/bash
cp ../target/smart-platform-basic-admin-exec.jar app.jar
docker rmi -f registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_admin
docker build -t registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_admin .
docker push registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_admin
