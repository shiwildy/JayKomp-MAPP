const express = require('express');
const bodyParser = require('body-parser');
const bcrypt = require('bcryptjs');
const nodemailer = require('nodemailer');
const mysql = require('mysql');
const dotenv = require('dotenv');
dotenv.config();

// Inisialisasi Express
const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Koneksi ke Database MySQL
const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME,
});

// Cek koneksi MySQL
db.connect((err) => {
    if (err) {
        console.error('Database connection failed: ', err);
        return;
    }
    console.log('Connected to MySQL database');
});

app.post('/register', async (req, res) => {
    const { nama, email, password } = req.body;

    if (!nama || !email || !password) {
        return res.status(400).json({ error: 'nama, Email, password are required' });
    }

    db.query('SELECT * FROM users WHERE email = ?', [email], async (err, result) => {
        if (err) {
            return res.status(500).json({ error: 'Database error' });
        }

        if (result.length > 0) {
            return res.status(400).json({ error: 'Email already exists' });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        db.query('INSERT INTO users (email, nama, password) VALUES (?, ?, ?)', [email, nama, hashedPassword], (err) => {
            if (err) {
                return res.status(500).json({ error: 'Database error' });
            }
            return res.status(201).json({ message: 'User registered successfully' });
        });
    });
});

app.post('/login', async (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.status(400).json({ error: 'Email and password are required' });
    }

    db.query('SELECT * FROM users WHERE email = ?', [email], async (err, result) => {
        if (err) {
            return res.status(500).json({ error: 'Database error' });
        }

        if (result.length === 0) {
            return res.status(400).json({ error: 'User not found' });
        }

        const isMatch = await bcrypt.compare(password, result[0].password);
        if (!isMatch) {
            return res.status(400).json({ error: 'Invalid password' });
        }

        // Return both message and the user's name (nama)
        return res.status(200).json({ 
            message: 'Login successful',
            nama: result[0].nama
        });
    });
});

app.post('/forget', (req, res) => {
    const { email } = req.body;

    if (!email) {
        return res.status(400).json({ error: 'Email is required' });
    }

    db.query('SELECT * FROM users WHERE email = ?', [email], (err, result) => {
        if (err) {
            return res.status(500).json({ error: 'Database error' });
        }

        if (result.length === 0) {
            return res.status(400).json({ error: 'User not found' });
        }

        const resetToken = Math.random().toString(36).substring(2, 15);

        db.query('UPDATE users SET reset_token = ? WHERE email = ?', [resetToken, email], (err) => {
            if (err) {
                return res.status(500).json({ error: 'Failed to store reset token' });
            }

            const transporter = nodemailer.createTransport({
                host: process.env.SMTP_HOST,
                port: process.env.SMTP_PORT,
                secure: process.env.SMTP_UTLS,
                auth: {
                    user: process.env.SMTP_USER,
                    pass: process.env.SMTP_PASS,
                },
                tls: {
                    rejectUnauthorized: false
                }
            });
            const resetUrl = `http://api-jaykomp.wildy.my.id/reset-password/${resetToken}`;

            const mailOptions = {
                from: process.env.SMTP_USER,
                to: email,
                subject: 'Password Reset Request',
                text: `Click the following link to reset your password: ${resetUrl}`,
            };

            transporter.sendMail(mailOptions, (err) => {
                if (err) {
                    return res.status(500).json({ error: 'Failed to send reset email' });
                }
                return res.status(200).json({ message: 'Reset link sent to your email' });
            });
        });
    });
});

