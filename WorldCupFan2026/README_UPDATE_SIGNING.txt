WorldCupFan2026 v3.1 stable update build

This package includes a stable test signing key:
app/worldcupfan2026-test-update.jks

Purpose:
- GitHub Actions signs every debug APK with the same certificate.
- After installing this v3.1 once, future APKs built with this same project should update over the existing app.

Important:
- If the currently installed app was signed with a different key, Android will require one last uninstall.
- After that, do not delete or replace the JKS file and do not change applicationId.
- For Play Store final release, use Play App Signing / secure upload key handling.
