#!/bin/bash
cp ../target/smart-platform-basic-gateway-0.1.0-exec.jar app.jar
docker rmi -f registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_gateway
docker build -t registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_gateway .
docker push registry.cn-shenzhen.aliyuncs.com/hzly/smart_platform_basic_gateway
