const nodemailer = require('nodemailer');
const dotenv = require('dotenv');
const path = require('path');

// Load configuration from .env
dotenv.config({ path: path.resolve(__dirname, '../.env') });

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

const mailOptions = {
    from: process.env.SMTP_USER,
    to: 'shiwildy@gmail.com',
    subject: 'Coba email verifikasi',
    text: 'Email ini hanya sekedar untuk mencoba email verifikasi',
};

// Send
transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
        console.error('Error sending email:', error.message);
        process.exit(1);
    } else {
        console.log('Email sent successfully! Message ID:', info.messageId);
    }
});
