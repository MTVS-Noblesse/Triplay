FROM node:lts-alpine

WORKDIR /app

# package.json과 package-lock.json을 먼저 복사
COPY package*.json ./

# npm을 최신 버전으로 업데이트하고 종속성 설치
RUN npm install -g && npm install && npm i @rollup/rollup-linux-x64-gnu

# 모든 소스 파일 복사
COPY . .

# 포트 노출
EXPOSE 5173

# 애플리케이션 실행
CMD ["npm", "run", "dev"]

