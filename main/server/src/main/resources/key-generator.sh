
keytool -genkeypair \
-alias mysslkey \
-keyalg RSA \
-keysize 2048 \
-storetype PKCS12 \
-keystore keystore.p12 \
-validity 3650 \
-storepass changeit

