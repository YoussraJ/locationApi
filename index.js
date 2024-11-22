const express = require('express')
const mongoose = require('mongoose')
const bodyParser = require('body-parser')
const cors = require('cors');
const app = express()

// Use CORS middleware to allow cross-origin requests
app.use(cors());

// Middleware to parse JSON body
app.use(express.json());
app.use(bodyParser.json());

//connect  to MongoDB
mongoose.connect('put_your_database_url')
    .then(() => console.log('Connected to MongoDB'))
    .catch((err) => console.error('MongoDB connection error:', err));


//Import routes
const positionRoutes = require('./routes/positions.js')
app.use('/position', positionRoutes)

//Start the server
const PORT = 3000
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});