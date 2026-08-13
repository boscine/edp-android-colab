@echo off
:: Set Git Identity
git config user.name "Jhan"
git config user.email "jlmandahinog65115@liceo.edu.ph"

:: Set/Switch Remote
echo Setting remote URL to https://github.com/boscine/edp-android-colab.git ...
git remote set-url origin https://github.com/boscine/edp-android-colab.git 2>nul || git remote add origin https://github.com/boscine/edp-android-colab.git

:: Switch/Create Branch
echo Switching to lab-activity-7 branch...
git checkout lab-activity-7 2>nul || git checkout -b lab-activity-7

:: Stage and Commit
git add .
git commit -m "Lab Activity 7: Final Rewrite - Jetpack Compose navigation two-screen app"

:: Pull/Push
echo Integrating remote changes and pushing...
git pull origin lab-activity-7 --rebase
git push -u origin lab-activity-7

echo.
echo ======================================================
echo Done! Your code is now on the lab-activity-7 branch.
echo Check the 'screenshots/' folder for 'greeting_screenshot.png'.
echo ======================================================
pause
