#!/bin/bash
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
# Beehao deploy to GAE

echo "导出war (play war -o /tmp/beehao)"

rm -rf /tmp/beehao

play war --%war -o /tmp/beehao

echo "部署到GAE (appcfg.sh  update /tmp/beehao)"

appcfg.sh update /tmp/beehao



