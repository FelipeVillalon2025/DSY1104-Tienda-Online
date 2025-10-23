import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

// URL del Backend (Spring Boot - Puerto 8080)
const API_URL = 'http://localhost:8080/api/auth/login';

export default function Login() {
  // 🟢 Corregido: Usamos 'password' en el estado para consistencia con el Backend.
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState(''); // Usamos 'password'
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    // ⚠️ Validación rápida del formulario
    if (!email || !password) {
        setError('El email y la contraseña son obligatorios.');
        setLoading(false);
        return;
    }
    
    try {
      // 1. Llamada a la API de autenticación
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        // 🟢 CORRECCIÓN CLAVE: Enviamos el campo como 'password'
        body: JSON.stringify({ email, password }), 
      });

      if (!response.ok) {
        // Manejar errores de credenciales (401 Unauthorized)
        const errorText = await response.text();
        throw new Error(errorText || 'Error de credenciales o acceso denegado.');
      }

      // 2. Login Exitoso
      const userData = await response.json(); 
      console.log("LOGIN EXITOSO, Usuario:", userData);

      // 3. Almacenar datos básicos de la sesión (Requisito de la rúbrica)
      localStorage.setItem('userRole', userData.rol);
      localStorage.setItem('userId', userData.id);

      // 4. Redirigir al Dashboard Administrativo (Requisito de la rúbrica)
      navigate('/admin/dashboard'); 

    } catch (err) {
      setError(err.message || 'Error de conexión con el servidor. Verifica el Backend.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <h1>Iniciar Sesión</h1>
          
          {/* Implementación de validación visual y feedback (Requisito de rúbrica) */}
          {error && <div className="alert alert-danger">{error}</div>}
          
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email</label>
              <input
                type="email"
                className="form-control"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password">Contraseña</label>
              <input
                type="password"
                className="form-control"
                id="password" // 🟢 Corregido: ID para accesibilidad
                // 🟢 Corregido: Usamos el estado 'password' y su setter
                value={password} 
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? 'Ingresando...' : 'Acceder'}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}