// Reset token access untuk user
app.get('/reset-password/:token', (req, res) => {
    const { token } = req.params;

    db.query('SELECT * FROM users WHERE reset_token = ?', [token], (err, result) => {
        if (err) {
            return res.status(500).send('<h1>Internal Server Error</h1>');
        }

        if (result.length === 0) {
            return res.status(400).send('<h1>Invalid or expired reset token</h1>');
        }

        return res.send(`
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <title>Reset Password</title>
              <style>
                body {
                  font-family: Arial, sans-serif;
                  background-color: #f3f4f6;
                  margin: 0;
                  padding: 0;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  height: 100vh;
                }
                .container {
                  background: #ffffff;
                  padding: 30px;
                  border-radius: 8px;
                  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                  max-width: 400px;
                  width: 100%;
                }
                .container h1 {
                  text-align: center;
                  font-size: 24px;
                  color: #333;
                  margin-bottom: 20px;
                }
                .form-group {
                  margin-bottom: 15px;
                }
                .form-group label {
                  display: block;
                  font-weight: bold;
                  margin-bottom: 5px;
                  color: #555;
                }
                .form-group input {
                  width: 100%;
                  padding: 10px;
                  border: 1px solid #ddd;
                  border-radius: 5px;
                  font-size: 14px;
                }
                .form-group input:focus {
                  outline: none;
                  border-color: #007bff;
                  box-shadow: 0 0 4px rgba(0, 123, 255, 0.3);
                }
                .submit-btn {
                  width: 100%;
                  padding: 10px 15px;
                  background-color: #007bff;
                  border: none;
                  border-radius: 5px;
                  color: #fff;
                  font-size: 16px;
                  cursor: pointer;
                  transition: background-color 0.3s ease;
                }
                .submit-btn:hover {
                  background-color: #0056b3;
                }
                .footer {
                  text-align: center;
                  margin-top: 20px;
                  font-size: 12px;
                  color: #aaa;
                }
              </style>
            </head>
            <body>
              <div class="container">
                <h1>Reset Password</h1>
                <form action="/update-password" method="POST">
                  <input type="hidden" name="token" value="${token}" />
                  <div class="form-group">
                    <label for="newPassword">New Password:</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Enter your new password" required />
                  </div>
                  <button type="submit" class="submit-btn">Update Password</button>
                </form>
                <div class="footer">
                  © 2024 - JayKomp
                </div>
              </div>
            </body>
            </html>
          `);
    });
});

app.post('/update-password', async (req, res) => {
    const { token, newPassword } = req.body;

    if (!token || !newPassword) {
        return res.status(400).send('<h1>Error: Token and new password are required</h1>');
    }

    db.query('SELECT * FROM users WHERE reset_token = ?', [token], async (err, result) => {
        if (err) {
            return res.status(500).send('<h1>Internal Server Error</h1>');
        }

        if (result.length === 0) {
            return res.status(400).send('<h1>Invalid or expired reset token</h1>');
        }

        const hashedPassword = await bcrypt.hash(newPassword, 10);

        db.query(
            'UPDATE users SET password = ?, reset_token = NULL WHERE reset_token = ?',
            [hashedPassword, token],
            (err) => {
                if (err) {
                    return res.status(500).send('<h1>Internal Server Error</h1>');
                }
                return res.send('<h1>Password telah diubah.</h1>');
            }
        );
    });
});

app.get('/getnewproduct', (req, res) => {
    const query = 'SELECT * FROM barangs ORDER BY id DESC LIMIT 10';
    
    db.query(query, (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Database error' });
        }

        return res.status(200).json(results);
    });
});

app.get('/searchproduct', (req, res) => {
    const { keyword } = req.query;

    console.log('Keyword received:', keyword);

    if (!keyword) {
        return res.status(400).json({ error: 'Keyword is required' });
    }

    const cleanedKeyword = keyword.trim();
    const cleankeyword = cleanedKeyword.replace(/['"]/g, '').trim();
    const query = `
        SELECT * FROM barangs 
        WHERE nama LIKE '%${cleankeyword}%' 
        OR deskripsi LIKE '%${cleankeyword}%'
    `;

    db.query(query, (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Database error' });
        }

        return res.status(200).json(results);
    });
});

app.get('/get4', (req, res) => {
    const query = 'SELECT * FROM barangs ORDER BY RAND() LIMIT 4';

    db.query(query, (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Database error' });
        }

        return res.status(200).json(results);
    });
});


app.use('/images', express.static('images'));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});