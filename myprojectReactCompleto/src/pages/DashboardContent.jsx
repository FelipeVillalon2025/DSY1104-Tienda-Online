// src/pages/DashboardContent.jsx
import React, { useState, useEffect } from 'react';

// URL base de tu API
const API_BASE_URL = 'http://localhost:8080/api'; 
const LIMITE_CRITICO = 5; // Stock bajo para la alerta

// ❌ ELIMINAMOS las variables de autenticación, ya que el Backend permite acceso GET.

export default function DashboardContent() {
    // Definición de estados para las estadísticas
    const [stats, setStats] = useState({
        totalProductos: '...', 
        totalUsuarios: '...',
        stockCritico: '...',
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // ❌ ELIMINAMOS el objeto headers (no es necesario si el Backend permite acceso)

        const fetchStats = async () => {
            setLoading(true);
            setError(null);
            
            try {
                // 1. Total de Productos (Acceso ahora permitido por el Backend)
                const resProductos = await fetch(`${API_BASE_URL}/productos`); 
                // 2. Total de Usuarios (Acceso ahora permitido por el Backend)
                const resUsuarios = await fetch(`${API_BASE_URL}/usuarios`);
                // 3. Stock Crítico
                const resCritico = await fetch(`${API_BASE_URL}/productos/stock-critico?limit=${LIMITE_CRITICO}`);

                // Verificación de respuestas (si alguna no es 200, lanza error)
                if (!resProductos.ok || !resUsuarios.ok || !resCritico.ok) {
                    throw new Error('Fallo al obtener datos. Código: ' + resProductos.status);
                }

                // 4. Procesar los datos
                const dataProductos = await resProductos.json();
                const dataUsuarios = await resUsuarios.json();
                const dataCritico = await resCritico.json();

                // 5. Actualizar el estado con los resultados
                setStats({
                    totalProductos: dataProductos.length, // Debería ser 16
                    totalUsuarios: dataUsuarios.length,   // Debería ser 2
                    stockCritico: dataCritico.length,     // Debería ser 6
                });

            } catch (err) {
                console.error("Error al cargar estadísticas:", err);
                setError(err.message || "Error al cargar las estadísticas. Verifique la API.");
                setStats({ totalProductos: 'N/A', totalUsuarios: 'N/A', stockCritico: 'N/A' });
            } finally {
                setLoading(false);
            }
        };

        fetchStats();
    }, []); 


    return (
        <div>
            <h1 className="mb-4">Resumen de Actividades</h1>
            
            {/* 🟢 Renderizado condicional de la rúbrica */}
            {loading ? (
                <div className="alert alert-info">Cargando estadísticas...</div>
            ) : error ? (
                <div className="alert alert-danger">{error}</div>
            ) : (
                <div className="row">
                    {/* Tarjeta 1: Total de Productos (Esquema: Verde) */}
                    <div className="col-md-4 mb-3">
                        <div className="card text-white bg-success">
                            <div className="card-body">
                                <h5 className="card-title">Total Productos</h5>
                                <p className="card-text fs-2">{stats.totalProductos}</p>
                            </div>
                        </div>
                    </div>

                    {/* Tarjeta 2: Total de Usuarios (Esquema: Azul/Primary) */}
                    <div className="col-md-4 mb-3">
                        <div className="card text-white bg-primary">
                            <div className="card-body">
                                <h5 className="card-title">Usuarios Registrados</h5>
                                <p className="card-text fs-2">{stats.totalUsuarios}</p>
                            </div>
                        </div>
                    </div>

                    {/* Tarjeta 3: Productos con Stock Crítico (Esquema: Rojo/Danger - Requisito de la rúbrica) */}
                    <div className="col-md-4 mb-3">
                        <div className="card text-white bg-danger">
                            <div className="card-body">
                                <h5 className="card-title">Stock Crítico (Menos de {LIMITE_CRITICO} uds)</h5>
                                <p className="card-text fs-2">{stats.stockCritico}</p>
                            </div>
                        </div>
                    </div>
                </div>
            )}

            <hr/>
            <h2>Gestión Rápida</h2>
            <p>El Dashboard ya está consumiendo los datos de tu API REST.</p>
        </div>
    );
}