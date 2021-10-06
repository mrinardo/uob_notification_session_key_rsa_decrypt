# UOB Notification Session Key RSA Decrypt

Java program to test RSA decryption of UOB's notification payload session key.

The encrypted session key in base64 format should come from the notification payload sent by UOB notification service.

The appropriated private key in PEM format (PKCS#8) should be used for decryption.

![Screenshot](https://raw.githubusercontent.com/mrinardo/uob_notification_session_key_rsa_decrypt/assets/SessionKeyDecrypt.PNG)

## Reference
- https://github.com/1MansiS/java_crypto/blob/master/cipher/SecuredRSAUsage.java