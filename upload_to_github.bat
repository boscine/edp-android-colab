@echo off
:: Set Git Identity
git config user.name "Jhan"
git config user.email "jlmandahinog65115@liceo.edu.ph"

:: Set Remote (if not already set correctly)
git remote add origin https://github.com/boscine/edp-android-colab.git 2>nul

:: Switch to prelim-handson-exam branch
echo Switching to prelim-handson-exam branch...
git checkout prelim-handson-exam 2>nul || git checkout -b prelim-handson-exam

:: Stage and Commit
git add .
git commit -m "Prelim hands-on: my profile screen"

:: Push to GitHub
echo Pushing to GitHub...
git push -u origin prelim-handson-exam

echo.
echo Done! Please copy the branch link and submit to the class portal with your screenshot.
pause
