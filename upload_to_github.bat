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
git commit -m "Lab Activity 2: Implementation of Profile Screen and Material 3 Theming"

:: Pull changes from remote to handle the [rejected] error (merging or rebasing)
echo Pulling latest changes...
git pull origin lab-activity-2 --rebase

:: Push to GitHub
echo Pushing to GitHub...
git push -u origin lab-activity-2

echo.
echo Done! If the push was successful, please copy the link and submit to GCR.
pause
