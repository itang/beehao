@echo off

echo "export war (play war -o %TEMP%/deploy/beehao)"

rm -rf %TEMP%/deploy/beehao

play war -o %TEMP%/deploy/beehao


