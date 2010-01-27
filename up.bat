@echo off
echo "deploy to GAE (appcfg update %TEMP%/deploy/beehao)"

appcfg update %TEMP%/deploy/beehao

echo "clean temp data"

rm -rf %TEMP%/deploy/beehao

