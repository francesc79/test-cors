keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -sigalg SHA256withRSA -keysize 2048 -keystore ./keystore.p12 -validity 3650 -storepass mypassword -dname "cn=TestCORS"
