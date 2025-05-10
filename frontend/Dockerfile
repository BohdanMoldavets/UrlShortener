FROM node:18

WORKDIR /frontend

COPY react/package*.json ./
COPY react/vite.config.js ./

RUN npm install

COPY react/ ./

EXPOSE 5173

CMD [ "npm", "run", "dev" ]