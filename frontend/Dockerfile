FROM node:18

WORKDIR /frontend

COPY package*.json ./
COPY vite.config.js ./

RUN npm install

COPY . .

EXPOSE 5173

CMD [ "npm", "run", "dev" ]