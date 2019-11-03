const express = require('express');
const router = express.Router();
const User = require('../models/user');

router.get('/', async (req, res) => {
  console.log("GET from '/'");

  User.find(function (err, users) {
    if (err) {
      return console.error(err);
    }
    console.log(users);
    res.send(users);
  });
});

router.post('/', async (req, res) => {
  console.log("POST from '/'");
  console.log(req.query);

  var newUser = new User(req.query);
  newUser.save().then(item => {
    res.send("User created.");
  }).catch(err => {
    res.status(400).send("Unable to create user.");
  });
});

router.get('/:id', async (req, res) => {
  console.log("GET from '/:id'");
  console.log(req.params);

  User.findById(req.params.id, function (err, user) {
    if (err || user == null) {
      res.status(404).send("User ID not found.");
      return console.error(err);
    }

    console.log(user);
    res.send(user);
  });
});

router.put('/:id', async (req, res) => {
  // TODO:update a user
  console.log("PUT from '/:id'");
  console.log(req.params);
  console.log(req.query);

  User.findByIdAndUpdate(req.params.id, req.query, function (err, user) {
    if (err || user == null) {
      res.status(404).send("User ID not found.");
      return console.error(err);
    }
  });

  res.send("User updated.");
});

router.delete('/:id', async (req, res) => {
  // TODO:delete a user
  console.log("DELETE from '/:id'");
  console.log(req.params);

  User.findByIdAndDelete(req.params.id, function (err, user) {
    if (err || user == null) {
      res.status(404).send("User ID not found.");
      return console.error(err);
    }

    console.log(user);
  });

  res.send("User deleted.");
});

// async function getUser(req, res, next) {
//   // Not required, but could be used to get users with a specific id or all users
// }

module.exports = router 
