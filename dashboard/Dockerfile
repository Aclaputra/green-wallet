FROM node:alpine as build

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

COPY . .

RUN npm run build --prod

FROM nginx:latest

RUN apt-get update \
  && apt-get install -y \
  nmap \
  vim

RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /app/dist/dashboard/browser /usr/share/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
