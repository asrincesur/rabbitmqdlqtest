
## RabbitMQ Spring Boot DLQ-Enabled Configuration

This project demonstrates a messaging setup using Spring Boot and RabbitMQ, including DLQ (Dead Letter Queue), retry mechanism, and custom error handling.

### 🚀 Features
- Main queue and DLQ definition
- Retry interceptor with 5 attempts
- Custom MessageRecoverer that adds error message and cause class to DLQ header
- Jackson-based JSON message converter
- **Since a custom recoverer is used, messages are manually published to the DLQ**

### 📁 Configuration Components
- `RabbitMQFullConfig.java`: Defines all queues, exchanges, bindings, retry and listener setup
- `CustomMessageRecoverer.java`: Adds error reason into DLQ messages and manually publishes the message to DLQ
- `RabbitTemplate`: Configured for proper JSON message delivery
- `SimpleRabbitListenerContainerFactory`: Listener factory with retry and message conversion support

### 🧪 Test Scenarios
- If message processing fails, it is retried 5 times
- If still failing, it is manually routed to DLQ
- DLQ message will contain `x-error-reason` header with the cause

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

## TR RabbitMQ Spring Boot DLQ Destekli Yapılandırma

Bu proje, Spring Boot ile RabbitMQ kuyruk sistemini kullanarak DLQ (Dead Letter Queue), retry mekanizması ve özel hata yönetimi barındıran mesajlaşma yapısını örneklemektedir.

### 🚀 Özellikler
- Ana kuyruk ve DLQ tanımı
- Retry interceptor ile 5 kez tekrar deneme
- Özel MessageRecoverer ile hata mesajının DLQ mesajına header olarak eklenmesi
- Jackson JSON message converter entegrasyonu
- **Custom recoverer kullanıldığı için mesajlar manuel olarak DLQ'ya yönlendirilir**


### 🧪 Test Senaryoları
- Mesaj başarısız olursa 5 kere yeniden denenir
- Hâlâ başarısızsa DLQ'ya manuel olarak yönlendirilir
- DLQ mesajında `x-error-reason` header'ı görünür

---

