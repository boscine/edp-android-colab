@echo off
:: Set Git Identity
git config user.name "Jhan"
git config user.email "jlmandahinog65115@liceo.edu.ph"

:: Set Remote
git remote add origin https://github.com/boscine/edp-android-colab.git 2>nul

:: Switch/Create Branch
git checkout -b lab-activity-2 2>nul || git checkout lab-activity-2

:: Stage and Commit
git add .
git commit -m "Lab Activity 2: Final Submission Version"

:: Create a Tag to mark this as its own version
echo Tagging version v2.0...
git tag -a v2.0 -m "Laboratory Activity 2 Final Version" -f

:: Pull/Push
echo Integrating remote changes and pushing...
git pull origin lab-activity-2 --rebase
git push -u origin lab-activity-2
git push origin v2.0 -f

echo.
echo Done! Version v2.0 has been created and pushed to the lab-activity-2 branch.
pause
