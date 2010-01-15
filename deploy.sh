#!/bin/bash
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Beehao deploy to GAE

echo "导出war (play war -o /tmp/beehao)"

#rm -rf /tmp/beehao

play war -o /tmp/beehao

echo "部署到GAE (appcfg update /tmp/beehao)"

appcfg update /tmp/beehao

echo "清理临时数据"

rm -rf /tmp/beehao

