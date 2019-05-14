FROM node:8.10.0
RUN mkdir -p /usr/lib/app
WORKDIR /usr/lib/app
COPY dist /usr/lib/app/dist
RUN npm install http-server -g
CMD ["http-server","dist/GipherUI"]

