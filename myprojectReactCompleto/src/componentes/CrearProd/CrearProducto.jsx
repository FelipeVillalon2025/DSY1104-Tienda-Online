// src/componentes/CrearProd/CrearProducto.jsx
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
// ❌ Eliminamos la importación del Navbar, ya que el DashboardLayout lo proporciona.
import './CrearProducto.css';

const API_BASE_URL = 'http://localhost:8080/api';

// 💡 Credenciales para la autenticación Basic (Necesario para el CRUD y CATEGORÍAS)
const ADMIN_EMAIL = 'admin@tienda.cl';
const ADMIN_PASSWORD = 'admin123'; 
const AUTH_HEADERS_OBJ = {
    'Authorization': 'Basic ' + btoa(`${ADMIN_EMAIL}:${ADMIN_PASSWORD}`),
    'Content-Type': 'application/json'
};


export default function CrearProducto() {
    
    const navigate = useNavigate();

    const [producto, setProducto] = useState({
        nombre: '',
        descripcion: '',
        precio: '',
        categoria: ''
    });

    const [categorias, setCategorias] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [loadingCategorias, setLoadingCategorias] = useState(true);

    // 🟢 FUNCIÓN PARA CARGAR CATEGORÍAS CON AUTENTICACIÓN
    useEffect(() => {
        setLoadingCategorias(true);
        // Envía la cabecera de autenticación para acceder a las categorías
        fetch(`${API_BASE_URL}/categorias`, { headers: AUTH_HEADERS_OBJ })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Error al cargar categorías. Código: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                setCategorias(data);
                setError(null);
            })
            .catch(error => {
                console.error('Error al obtener las categorías:', error);
                setError('No se pudieron cargar las categorías. Verifique el endpoint /api/categorias.');
            })
            .finally(() => {
                setLoadingCategorias(false);
            });
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProducto(prev => ({
            ...prev,
            [name]: value
        }));
    };

    // 🟢 LÓGICA DE ENVÍO CON AUTENTICACIÓN (CRUD: POST)
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        const productoParaEnviar = {
            nombre: producto.nombre.trim(),
            descripcion: producto.descripcion.trim(),
            precio: parseFloat(producto.precio),
            // Asumimos que la categoría se mapea por ID
            categoria: { id: parseInt(producto.categoria, 10) } 
        };

        try {
            // Envía la cabecera de autenticación para la operación POST (REQUIERE SUPER_ADMIN)
            const response = await fetch(`${API_BASE_URL}/productos`, {
                method: 'POST',
                headers: AUTH_HEADERS_OBJ, 
                body: JSON.stringify(productoParaEnviar)
            });

            if (!response.ok) {
                if (response.status === 403 || response.status === 401) {
                    throw new Error('Permisos insuficientes. Se requiere rol SUPER_ADMIN.');
                }
                const errorData = await response.json().catch(() => ({}));
                throw new Error(errorData.message || 'Error al crear el producto.');
            }

            navigate('/admin/productos', { // Redirige a la tabla
                state: { message: 'Producto creado exitosamente' }
            });

        } catch (err) {
            setError(err.message || 'No se pudo crear el producto.');
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = () => {
        const tieneContenido = Object.values(producto).some(val => val !== '');

        if (!tieneContenido || window.confirm('¿Está seguro de cancelar? Se perderán los datos ingresados.')) {
            navigate('/admin/productos'); 
        }
    };

    return (
        // 🟢 ELIMINAMOS EL FRAGMENTO <> Y EL NAVBAR DUPLICADO
        <div className="crear-producto-container"> 
            <h3 className="mb-4">Crear Nuevo Producto (CRUD: POST)</h3>
            <div className="form-card">
                {error && (
                    <div className="alert alert-danger">
                        {error}
                        <button onClick={() => setError(null)} className="btn-close ms-2" />
                    </div>
                )}
                
                {loadingCategorias ? (
                    <div className="alert alert-info">Cargando categorías...</div>
                ) : (

                    <form onSubmit={handleSubmit}>
                        {/* ------------------- CAMPOS DEL FORMULARIO ------------------- */}
                        <div className="form-row">
                            <div className="form-group">
                                <label htmlFor="nombre">Nombre del producto <span className="required">*</span></label>
                                <input type="text" id="nombre" name="nombre" value={producto.nombre}
                                    onChange={handleChange} disabled={loading} maxLength={100}
                                    placeholder="Nombre del producto" required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="precio">Precio <span className="required">*</span></label>
                                <div className="price-input">
                                    <span className="currency">$</span>
                                    <input type="number" id="precio" name="precio" step="1" min="1"
                                        value={producto.precio} onChange={handleChange} disabled={loading}
                                        placeholder="1000" required
                                    />
                                </div>
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="descripcion">Descripción <span className="required">*</span></label>
                            <textarea id="descripcion" name="descripcion" rows="4"
                                value={producto.descripcion} onChange={handleChange} disabled={loading}
                                maxLength={500} placeholder="Características principales del producto..."
                                required
                            />
                            <small className="char-count">
                                {producto.descripcion.length}/500 caracteres
                            </small>
                        </div>

                        <div className="form-group">
                            <label htmlFor="categoria">Categoría <span className="required">*</span></label>
                            <select id="categoria" name="categoria" value={producto.categoria}
                                onChange={handleChange} disabled={loading || categorias.length === 0}
                                required>
                                <option value="">Seleccione una categoría</option>
                                {categorias.map(cat => (
                                    <option key={cat.id} value={cat.id}>
                                        {cat.nombre}
                                    </option>
                                ))}
                            </select>
                            {categorias.length === 0 && !loadingCategorias && (
                                <small className="error-text">No hay categorías disponibles</small>
                            )}
                        </div>

                        <div className="form-actions">
                            <button type="submit" disabled={loading} className="btn-primary">
                                {loading ? 'Guardando...' : 'Crear Producto'}
                            </button>
                            <button type="button" onClick={handleCancel} disabled={loading} className="btn-secondary">
                                Cancelar
                            </button>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}