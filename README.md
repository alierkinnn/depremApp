# Deprem Haritası

Bu proje, basit bir panel içerisinde dünya haritası üzerinde anormal depremleri gösterebilen bir web uygulamasını içermektedir.


### Gereksinimler

1. JDK (Java Development Kit)

2. IntelliJ IDEA veya tercih ettiğiniz bir Java IDE

3. Node.js

4. VS Code veya tercih ettiğiniz bir metin editörü

5. Docker Desktop

6. Kafka


### Kurulum

1. Terminali açın ve sırayla

$ docker pull apache/kafka:3.7.0  (docker image'ını bilgisayara çekme işlemi)

$ docker run -p 9092:9092 apache/kafka:3.7.0 (başlatma işlemi) 

yazın.

2 .Sırada topic oluşturmak var 

İndirdiğiniz dosyada terminali açın ve 

$ bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092

yazın.


### Başlangıç

* Projeyi bilgisayarınıza klonlayın:

* IntelliJ IDEA veya tercih ettiğiniz Java IDE'sinde backend klasörünü açın ve uygulamayı çalıştırın

* VS Code veya tercih ettiğiniz metin editöründe frontend klasörünü açın ve uygulamayı çalıştırın

