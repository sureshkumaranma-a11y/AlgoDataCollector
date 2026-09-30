# Algo Data Collector — Cloud APK Build

This project can be built on GitHub-hosted runners without installing Android Studio.

Steps:
1. Sign in to GitHub.
2. Create a new repository named `AlgoDataCollector`.
3. Upload the contents of this project to the repository.
4. Open the repository's Actions tab.
5. Select `Build Android APK` and click `Run workflow`.
6. After a successful run, open that workflow run and download the artifact
   `AlgoDataCollector-debug-apk`.
7. Extract the artifact ZIP to obtain `app-debug.apk`.
8. Copy the APK to your Android phone and install it.

This is the MVP. TradingView/option-data processing will be added in later versions.
