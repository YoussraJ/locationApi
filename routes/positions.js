const express = require('express')
const router = express.Router()
const Position = require('../models/Position')

//GET /position - Retieve all saved position
router.get('/favorites', async(req, res) => {
  try {
      const positions = await Position.find({isFavorite: true});
      res.json(positions)
  } catch (error) {
     res.status(500).json({message: error.message}) 
  }
});

//GET /position - Retieve position by Id
router.get('/:id', async(req, res) => {
  try {
      const id = req.params.id
      const position = await Position.findById(id);
      res.json(position)
  } catch (error) {
     res.status(500).json({message: error.message}) 
  }
});
//GET /position - Retieve all saved position
router.get('/', async(req, res) => {
  try {
      const positions = await Position.find();
      res.json(positions)
  } catch (error) {
     res.status(500).json({message: error.message}) 
  }
});



// POST /positions - Save a new position
router.post('/', async (req, res) => {
    const { name, number, latitude, longitude } = req.body;
    console.log("Received dataaaa:", req.body);
    console.log("Latitude:", latitude, "Longitude:", longitude);
    
    const position = new Position({
      name,
      number,
      latitude: parseFloat(latitude),  // Convert latitude to number
      longitude: parseFloat(longitude) // Convert longitude to number
    });
    console.log("type logitude", typeof position.longitude)
    try {
      console.log("ayyyyyyyyyyyy")
      const newPosition = await position.save();
      res.json(newPosition);
    } catch (err) {
      res.status(400).json({ message: err.message });
    }
  });

  // POST /positions/bulk - Save multiple positions
router.post('/bulk', async (req, res) => {
  const positions = req.body; // Expecting an array of position objects
  if (!Array.isArray(positions)) {
      return res.status(400).json({ message: "Invalid data format. Expected an array of positions." });
  }

  try {
      // Validate and convert data types for each position
      const formattedPositions = positions.map(position => ({
          name: position.name,
          number: position.number,
          latitude: parseFloat(position.latitude), // Convert latitude to number
          longitude: parseFloat(position.longitude) // Convert longitude to number
      }));

      // Insert all positions into the database
      const savedPositions = await Position.insertMany(formattedPositions);

      res.status(201).json({
          message: `${savedPositions.length} positions successfully saved`,
          data: savedPositions
      });
  } catch (err) {
      res.status(500).json({ message: err.message });
  }
});


  // PUT /positions/:id - Update an existing position
router.put('/:id', async (req, res) => {
  console.log("bodddd", req.body.isFavorite)
    try {
      const updatedPosition = await Position.findByIdAndUpdate(
        req.params.id,
        req.body,
        { new: true }
      );
      res.json(updatedPosition);
    } catch (err) {
      console.log(err.message)
      res.status(400).json({ message: err.message });
    }
  });

  // DELETE /positions/:id - Delete a position by ID
router.delete('/:id', async (req, res) => {
  try {
      const deletedPosition = await Position.findByIdAndDelete(req.params.id);
      if (!deletedPosition) {
          return res.status(404).json({ message: 'Position not found' });
      }
      res.json({ message: 'Position deleted successfully' });
  } catch (err) {
      res.status(500).json({ message: err.message });
  }
});

module.exports =  router;