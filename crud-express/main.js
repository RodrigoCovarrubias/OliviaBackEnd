const express = require('express');
const { getUsers, getUserById, createUser, deleteUserById, updateUserById } = require('./service');
const cors = require('cors');

const app = express();
app.use(express.json());
const port = 3001;
app.use(cors());

// Ruta para obtener todos los usuarios
app.get('/usuarios', async (req, res) => {
  try {
    const users = await getUsers();
    res.json(users);
  } catch (err) {
    console.error(err);
    res.status(500).send(err.message);
  }
});
//Ruta para obetener un solo usuario 
app.get('/usuario/:id', async (req, res) => {
    try {
      const user = await getUserById(req.params.id)
      res.json(user);
    } catch (err) {
      console.error(err);
      res.status(500).send(err.message);
    }
  });

// Ruta para crear un nuevo usuario
app.get('/usuario/:id', async (req, res) => {
    try {
        const { id } = req.params; // Obtener el ID del usuario de los parámetros de la ruta
        const user = await getUserById(id);
        res.json(user);
    } catch (err) {
        console.error(err);
        res.status(500).send(err.message);
    }
});

app.post('/usuario', async (req, res) => {
  const {nombre, correo, contrasena, apaterno, amaterno } = req.body; // Obtener los datos del cuerpo de la solicitud
  try {
      const newUser = await createUser( nombre, correo, contrasena, apaterno, amaterno);
      res.status(201).json(newUser);
  } catch (err) {
      console.error(err);
      res.status(500).send('Error al crear usuario');
  }
});


// Ruta para eliminar un usuario por ID
app.delete('/usuario/:id', async (req, res) => {
    const { id } = req.params; // Obtén el ID del usuario de los parámetros de la ruta
    try {
        const deletedUser = await deleteUserById(id); 
        if (!deletedUser) { 
            return res.status(404).send('Usuario no encontrado');
        }
        res.json({ message: 'Usuario eliminado correctamente', user: deletedUser });
    } catch (err) {
        console.error(err);
        res.status(500).send('Error al eliminar usuario');
    }
});

app.put('/usuario/:id', async (req, res) => {
  const { id } = req.params;
  const { nombre, correo, contrasena, apaterno, amaterno } = req.body;
  try {
      const updatedUser = await updateUserById(id, nombre, correo, contrasena, apaterno, amaterno);
      res.json(updatedUser);
  } catch (err) {
      console.error(err);
      res.status(500).send('Error al actualizar usuario');
  }
});


// Inicia el servidor
app.listen(port, () => {
  console.log(`Servidor Express escuchando en el puerto ${port}`);
});
