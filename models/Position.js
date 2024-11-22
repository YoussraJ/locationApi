const mongoose = require('mongoose');

const positionSchema = new mongoose.Schema({
  name: { type: String, required: true },
  latitude: { type: Number, required: true },
  longitude: { type: Number, required: true },
  isFavorite: {type: Boolean, default: false},
}, { timestamps: true }); // Automatically adds createdAt and updatedAt

module.exports = mongoose.model('Position', positionSchema);