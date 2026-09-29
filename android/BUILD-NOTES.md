# Build notes — House Rules Android

`assembleDebug` was verified working on 2026-09-28 (APK at
`app/build/outputs/apk/debug/app-debug.apk`, 8.9 MB).

## One-time environment setup (this sandbox)

The sandbox has two networking quirks that break a plain Gradle build:

1. **Gradle daemon can't talk to its client over IPv6 loopback.**
   `localhost` resolves to `::1` first, but the daemon binds `127.0.0.1`.
   Fix: force the IPv4 stack on every JVM via `JAVA_TOOL_OPTIONS` —
   `org.gradle.jvmargs` in `gradle.properties` is ignored for `--no-daemon`
   single-use daemons.

2. **The egress proxy MITMs TLS and only works over IPv6.**
   Java's direct IPv4 connection to `hatch-egress-proxy:3128` hangs, and the
   proxy presents certs issued by `Hatch Sandbox Egress CA`, which is not in
   Java's cacerts. Fix: run a small localhost CONNECT forwarder
   (`/tmp/proxytest/fwd.py` — Python, unaffected by the JVM's IPv4 forcing)
   that listens on `127.0.0.1:3129` and relays to the egress proxy over IPv6,
   and import the CA into the JDK:

   ```sh
   keytool -importcert -noprompt -trustcacerts -alias hatch-egress-ca \
     -file /usr/local/share/ca-certificates/hatch-egress-ca.crt \
     -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
   ```

## Reproducible build

```sh
# 1. start the forwarder (background)
python3 /tmp/proxytest/fwd.py &

# 2. ~/.gradle/gradle.properties must contain:
#    org.gradle.jvmargs=-Djava.net.preferIPv4Stack=true -Xmx2048m -Dfile.encoding=UTF-8
#    systemProp.https.proxyHost=127.0.0.1
#    systemProp.https.proxyPort=3129
#    systemProp.http.proxyHost=127.0.0.1
#    systemProp.http.proxyPort=3129

# 3. build
cd ~/workspace/house-rules-android
export JAVA_HOME=~/workspace/.tooling/jdk-17.0.20.1+1
export ANDROID_HOME=~/workspace/.tooling/android-sdk
export GRADLE_USER_HOME=/home/hatch/.gradle
export JAVA_TOOL_OPTIONS="-Djava.net.preferIPv4Stack=true"
/home/hatch/.gradle/wrapper/dists/gradle-8.7-bin/c225461986ad5741326ccd56e4273476/gradle-8.7/bin/gradle assembleDebug --no-daemon
```

(Or `./gradlew assembleDebug --no-daemon` — the wrapper jar is committed —
with the same env vars.)

## Verified APK properties (aapt dump badging)

- package `com.orbitaldesk.houserules`, versionCode 1, versionName 1.0
- minSdk 26, targetSdk 34
- zero user-facing permissions (only the auto-added signature-level
  `DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION` from AndroidX on API 34)
- label "House Rules", launchable `MainActivity`, adaptive launcher icon

## Project file naming

The Gradle scripts use Kotlin DSL and are named `*.gradle.kts`
(`settings.gradle.kts`, `build.gradle.kts`, `app/build.gradle.kts`).
