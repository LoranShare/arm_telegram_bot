git pull https://github.com/LoranShare/arm_telegram_bot.git

mvn clean
mvn package

docker-compose stop

export BOT_NAME=$1
export BOT_TOKEN=$2

docker-compose up --build -d