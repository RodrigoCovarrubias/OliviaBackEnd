const { Pool } = require('pg');

// Configuración de la base de datos PostgreSQL
const pool = new Pool({
  user: 'qesxfpyu',
  host: 'isabelle.db.elephantsql.com',
  database: 'qesxfpyu',
  password: 'GT6i8Bey_KHKkopSM0Y9SNHjx0WCuVFP',
  port: 5432,
  max: 1,
  min: 1
});

async function getUsers() {
    try {
        const client = await pool.connect();
        const result = await client.query('SELECT * FROM usuario');
        const users = result.rows;
        client.release();
        return users;
      } catch (err) {
        console.error(err);
        throw new Error('Error al obtener usuarios');
      }
}

async function getUserById(id) {
    try {
        const client = await pool.connect();
        const result = await client.query('SELECT * FROM usuario WHERE id = $1', [id]);
        const user = result.rows[0];
        client.release();

        if(!user){
            throw new Error('Usuario no encontrado')
        }

        return user;
      } catch (err) {
        console.error(err);
        throw new Error('Error al obtener usuarios');
        
      }
}

async function createUser(nombre, correo, contrasena, apaterno, amaterno) {
  try {
      const regex = /^\S+$/; 

      if (!regex.test(nombre) || !regex.test(correo) || !regex.test(contrasena) || !regex.test(apaterno) || !regex.test(amaterno)) {
          throw new Error('Todos los campos son requeridos y no pueden contener espacios en blanco');
      }

      const client = await pool.connect();
      const result = await client.query('INSERT INTO usuario (nombre, correo, contrasena, apaterno, amaterno) VALUES ($1, $2, $3, $4, $5) RETURNING *', [nombre, correo, contrasena, apaterno, amaterno]);
      const newUser = result.rows[0];
      client.release();
      return newUser;
  } catch (err) {
      console.error(err);
      throw new Error('Error al crear usuario');
  }
}


async function deleteUserById(id) {
    try {
        const client = await pool.connect();
        const result = await client.query('DELETE FROM usuario WHERE id = $1 RETURNING *', [id]);
        const deletedUser = result.rows[0];
        client.release();
        return deletedUser;
    } catch (err) {
        console.error(err);
        throw new Error('Error al eliminar usuario');
    }
}



async function updateUserById(id, nombre, correo, contrasena, apaterno, amaterno) {
  try {
     const regex = /^\S+$/;  

      if (!id || !regex.test(nombre) || !regex.test(correo) || !regex.test(contrasena) || !regex.test(apaterno) || !regex.test(amaterno)) {
          throw new Error('Todos los campos son requeridos y no pueden contener espacios en blanco');
      }

      const client = await pool.connect();
      const result = await client.query('UPDATE usuario SET nombre = $2, correo = $3, contrasena = $4, apaterno = $5, amaterno = $6 WHERE id = $1 RETURNING *', [id, nombre, correo, contrasena, apaterno, amaterno]);
      const updatedUser = result.rows[0];
      client.release();

      if (!updatedUser) {
          throw new Error('Usuario no encontrado');
      }

      return updatedUser;
  } catch (err) {
      console.error(err);
      throw new Error('Error al actualizar usuario');
  }
}

module.exports = {
  getUsers,
  getUserById,
  createUser,
  deleteUserById,
  updateUserById  
}
