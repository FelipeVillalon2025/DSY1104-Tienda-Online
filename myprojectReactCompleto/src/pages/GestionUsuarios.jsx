import React, { useState } from 'react';

export default function GestionUsuarios() {
    const [usuarios, setUsuarios] = useState([
        { id: 1, nombre: 'Juan', email: 'juan@example.com' },
        { id: 2, nombre: 'María', email: 'maria@example.com' },
    ]);

    const [nuevoUsuario, setNuevoUsuario] = useState({
        nombre: '',
        email: '',
    });

    const handleInputChange = (e) => {
        setNuevoUsuario({
            ...nuevoUsuario,
            [e.target.name]: e.target.value,
        });
    };

    const agregarUsuario = () => {
        setUsuarios([
            ...usuarios,
            { id: usuarios.length + 1, ...nuevoUsuario },
        ]);
        setNuevoUsuario({ nombre: '', email: '' });
    };

    return (
        <div className="container">
            <h2>Gestión de Usuarios (CRUD)</h2>
            
            {/* Formulario para agregar usuario */}
            <div className="form-group">
                <h3>Agregar Usuario</h3>
                <input
                    type="text"
                    name="nombre"
                    placeholder="Nombre"
                    value={nuevoUsuario.nombre}
                    onChange={handleInputChange}
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={nuevoUsuario.email}
                    onChange={handleInputChange}
                />
                <button onClick={agregarUsuario}>Agregar</button>
            </div>

            {/* Lista de usuarios */}
            <div className="lista-usuarios">
                <h3>Lista de Usuarios</h3>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {usuarios.map((usuario) => (
                            <tr key={usuario.id}>
                                <td>{usuario.id}</td>
                                <td>{usuario.nombre}</td>
                                <td>{usuario.email}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}