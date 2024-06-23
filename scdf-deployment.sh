#!/bin/bash

echo "==== Retrieving Project Version ===="
PROJECT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
echo -e "==== Done! Project version: ${PROJECT_VERSION} ====\n"

echo "==== Deploying Application ===="
response=$(curl -s -w "%{http_code}" -X POST http://localhost:9393/apps/task/ec-cloud-task-app-v2 \
     -d "uri=maven://com.fnf:ec-cloud-task-app-v2:jar:${PROJECT_VERSION}" \
     -d "force=true" \
     -d "bootVersion=2")

echo -e "\n==== App Deployment Result ===="
echo "$response"

http_code=${response: -3}

if [ "$http_code" -ge 200 ] && [ "$http_code" -lt 300 ]; then
    echo -e "\n==== Updating App Version ===="
    response=$(curl -s http://localhost:9393/apps/task/ec-cloud-task-app-v2/${PROJECT_VERSION} -i -X PUT \
        -H 'Accept: application/json')

    echo -e "\n==== Update App Version Result ===="
    echo "$response"
else
    echo -e "\n==== ERROR: App Deployment Failed with Status Code: ${http_code} ===="
fi