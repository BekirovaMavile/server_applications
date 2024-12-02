const logger = require('../logger'); // Убедитесь, что путь правильный
const Sequelize = require('sequelize');

const sequelize = new Sequelize(
    process.env.PG_DB,
    process.env.PG_USER,
    process.env.PG_PASSWORD,
    {
        host: process.env.PG_HOST,
        dialect: 'postgres',
        logging: (msg) => logger.info(msg) // Использование logger для SQL-запросов
    }
);

module.exports = sequelize;

// Проверка подключения с логированием ошибок
sequelize
    .authenticate()
    .then(() => {
        logger.info('Database connected successfully');
    })
    .catch((err) => {
        logger.error(`Database connection error: ${err.message}`);
        logger.error(err); // Логирование полной ошибки
    });