# Deprem Haritası

Bu proje, basit bir panel içerisinde dünya haritası üzerinde depremleri gösterebilen bir web uygulamasını içermektedir.


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

2. [Buraya tıklayarak](https://kafka.apache.org/downloads "Kafka") kafka web sitesine gidip Scala 2.12'yi indirin

3. Sırada topic oluşturmak var 

  İndirdiğiniz dosyaya gidip terminali açın ve 

  $ bin/kafka-topics.sh --create --topic earthquake-topic --bootstrap-server localhost:9092

  yazın.


### Başlangıç

* Projeyi bilgisayarınıza klonlayın:

* IntelliJ IDEA veya tercih ettiğiniz Java IDE'sinde backend klasörünü açın ve uygulamayı çalıştırın

* VS Code veya tercih ettiğiniz metin editöründe frontend klasörünü açın ve uygulamayı çalıştırın


### Notlar

#### 1.Script

* Boşluklara girilen değerler ile sorunsuz bir şekilde veri eklenebilir.

#### 2.Script

* Butona basıldığı andan itibaren 3 saniyede bir rastgele deprem üretilerek sisteme dahil edilir, durdurulduğunda ise üretme işlemi durur ve eklenen depremler haritada gösterilir.

#### Depremleri çekme işlemi

* 1.Script başarıyla tamamlandığında ve 2.Script'te üretme işlemi durduğunda son 1 dakika içindeki depremler 50km çevresindeki depremler filtrelenerek çekilir. Ayrıca her 15 saniyede bir istek atılır.

