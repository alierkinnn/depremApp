# İlk olarak, Node.js imajını baz alıyoruz
FROM node:14

# Çalışma dizinini /app olarak ayarlıyoruz
WORKDIR /app

# package.json ve package-lock.json dosyalarını /app dizinine kopyalıyoruz
COPY package*.json ./

# Bağımlılıkları yüklüyoruz
RUN npm install

# Tüm proje dosyalarını /app dizinine kopyalıyoruz
COPY . .

# Uygulamayı build ediyoruz
RUN npm run build

# Uygulamayı çalıştırmak için serve modülünü yüklüyoruz
RUN npm install -g serve

# Üretilen build dosyalarını yayınlamak için serve komutunu kullanıyoruz
CMD ["serve", "-s", "build", "-l", "3000"]
