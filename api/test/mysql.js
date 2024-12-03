const mysql = require('mysql');
const dotenv = require('dotenv');
const path = require('path');

// Load environment variables
dotenv.config({ path: path.resolve(__dirname, '../.env') });

// MySQL connection
const db = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
});

db.connect((err) => {
  if (err) {
    console.error('Failed to connect to MySQL:', err.message);
    process.exit(1);
  } else {
    console.log('Connected to MySQL database successfully!');
    db.query('SHOW TABLES;', (queryErr, results) => {
      if (queryErr) {
        console.error('Error running query:', queryErr.message);
      } else {
        console.log('Tables in the database:', results);
      }
      db.end();
    });
  }
});
