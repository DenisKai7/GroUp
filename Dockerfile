# Gunakan image resmi Node.js sebagai base image
FROM node:18-slim

# Set working directory dalam container
WORKDIR /usr/src/app

# Menyalin package.json dan package-lock.json (jika ada) untuk menginstal dependensi
COPY package*.json ./

# Install dependencies aplikasi
RUN npm install --production

# Menyalin seluruh aplikasi ke dalam container
COPY . .

# Expose port yang digunakan oleh aplikasi
EXPOSE 8080

# Menjalankan aplikasi dengan perintah start-prod
CMD [ "npm", "run", "start:prod" ]