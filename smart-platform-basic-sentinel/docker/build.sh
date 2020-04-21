#!/bin/bash
export SENTINEL_DASHBOARD_VERSION=1.7.2
if [ -f "sentinel-dashboard-${SENTINEL_DASHBOARD_VERSION}.jar" ]; then
  cp sentinel-dashboard-${SENTINEL_DASHBOARD_VERSION}.jar app.jar
else
  wget -O app.jar https://github.com/alibaba/Sentinel/releases/download/${SENTINEL_DASHBOARD_VERSION}/sentinel-dashboard-${SENTINEL_DASHBOARD_VERSION}.jar
fi
docker rmi -f registry.cn-shenzhen.aliyuncs.com/hzly/sentinel_dashboard:${SENTINEL_DASHBOARD_VERSION}
docker build -t registry.cn-shenzhen.aliyuncs.com/hzly/sentinel_dashboard:${SENTINEL_DASHBOARD_VERSION} .
docker push registry.cn-shenzhen.aliyuncs.com/hzly/sentinel_dashboard:${SENTINEL_DASHBOARD_VERSION}
