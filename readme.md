# Kotlin SMTP

This projects provides an [rfc5321](https://tools.ietf.org/html/rfc5321) compliant SMTP server implementation - written in Kotlin.

Currently, the only dependency of this project is the official network module of Ktor. This implementation is 100% suspending, it makes high use of Kotlin coroutines.
