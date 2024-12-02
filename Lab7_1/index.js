const express = require('express');
const bodyparser = require('body-parser');
const sequelize = require('./util/database');
const User = require('./models/user');
const logger = require('./logger'); // Импорт логгера

const app = express();

app.use(bodyparser.json());
app.use(bodyparser.urlencoded({ extended: false }));

// Логирование всех запросов
app.use((req, res, next) => {
  logger.info(`Incoming request: ${req.method} ${req.url}`);
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
  next();
});

// Тестовый маршрут
app.get('/', (req, res, next) => {
  logger.info('GET / endpoint hit');
  res.send('Hello World');
});

// CRUD маршруты
app.use('/users', require('./routes/users'));

// Обработка ошибок
app.use((error, req, res, next) => {
  logger.error(`Error occurred: ${error.message}`);
  const status = error.statusCode || 500;
  const message = error.message;
  res.status(status).json({ message: message });
});

// Синхронизация базы данных и запуск сервера
sequelize
  .sync()
  .then(result => {
    logger.info('Database connected successfully');
    app.listen(3000, () => {
      logger.info('Server started on port 3000');
    });
  })
  .catch(err => logger.error(`Database connection error: ${err.message}`));