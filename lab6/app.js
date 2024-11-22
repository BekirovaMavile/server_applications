const express = require('express');
const logger = require('./logger');

const app = express();
const PORT = 3000;

app.use((req, res, next) => {
  logger.http(`HTTP Request: ${req.method} ${req.url}`);
  next();
});

app.get('/', (req, res) => {
  logger.info('User visited the home page');
  res.send('<h1>Welcome to the Home Page</h1><a href="/about">Go to About</a>');
});

app.get('/about', (req, res) => {
  logger.info('User visited the about page');
  res.send('<h1>About Page</h1><a href="/">Go back Home</a>');
});

app.get('/warn', (req, res) => {
  logger.warn('User triggered a warning log');
  res.send('<h1>Warning log recorded!</h1><a href="/">Go back Home</a>');
});

app.get('/error', (req, res) => {
  try {
    throw new Error('Simulated error occurred');
  } catch (err) {
    logger.error(`Error occurred: ${err.message}`);
    res.status(500).send('<h1>An error occurred! Check logs for details.</h1><a href="/">Go back Home</a>');
  }
});

app.get('/form', (req, res) => {
  logger.info('User accessed the form page');
  res.send(`
    <form method="POST" action="/form">
      <input type="text" name="name" placeholder="Enter your name" required />
      <button type="submit">Submit</button>
    </form>
  `);
});

app.use(express.urlencoded({ extended: true }));
app.post('/form', (req, res) => {
  const { name } = req.body;
  if (name.toLowerCase() === 'admin') {
    logger.warn('Admin access detected in form submission');
  }
  logger.info(`Form submitted with name: ${name}`);
  res.send(`<h1>Thank you, ${name}!</h1><a href="/">Go back Home</a>`);
});

app.get('/debug', (req, res) => {
  logger.debug('Debug route accessed. This is a detailed debug log.');
  res.send('<h1>Debug log recorded!</h1><a href="/">Go back Home</a>');
});

app.use((err, req, res, next) => {
  logger.error(`Unhandled error: ${err.message}`);
  res.status(500).send('Something went wrong!');
});

app.listen(PORT, () => {
  logger.info(`Server is running on http://localhost:${PORT}`);
});